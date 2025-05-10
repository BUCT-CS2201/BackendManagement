<template>
    <el-card>
      <template #header>
        <div>
          <el-form ref="searchFomRef" :model="searchForm" label-width="100px" :inline="true">
            <el-form-item label="公告id">
              <el-input v-model="searchForm.noticeId" placeholder="请输入" clearable></el-input>
            </el-form-item>
            <el-form-item label="博物馆id">
              <el-input v-model="searchForm.museumId" placeholder="请输入" clearable></el-input>
            </el-form-item>           
            <el-form-item label="公告标题">
              <el-input v-model="searchForm.noticeTitle" placeholder="支持模糊搜索" clearable></el-input>
            </el-form-item>
            <el-form-item label="发布人">
              <el-input v-model="searchForm.noticeAuthor" placeholder="支持模糊搜索" clearable></el-input>
            </el-form-item>
            <el-form-item label="公告时间起始">
              <el-date-picker v-model="searchForm.noticeTimeStart" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
            </el-form-item>
            <el-form-item label="公告时间结束">
              <el-date-picker v-model="searchForm.noticeTimeEnd" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
            </el-form-item>
            <el-row>
              <el-col style="margin-left: 900px;">
                <el-button type="primary" @click="clearSearchForm">清空</el-button>
                <el-button type="primary" @click="loadDataList">查询</el-button>
                <el-button type="primary" @click="router.push({ name: 'noticeCreateOrUpdate' })">新增</el-button>
                <!-- <el-button type="primary" @click="exportExcel">导出</el-button> -->
              </el-col>
            </el-row>
          </el-form>
        </div>
      </template>
      <Table :columns="columns" :fetch="loadDataList" :dataSource="tableData" :options="tableOptions">
        <!-- 表单列插槽插入 -->
        <!-- 公告id -->
        <template #slotNoticeId="{index,row}">
          {{ row.noticeId }}
        </template>
        <!-- 博物馆名称 -->
        <template #slotMuseumName="{index,row}">
          {{ row.museumName }}
        </template>
        <!-- 公告标题 -->
        <template #slotNoticeTitle="{index,row}">
          {{ row.noticeTitle }}
        </template>
        <!-- 发布人 -->
        <template #slotNoticeAuthor="{index,row}">
          {{ row.noticeAuthor }}
        </template>
        <!-- 公告内容 -->
        <template #slotNoticeContent="{index,row}">
          {{ row.noticeContent }}
        </template>
        <!-- 公告时间 -->
        <template #slotNoticeTime="{index,row}">
          {{ row.noticeTime }}
        </template>
        <!-- 操作 -->
        <!-- 操作 -->
      <template #slotOperation="{index,row}">
        <el-link type="primary"
          @click="router.push({name:'noticeCreateOrUpdate',query:{row:JSON.stringify(row)}})"
          style="margin-right: 20px">编辑</el-link>
        <el-link type="danger" @click="deleteNotice(row,true)">删除</el-link>
      </template>
      </Table>
    </el-card>
  </template>
  
  <script setup>
  import { ref, getCurrentInstance,onMounted } from "vue";
  import { useRouter } from "vue-router";
  import Table from "@/components/Table.vue";
  
  const { proxy } = getCurrentInstance();
  // 路由
  const router = useRouter();
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
      label: "公告id",
      prop: "noticeId",
      width: "100",
      scopedSlots: "slotNoticeId",
      fixed: false
    },
    {
      label: "博物馆名称",
      prop: "museumName",
      width: "100",
      scopedSlots: "slotMuseumName",
      fixed: false
    },
    {
      label: "标题",
      prop: "noticeTitle",
      width: "200",
      scopedSlots: "slotNoticeTitle",
      fixed: false
    },
    {
      label: "发布人",
      prop: "noticeAuthor",
      width: "200",
      scopedSlots: "slotNoticeAuthor",
      fixed: false
    },
    {
      label: "公告内容",
      prop: "noticeContent",
      width: "350",
      scopedSlots: "slotNoticeContent",
      fixed: false
    },
    {
      label: "发布时间",
      prop: "noticeTime",
      width: "100",
      scopedSlots: "slotNoticeTime",
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
    url: proxy.Api.loadNoticeList,
    params: params,
  });
  if (!result) {
    return;
  }
  Object.assign(tableData.value, result.data);
};
/**
 * 删除公告
 */
const deleteNotice = async (row,needConfirm=false)=>{
  if(needConfirm){
    proxy.Confirm({
      message: "确定要删除吗？",
      okfun: ()=>deleteNotice(row,false),
    });
    return;
  }
  let result = await proxy.Request.request({
    url: proxy.Api.deleteNotice,
    params: {
      noticeId: row.noticeId
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
    const userList = (tableData.value.list || []).map((item) => item);
    if (userList.length === 0) {
        proxy.Message.warning("当前没有可导出的用户数据");
        return;
    }
    let result = await proxy.Request.requestJSON({
        url: proxy.Api.exportUserExcel,
        params: userList, 
        responseType: 'arraybuffer'
    });
    if(!result){
        return;
    }
    let blob = new Blob([result.data], { type: 'application/vnd.ms-excel' });
    const fileName = "用户数据excel.xlsx";
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
