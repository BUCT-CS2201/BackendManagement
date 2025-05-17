<template>
    <div style="display: flex;background-color: rgb(221.7, 222.6, 224.4); width: 100%; height: 350px">
      <el-form ref="dataFormRef" :model="dataForm" label-width="130px" style="margin-top: 25px;width: 800px" :rules="rules">
        <el-form-item label="管理员姓名" prop="adminName">
         <el-input v-model="dataForm.adminName" placeholder="请输入管理员姓名"/>
        </el-form-item>
        <el-form-item label="管理员电话号码" prop="phoneNumber">
         <el-input v-model="dataForm.phoneNumber" placeholder="请输入电话号码"/>
        </el-form-item>
        <el-form-item label="管理员密码" prop="password">
         <el-input v-model="dataForm.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item label="日志内容" prop="operation">
         <el-input v-model="dataForm.operation" placeholder="请输入日志内容"/>
        </el-form-item>
        <el-form-item style="margin-top: 30px;">
         <el-button type="primary" @click="onSubmit">提交</el-button>
         <el-button @click="router.push({name:'log'})">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script setup>
   import { useRoute,useRouter} from 'vue-router';
   import {ref,reactive,onMounted,getCurrentInstance} from 'vue';
   const route = useRoute();
   const router = useRouter();
   const proxy = getCurrentInstance().proxy;
   const dataFormRef = ref(null);
   // 表单
   const dataForm = reactive({
      adminName:'',
      phoneNumber: '',
      password:'',
      operation:'',
   });
   // 表单提交校验
   const rules ={
        adminName:[
            { required: true, message: '请输入管理员姓名', trigger: 'blur' },
            { min: 1, max: 40, message: '姓名不能为空', trigger: 'blur' }
        ],
        phoneNumber: [
            { required: true, message: '请输入电话号码', trigger: 'blur' },
            { pattern: /^1[3456789]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
        ],
        password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 1, max: 40, message: '密码不能为空', trigger: 'blur' }
        ],
        operation: [
            { required: true, message: '请输入日志内容', trigger: 'blur' },
            { min: 1, max: 40, message: '身份证号格式不正确', trigger: 'blur' }
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
                     url: proxy.Api.addLog,
                     params: params
                  });
                  if(!result){
                     return;
                  }
                  if (result.code !== 200) {
                     proxy.Message.error(result.info);
                     return;
                  }
                  proxy.Message.success("添加成功");
                  router.push({name:'log'});
                },
             });
          }
       })
    }

   onMounted(()=>{
      const row = route.query.row?JSON.parse(route.query.row):null;
      if(row){
         dataForm.adminName = row.adminName;
         dataForm.phoneNumber = row.phoneNumber;
         dataForm.password = row.password;
         dataForm.operation = row.operation;
      }
   })
</script>

<style scoped>

</style>