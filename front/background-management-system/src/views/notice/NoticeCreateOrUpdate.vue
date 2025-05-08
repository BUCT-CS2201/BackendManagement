<template>
    <div style="display: flex;background-color: rgb(221.7, 222.6, 224.4); width: 100%; height: 350px">
       <el-form ref="dataFormRef" :model="dataForm" label-width="80px" style="margin-top: 25px;width: 800px" >
          <el-form-item label="公告标题" prop="title">
          <el-input v-model="dataForm.title" placeholder="请输入公告标题"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="dataForm.name" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input type="textarea" v-model="dataForm.content" placeholder="请输入公告内容"/>
        </el-form-item>
        <el-form-item label="是否公开">
         <el-select v-model="dataForm.ispublic" placeholder="请选择状态">
            <el-option v-for="item in ispublicOptions" :key="item.value" :label="item.label" :value="item.value"/>
         </el-select>
       </el-form-item>
        <el-form-item style="margin-top: 30px;">
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="router.push({name:'notice'})">取消</el-button>
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
       notice_id:null,
       title:'',
       name:'',
       content:'',
       ispublic:1,
    });
     
    const ispublicOptions = ref([
      {
         label:'公开',
         value:1
      },
      {
         label:'不公开',
         value:0
      }
   ]);
    // 表单提交校验
    
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
                      url: proxy.Api.addOrUpdateNotice,
                      params: params
                   });
                   if(!result){
                      return;
                   }
                   proxy.Message.success("提交成功");
                   router.push({name:'notice'});
                 },
              });
           }
        })
     }
 
    onMounted(()=>{
       const row = route.query.row?JSON.parse(route.query.row):null;
       if(row){
          dataForm.notice_id = row.notice_id;
          dataForm.title = row.title;
          dataForm.name = row.name;
          dataForm.content = row.content;
          dataForm.ispublic = row.ispublic;
       }
    })
 </script>
 
 <style scoped>
 
 </style>