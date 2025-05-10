<template>
    <div style="display: flex;background-color: rgb(221.7, 222.6, 224.4); width: 100%; height: 350px">
      <el-form ref="dataFormRef" :model="dataForm" label-width="80px" style="margin-top: 25px;width: 800px" :rules="rules">
          <el-form-item label="博物馆id" prop="museumId">
          <el-input v-model="dataForm.museumId" placeholder="请输入博物馆id"/>
        </el-form-item>
        <el-form-item label="公告标题" prop="noticeTitle">
          <el-input v-model="dataForm.noticeTitle" placeholder="请输入公告标题"/>
        </el-form-item>
        <el-form-item label="公告作者" prop="noticeAuthor">
          <el-input v-model="dataForm.noticeAuthor" placeholder="请输入公告作者"/>
        </el-form-item>
        <el-form-item label="公告内容" prop="noticeContent">
          <el-input type="textarea" v-model="dataForm.noticeContent" placeholder="请输入公告内容"/>
        </el-form-item>
        <el-form-item label="公告时间">
              <el-date-picker v-model="dataForm.noticeTime" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
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
       noticeId:null,
       museumId:null,
       noticeTitle:'',
       noticeAuthor:'',
       noticeContent:'',
       noticeTime:null,
    });
    // 表单提交校验
    // 表单提交校验
   const rules = {
      museumId: [
         { required: true, message: '请输入博物馆id', trigger: 'blur' },
      ],
      noticeTitle: [
         { required: true, message: '请输入公告标题', trigger: 'blur' },
         { min: 1, max: 100, message: '公告标题在 1 到 100 个字符', trigger: 'blur' }
      ],
      noticeAuthor: [
         { required: true, message: '请输入公告作者', trigger: 'blur' },
         { min: 1, max: 100, message: '公告作者在 1 到 50 个字符', trigger: 'blur' }
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
                   if(!params.noticeTime || params.noticeTime.length == 0){
                     delete params.noticeTime;
                   }
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
          dataForm.noticeId = row.noticeId;
          dataForm.museumId = row.museumId;
          dataForm.noticeTitle = row.noticeTitle;
          dataForm.noticeAuthor = row.noticeAuthor;
          dataForm.noticeContent = row.noticeContent;
          dataForm.noticeTime = row.noticeTime;
       }
    })
 </script>
 
 <style scoped>
 
 </style>