<template>
   <div style="display: flex;background-color: rgb(221.7, 222.6, 224.4); width: 100%; height: 380px">
      <el-form ref="dataFormRef" :model="dataForm" label-width="80px" style="margin-top: 25px;width: 800px" :rules="rules">
         <el-form-item label="电话号码" prop="phoneNumber">
         <el-input v-model="dataForm.phoneNumber" placeholder="请输入电话号码"/>
       </el-form-item>
       <el-form-item label="姓名" prop="name">
         <el-input v-model="dataForm.name" placeholder="请输入姓名"/>
       </el-form-item>
       <el-form-item label="身份账号" prop="idNumber">
         <el-input v-model="dataForm.idNumber" placeholder="请输入身份证号"/>
       </el-form-item>
       <el-form-item label="角色类型">
         <el-select v-model="dataForm.roleType" placeholder="请选择角色类型">
            <el-option v-for="item in roleTypeOptions" :key="item.value" :label="item.label" :value="item.value"/>
         </el-select>
       </el-form-item>
       <el-form-item label="账户状态">
         <el-select v-model="dataForm.accountStatus" placeholder="请选择状态">
            <el-option v-for="item in accountStatusOptions" :key="item.value" :label="item.label" :value="item.value"/>
         </el-select>
       </el-form-item>
       <el-form-item label="评论状态">
         <el-select v-model="dataForm.commentStatus" placeholder="请选择状态">
            <el-option v-for="item in commentStatusOptions" :key="item.value" :label="item.label" :value="item.value"/>
         </el-select>
       </el-form-item>
       <el-form-item style="margin-top: 30px;">
         <el-button type="primary" @click="onSubmit">提交</el-button>
         <el-button @click="router.push({name:'user'})">取消</el-button>
       </el-form-item>
   </el-form>
   </div>
</template>

<script setup>
   import { useRoute,useRouter} from 'vue-router';
   import {ref,reactive,onMounted,getCurrentInstance} from 'vue';
   import { useUserInfoStore } from '@/stores/userInfoStore'
   const route = useRoute();
   const router = useRouter();
   // 用户信息
   const userInfoStore = useUserInfoStore()
   const proxy = getCurrentInstance().proxy;
   const dataFormRef = ref(null);
   // 表单
   const dataForm = reactive({
      userId: null,
      phoneNumber:'',
      name:'',
      idNumber:'',
      roleType: 0,
      accountStatus: 1,
      commentStatus: 1,
   });
   const roleTypeOptions = ref([
      {
         label:'管理员',
         value:1
      },
      {
         label:'用户',
         value:0
      }
   ]);
   const accountStatusOptions = ref([
      {
         label:'启用',
         value:1
      },
      {
         label:'禁用',
         value:0
      }
   ]);
   const commentStatusOptions = ref([
      {
         label:'允许',
         value:1
      },
      {
         label:'禁止',
         value:0
      }
   ]);
   // 表单提交校验
   const rules ={
        phoneNumber: [
            { required: true, message: '请输入电话号码', trigger: 'blur' },
            { pattern: /^1[3456789]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
        name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 2, max: 20, message: '姓名长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        idNumber: [
            { required: true, message: '请输入身份证号', trigger: 'blur' },
            { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '身份证号格式不正确', trigger: 'blur' }
        ],
    }

    /**
     * 表单提交
     */
    const onSubmit = ()=>{
       // 校验
       dataFormRef.value.validate((valid) => {
          if(!valid){
            proxy.Message.error("请检查输入信息是否正确");
          }else{
             // 确认框
             proxy.Confirm({
                message: '确定提交？',
                okfun: async ()=>{
                  // 提交表单
                  let params = {};
                  Object.assign(params,dataForm);
                  let result = await proxy.Request.request({
                     url: proxy.Api.addOrUpdateUser,
                     params: params
                  });
                  if(!result){
                     return;
                  }
                  proxy.Message.success("提交成功");
                  router.push({name:'user'});
                },
             });
          }
       })
    }

   onMounted(()=>{
      const row = route.query.row?JSON.parse(route.query.row):null;
      if(row){
         dataForm.userId = row.userId;
         dataForm.phoneNumber = row.phoneNumber;
         dataForm.name = row.name;
         dataForm.idNumber = row.idNumber;
         dataForm.roleType = row.roleType;
         dataForm.accountStatus = row.accountStatus;
         dataForm.commentStatus = row.commentStatus;
      }
   })
</script>

<style scoped>

</style>