<template>
  <div style="display: flex;background-color: rgb(221.7, 222.6, 224.4); width: 100%; height: 580px">
    <el-form ref="dataFormRef" :model="dataForm" label-width="80px" style="margin-top: 25px;width: 800px" :rules="rules">
      <el-form-item label="文物名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入文物名称"/>
      </el-form-item>
      <el-form-item label="文物类型" prop="type">
        <el-input v-model="dataForm.type" placeholder="请输入文物类型"/>
      </el-form-item>
      <el-form-item label="文物介绍" prop="description">
        <el-input v-model="dataForm.description" placeholder="请输入文物介绍"/>
      </el-form-item>
      <el-form-item label="文物尺寸" prop="size">
        <el-input v-model="dataForm.size" placeholder="请输入文物尺寸"/>
      </el-form-item>
      <el-form-item label="文物材料" prop="matrials">
        <el-input v-model="dataForm.matrials" placeholder="请输入文物材料"/>
      </el-form-item>
      <el-form-item label="文物年代" prop="dynasty">
        <el-select v-model="dataForm.dynasty" placeholder="请选择文物年代">
          <el-option v-for="item in dynastyOptions" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="文物作者" prop="author">
        <el-input v-model="dataForm.author" placeholder="请输入文物作者(不明请填写“不明”)"/>
      </el-form-item>
      <el-form-item label="入馆年份" prop="entryTime">
        <el-input v-model="dataForm.entryTime" placeholder="请输入入馆年份"/>
      </el-form-item>
      <el-form-item style="margin-top: 30px;">
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="router.push({name:'relic'})">取消</el-button>
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
const proxy = getCurrentInstance().proxy;
// 用户信息
const userInfoStore = useUserInfoStore()
const dataFormRef = ref(null);
// 表单 
const dataForm = reactive({
  relicId: null,
  museumId: 1,
  name:'',
  type:'',
  description:'',
  size: '',
  matrials: '',
  dynasty: '',
  author: '',
  entryTime:'',
  likesCount: 0,
  viewsCount: 0,
});
const dynastyOptions = ref([
  { value: '新石器时代', label: '新石器时代' },
  { value: '龙山文化', label: '龙山文化' },
  { value: '商', label: '商' },
  { value: '商代前期', label: '商代前期' },
  { value: '商代后期', label: '商代后期' },
  { value: '周', label: '周' },
  { value: '西周', label: '西周' },
  { value: '西周早期', label: '西周早期' },
  { value: '东周', label: '东周' },
  { value: '战国时期', label: '战国时期' },
  { value: '秦朝', label: '秦朝' },
  { value: '西汉', label: '西汉' },
  { value: '新朝', label: '新朝' },
  { value: '东汉', label: '东汉' },
  { value: '三国', label: '三国（魏、蜀、吴）' },
  { value: '西晋', label: '西晋' },
  { value: '十六国', label: '十六国时期' },
  { value: '东晋', label: '东晋' },
  { value: '南朝', label: '南朝时期' },
  { value: '北朝', label: '北朝时期' },
  { value: '隋', label: '隋朝' },
  { value: '唐', label: '唐朝' },
  { value: '五代十国', label: '五代十国' },
  { value: '西夏', label: '西夏' },
  { value: '金朝', label: '金朝' },
  { value: '辽朝', label: '辽朝' },
  { value: '宋', label: '宋朝' },
  { value: '南宋', label: '南宋' },
  { value: '北宋', label: '北宋' },
  { value: '元', label: '元朝|元代' },
  { value: '明', label: '明朝|明代' },
  { value: '清', label: '清朝|清代' },
  { value: '近现代', label: '近现代' },
]);
// 表单提交校验
const rules ={
  museumId: [
    { required: true, message: '请输入文物所在博物馆id(1,2,3...)', trigger: 'blur' },
    { min: 1, max: 20, message: '博物馆id=1,2,3...', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入文物名称', trigger: 'blur' },
    { min: 2, max: 20, message: '文物名称长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请输入文物类型', trigger: 'blur' },
    { min: 2, max: 20, message: '文物类型长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入文物介绍', trigger: 'blur' },
    { min: 2, max: 500, message: '文物描述长度在 2 到 500 个字符', trigger: 'blur' }
  ],
  size: [
    { required: true, message: '请输入文物尺寸', trigger: 'blur' },
    { min: 2, max: 50, message: '文物尺寸长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  matrials: [
    { required: true, message: '请输入文物材料', trigger: 'blur' },
    { min: 2, max: 20, message: '文物材料长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  author: [
    { required: true, message: '请输入文物作者', trigger: 'blur' },
    { min: 2, max: 20, message: '文物作者长度在 2 到 20 个字符(未知填写“不明”)', trigger: 'blur' }
  ],
  entryTime: [
    { required: true, message: '请输入文物入馆时间', trigger: 'blur' },
    { pattern: /^-?[0-9]{1,4}$/, message: '入馆时间必须是 -9999 到 9999 之间的整数', trigger: 'blur' }
  ],
  likesCount:[
    { required: true, message: '请输入点赞数', trigger: 'blur' },
    { min: 1, max: 20, message: '初始化为0', trigger: 'blur' }
  ],
  viewsCount:[
    { required: true, message: '请输入阅读数', trigger: 'blur' },
    { min: 1, max: 20, message: '初始化为0', trigger: 'blur' }
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
          // 确保 likesCount 和 viewsCount 存在且不为 0 时才传递，否则不包含在 params 中
          let result = await proxy.Request.request({
            url: proxy.Api.addOrUpdateRelic,
            params: params
          });
          if(!result){
            return;
          }
          proxy.Message.success("提交成功");
          router.push({name:'relic'});
        },
      });
    }
  })
}

onMounted(()=>{
  const row = route.query.row?JSON.parse(route.query.row):null;
  if(row){
    dataForm.relicId = row.relicId;
    dataForm.museumId = row.museumId;
    dataForm.name = row.name;
    dataForm.type = row.type;
    dataForm.description = row.description;
    dataForm.size = row.size;
    dataForm.matrials = row.matrials;
    dataForm.dynasty = row.dynasty;
    dataForm.author = row.author;
    dataForm.entryTime = row.entryTime;
    dataForm.likesCount = row.likesCount;
    dataForm.viewsCount = row.viewsCount;
  }
})
</script>

<style scoped>

</style>