<template>
    <el-card>
      <template #header>
        <div>
          <el-form ref="searchFomRef" :model="searchForm" label-width="100px" :inline="true">
            <el-form-item label="备份id">
              <el-input v-model="searchForm.id" placeholder="请输入" clearable></el-input>
            </el-form-item>   
            <el-form-item label="备份注释">
              <el-input v-model="searchForm.comment" placeholder="支持模糊搜索" clearable></el-input>
            </el-form-item>     
            <el-form-item label="管理员名称">
              <el-input v-model="searchForm.adminName" placeholder="支持模糊搜索" clearable></el-input>
            </el-form-item>
            <el-form-item label="备份时间起始">
              <el-date-picker v-model="searchForm.backupTimeStart" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
            </el-form-item>
            <el-form-item label="备份时间结束">
              <el-date-picker v-model="searchForm.backupTimeEnd" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
            </el-form-item>
            <el-row>
              <el-col style="margin-left: 980px; margin-top: -30px;">
                <el-button type="primary" @click="clearSearchForm">清空</el-button>
                <el-button type="primary" @click="loadDataList">查询</el-button>
                <el-button type="primary" @click="router.push({ name: 'databaseCreateOrUpdate' })">备份</el-button>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </template>
      <Table :columns="columns" :fetch="loadDataList" :dataSource="tableData" :options="tableOptions">
        <!-- 表单列插槽插入 -->
        <!-- 备份id -->
        <template #slotId="{index,row}">
          {{ row.id }}
        </template>
        <!-- 管理员名称 -->
        <template #slotAdminName="{index,row}">
          {{ row.adminName }}
        </template>
        <!-- 备份注释 -->
        <template #slotComment="{index,row}">
          {{ row.comment }}
        </template>
        <!-- 备份路径 -->
        <template #slotPath="{index,row}">
          {{ row.path }}
        </template>
        <!-- 备份时间 -->
        <template #slotCreateTime="{index,row}">
          {{ row.createTime }}
        </template>
        <!-- 操作 -->
      <template #slotOperation="{index,row}">
        <el-link type="warning" style="margin-right: 20px;" @click="recover(row,true)">恢复</el-link>
        <el-link type="primary"
          @click="router.push({name:'databaseCreateOrUpdate',query:{row:JSON.stringify(row)}})"
          style="margin-right: 20px">编辑</el-link>
        <el-link type="danger" @click="deleteDataBaseBackup(row,true)">删除</el-link>
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
      label: "备份id",
      prop: "id",
      width: "100",
      scopedSlots: "slotId",
      fixed: false
    },
    {
      label: "管理员名称",
      prop: "adminName",
      width: "150",
      scopedSlots: "slotAdminName",
      fixed: false
    },
    {
      label: "备份注释",
      prop: "comment",
      width: "200",
      scopedSlots: "slotComment",
      fixed: false
    },
    {
      label: "备份路径",
      prop: "path",
      width: "900",
      scopedSlots: "slotPath",
      fixed: false
    },
    {
      label: "备份时间",
      prop: "createTime",
      width: "200",
      scopedSlots: "slotCreateTime",
      fixed: false
    },
    {
      label: "操作",
      width: "250",
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
    url: proxy.Api.loadDataBaseList,
    params: params,
  });
  if (!result) {
    return;
  }
  Object.assign(tableData.value, result.data);
};
/**
 * 删除备份
 */
const deleteDataBaseBackup = async (row,needConfirm=false)=>{
  if(needConfirm){
    proxy.Confirm({
      message: "确定要删除吗？",
      okfun: ()=>deleteDataBaseBackup(row,false),
    });
    return;
  }
  let result = await proxy.Request.request({
    url: proxy.Api.deleteDataBaseBackup,
    params: {
      id: row.id,
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
