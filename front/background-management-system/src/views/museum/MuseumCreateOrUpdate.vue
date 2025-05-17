<template>
   <div style="display: flex;background-color: rgb(221.7, 222.6, 224.4); width: 100%; height: 350px">
      <el-form ref="dataFormRef" :model="dataForm" label-width="80px" style="margin-top: 25px;width: 800px" :rules="rules">
         <el-form-item label="博物馆名称" prop="museumName">
            <el-input v-model="dataForm.museumName" placeholder="请输入博物馆名称"/>
         </el-form-item>
         <el-form-item label="博物馆介绍" prop="description">
            <el-input v-model="dataForm.description" type="textarea" placeholder="请输入博物馆介绍"/>
         </el-form-item>
         <el-form-item label="博物馆地址" prop="address">
            <el-input v-model="dataForm.address" placeholder="请输入博物馆地址"/>
         </el-form-item>
         <el-form-item label="网址" prop="websiteUrl">
            <el-input v-model="dataForm.websiteUrl" placeholder="请输入网址"/>
         </el-form-item>
         <el-form-item label="购票网站" prop="bookingUrl">
            <el-input v-model="dataForm.bookingUrl" placeholder="请输入购票网站"/>
         </el-form-item>
         <el-form-item style="margin-top: 30px;">
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button @click="router.push({name:'museum'})">取消</el-button>
         </el-form-item>
      </el-form>
   </div>
</template>

<script setup>
   import { useRoute, useRouter } from 'vue-router';
   import { ref, reactive, onMounted, getCurrentInstance } from 'vue';
   import { useUserInfoStore } from '@/stores/userInfoStore'
   const route = useRoute();
   const router = useRouter();
   const proxy = getCurrentInstance().proxy;
   const dataFormRef = ref(null);
   // 用户信息
   const userInfoStore = useUserInfoStore()
   // 判断是否为编辑状态
   const isEdit = ref(false);

   // 表单
   const dataForm = reactive({
      museumId: null,
      museumName: '',
      description: '',
      address: '',
      websiteUrl: '',
      bookingUrl: ''
   });

   // 表单提交校验
   const rules = {
      museumName: [
         { required: true, message: '请输入博物馆名称', trigger: 'blur' },
         { min: 1, max: 100, message: '博物馆名称长度在 1 到 100 个字符', trigger: 'blur' }
      ],
      description: [
         { required: false, message: '请输入博物馆介绍', trigger: 'blur' }
      ],
      address: [
         { required: false, message: '请输入博物馆地址', trigger: 'blur' },
         { min: 1, max: 100, message: '博物馆地址长度在 1 到 100 个字符', trigger: 'blur' }
      ],
      websiteUrl: [
         { required: false, message: '请输入网址', trigger: 'blur' },
         { pattern: /^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$/, message: '网址格式不正确', trigger: 'blur' }
      ],
      bookingUrl: [
         { required: false, message: '请输入购票网站', trigger: 'blur' },
         { pattern: /^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$/, message: '购票网站格式不正确', trigger: 'blur' }
      ]
   }

   /**
    * 表单提交
    */
   const onSubmit = () => {
      dataFormRef.value.validate((valid) => {
         if (!valid) {
            proxy.Message.error("请检查输入信息是否正确");
            return;
         }
         proxy.Confirm({
            message: '确定提交？',
            okfun: async () => {
               // 打印提交的数据，确保新增时 museumId 为 null 或未传递
               console.log('提交的博物馆数据:', dataForm);
               let params = { ...dataForm };
               if (!isEdit.value) {
                  // 新增时确保 museumId 为 null
                  params.museumId = null; 
               }

               try {
                  let result = await proxy.Request.request({
                     url: proxy.Api.addOrUpdateMuseum,
                     params: params
                  });
                  if (!result) {
                     proxy.Message.error("提交失败，未收到有效响应");
                     return;
                  }
                  proxy.Message.success("提交成功!");
                  router.push({ name: 'museum' }).then(() => {
                     window.dispatchEvent(new Event('refresh-museum-list'));
                  });
               } catch (error) {
                  console.error('提交请求出错:', error);
                  proxy.Message.error("提交失败，请稍后重试");
               }
            },
         });
      });
   };

   onMounted(() => {
      const row = route.query.row ? JSON.parse(route.query.row) : null;
      if (row) {
         isEdit.value = true;
         dataForm.museumId = row.museumId;
         dataForm.museumName = row.museumName;
         dataForm.description = row.description;
         dataForm.address = row.address;
         dataForm.websiteUrl = row.websiteUrl;
         dataForm.bookingUrl = row.bookingUrl;
      }
      else{
        dataForm.museumId = null; 
      }
   });
</script>

<style scoped>

</style>
