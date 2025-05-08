<template>
    <el-card>
      <template #header>
        <div>
          <el-form ref="searchFomRef" :model="searchForm" label-width="80px" :inline="true">           
            <el-form-item label="查询标题">
              <el-input v-model="searchForm.title" placeholder="支持模糊搜索" clearable></el-input>
            </el-form-item>
            <el-form-item label="查询姓名">
              <el-input v-model="searchForm.name" placeholder="支持模糊搜索" clearable></el-input>
            </el-form-item>
            <el-form-item label="查询时间">
              <el-input v-model="searchForm.time" placeholder="支持模糊搜索" clearable></el-input>
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
         <!-- id -->
        <template #slotNotice_id="{index,row}">
          {{ row.notice_id }}
        </template>
        <!-- 标题 -->
        <template #slotTitle="{index,row}">
          {{ row.title }}
        </template>
        <!-- 姓名 -->
        <template #slotName="{index,row}">
          {{ row.name }}
        </template>
        <!-- 公告内容 -->
        <template #slotcontent="{index,row}">
          {{ row.content }}
        </template>
        <template #slotTime="{index,row}">
          {{ row.time }}
        </template>
        <!-- 公告是否公开 -->
        <template #slotispublic="{index,row}">
          <div v-if="row.ispublic === 0" style="color: #F56C6C;">不公开</div>
          <div v-else-if="row.ispublic === 1" style="color: #67C23A;">公开</div>
        </template>
        <!-- 操作 -->
        <template #slotOperation="{index,row}">
          <el-link v-show="row.title!=0" type="primary"
            @click="router.push({name:'noticeCreateOrUpdate',query:{row:JSON.stringify(row)}})"
            style="margin-right: 20px">编辑</el-link>
          <el-link v-show="row.title!=0" type="danger" @click="deleteNotice(row,true)">删除</el-link>
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
      label: "id",
      prop: "notice_id",
      width: "100",
      scopedSlots: "slotNotice_id",
      fixed: false
    },
    {
      label: "标题",
      prop: "title",
      width: "200",
      scopedSlots: "slotTitle",
      fixed: false
    },
    {
      label: "发布人",
      prop: "name",
      width: "200",
      scopedSlots: "slotName",
      fixed: false
    },
    {
      label: "公告内容",
      prop: "content",
      width: "350",
      scopedSlots: "slotcontent",
      fixed: false
    },
    {
      label: "发布时间",
      prop: "time",
      width: "100",
      scopedSlots: "slotTime",
      fixed: false
    },
    {
      label: "是否公开",
      prop: "ispublic",
      width: "150",
      scopedSlots: "slotispublic",
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
 * 删除用户
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
      notice_id: row.notice_id
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
