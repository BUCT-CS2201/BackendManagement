<template>
    <div style="display: flex;background-color: rgb(221.7, 222.6, 224.4); width: 100%; height: 250px">
      <el-form ref="dataFormRef" :model="dataForm" label-width="100px" style="margin-top: 25px;width: 800px" :rules="rules">
          <el-form-item label="管理员名称" prop="adminName">
          <el-input v-model="dataForm.adminName" placeholder="请输入管理员名称"/>
        </el-form-item>
        <el-form-item label="备份注释" prop="comment">
          <el-input v-model="dataForm.comment" placeholder="请输入备份注释"/>
        </el-form-item>
        <el-form-item label="备份路径" prop="path">
         <el-input v-model="dataForm.path" :disabled="dataForm.id != null" placeholder="请输入备份路径"/>
        </el-form-item>
        <el-form-item style="margin-top: 30px;">
          <el-button type="primary" @click="onSubmit">备份</el-button>
          <el-button @click="router.push({name:'database'})">取消</el-button>
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
       id:null,
       adminName:'',
       comment:'',
       path:'',
    });
    // 表单提交校验
    // 表单提交校验
   const rules = {
      path: [
         { required: true, message: '请输入备份路径', trigger: 'blur' },
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
                      url: proxy.Api.addOrUpdateBackup,
                      params: params
                   });
                   if(!result){
                      return;
                   }
                   proxy.Message.success("提交成功");
                   router.push({name:'database'});
                 },
              });
           }
        })
     }
     /**
      * 获取默认路径
      */
     const getDefaultPath = async ()=>{
      let result = await proxy.Request.request({
         url: proxy.Api.getDefaultPath
      });
      if(!result){
         return;
      }
      dataForm.path = result.data;
     }
    onMounted(()=>{
       const row = route.query.row?JSON.parse(route.query.row):null;
       if(row){
          dataForm.id = row.id;
          dataForm.adminName = row.adminName;
          dataForm.comment = row.comment;
          dataForm.path = row.path;
       }
       if(!dataForm.path || dataForm.path.length == 0){
         getDefaultPath();
       }
    })
 </script>
 
 <style scoped>
 
 </style>