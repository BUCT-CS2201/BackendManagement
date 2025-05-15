<template>
  <el-card>
    <template #header>
      <div>
        <el-form ref="searchFomRef" :model="searchForm" label-width="100px" :inline="true">
          <el-form-item label="博物馆id">
            <el-input v-model="searchForm.museumId" placeholder="请输入" clearable></el-input>
          </el-form-item>
          <el-form-item label="博物馆名称">
            <el-input v-model="searchForm.museumName" placeholder="支持模糊搜索" clearable></el-input>
          </el-form-item>
          <el-row>
            <el-col style="margin-left: 950px;">
              <el-button type="primary" @click="clearSearchForm">清空</el-button>
              <el-button type="primary" @click="loadDataList">查询</el-button>
              <el-button type="primary" @click="router.push({ name: 'museumCreateOrUpdate' })">新增</el-button>
              <!-- <el-button type="primary" @click="exportExcel">导出</el-button> -->
            </el-col>
          </el-row>
        </el-form>
      </div>
    </template>
    <Table :columns="columns" :fetch="loadDataList" :dataSource="tableData" :options="tableOptions">
      <!-- 表单列插槽插入 -->
      <!-- 博物馆id -->
      <template #slotMuseumId="{ index, row }">
        {{ row.museumId }}
      </template>
      <!-- 博物馆名称 -->
      <template #slotMuseumName="{ index, row }">
        {{ row.museumName }}
      </template>
      <!-- 博物馆地址 -->
      <template #slotAddress="{index,row}">
        {{ row.address }}
      </template>
      <!-- 网址 -->
      <template #slotWebsiteUrl="{index,row}">
        {{ row.websiteUrl }}
      </template>
      <!-- 购票网站 -->
      <template #slotBookingUrl="{index,row}">
        {{ row.bookingUrl }}
      </template>
      <!-- 博物馆介绍 -->
      <template #slotDescription="{index,row}">
        {{ row.description }}
      </template>
      <!-- 操作 -->
      <template #slotOperation="{index,row}">
        <el-link type="primary"
          @click="router.push({name:'museumCreateOrUpdate',query:{row:JSON.stringify(row)}})"
          style="margin-right: 20px">编辑</el-link>
        <el-link type="danger" @click="deleteMuseum(row,true)">删除</el-link>
      </template>
    </Table>
  </el-card>
</template>

<script setup>
import { ref, getCurrentInstance, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserInfoStore } from '@/stores/userInfoStore'
import Table from "@/components/Table.vue";

const { proxy } = getCurrentInstance();
// 路由
const router = useRouter();
// 用户信息
const userInfoStore = useUserInfoStore()
// 表单
const tableData = ref({});
// 表单样式
const tableOptions = {
    showIndex: true,
    border: true,
};
// 表单列
const columns = [
  {
    label: "博物馆id",
    prop: "museumId",
    width: "200",
    scopedSlots: "slotMuseumId",
    fixed: false
  },
  {
    label: "博物馆名称",
    prop: "museumName",
    width: "200",
    scopedSlots: "slotMuseumName",
    fixed: false
  },
  {
    label: "博物馆地址",
    prop: "address",
    width: "300",
    scopedSlots: "slotAddress",
    fixed: false
  },
  {
    label: "网址",
    prop: "websiteUrl",
    width: "300",
    scopedSlots: "slotWebsiteUrl",
    fixed: false
  },
  {
    label: "购票网站",
    prop: "bookingUrl",
    width: "300",
    scopedSlots: "slotBookingUrl",
    fixed: false
  },
  {
    label: "博物馆介绍",
    prop: "description",
    width: "300",
    scopedSlots: "slotDescription",
    fixed: false
  },
  {
    label: "操作",
    width: "200",
    scopedSlots: "slotOperation" ,
    fixed: 'right'   
  }
];
// 查询表单
const searchForm = ref({});
/**
 * 获取数据
 */
const loadDataList = async () => {
  let params = {
    pageNo: tableData.value.pageNo,
    pageSize: tableData.value.pageSize,
  };
  Object.assign(params, searchForm.value);
  let result = await proxy.Request.request({
    url: proxy.Api.loadMuseumList,
    params: params,
  });
  if (!result) {
    return;
  }
  Object.assign(tableData.value, result.data);
};
/**
 * 删除博物馆
 */
const deleteMuseum = async (row,needConfirm=false)=>{
  if(needConfirm){
    proxy.Confirm({
      message: "确定要删除博物馆吗？",
      okfun: ()=>deleteMuseum(row,false),
    });
    return;
  }
  let result = await proxy.Request.request({
    url: proxy.Api.deleteMuseum,
    params: {
      museumId: row.museumId,
      adminName: userInfoStore.userInfo.name
    }
  });
  if (!result) {
    return;
  }
  proxy.Message.success("删除成功");
  loadDataList();
}
/**
 * 清空查询条件
 */
const clearSearchForm = () => {
  searchForm.value = {};
}
/**
 * 导出数据
 */
 const exportExcel = async (row,needConfirm=false)=>{
    if(needConfirm){
        proxy.Confirm({
            message: "确定要导出数据吗？",
            okfun: ()=>exportExcel(row,false)
        });
        return;
    }
    const museumList = (tableData.value.list || []).map((item) => item);
    if (museumList.length === 0) {
        proxy.Message.warning("当前没有可导出的博物馆数据");
        return;
    }
    let result = await proxy.Request.requestJSON({
        url: proxy.Api.exportMuseumExcel,
        params: museumList, 
        responseType: 'arraybuffer'
    });
    if(!result){
        return;
    }
    let blob = new Blob([result.data], { type: 'application/vnd.ms-excel' });
    const fileName = "博物馆数据excel.xlsx";
    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        navigator.msSaveBlob(blob, fileName);
    } else {
        const link = document.createElement('a');
        link.style.display = 'none';
        var href = URL.createObjectURL(blob);
        link.href = href;
        link.setAttribute('download', fileName);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(href);
    }
    proxy.Message.success("导出成功");
}
onMounted(()=>{
  loadDataList();
})

</script>

<style scoped>
.el-card {
  width: auto;
}
.el-form{
  .el-form-item{
    width: 300px;
  }
}
</style>