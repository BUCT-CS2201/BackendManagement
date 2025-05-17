<template>
  <el-card>
    <template #header>
      <div>
        <el-form ref="searchFormRef" :model="searchForm" label-width="100px" :inline="true">
          <!-- 管理员姓名 -->
          <el-form-item label="管理员姓名">
            <el-input v-model="searchForm.adminName" placeholder="请输入" clearable />
          </el-form-item>
          <!-- 操作时间起始 -->
          <el-form-item label="操作时间起始">
            <el-date-picker
              v-model="searchForm.operationTimeStart"
              type="datetime"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="请选择"
            />
          </el-form-item>
          <!-- 操作时间结束 -->
          <el-form-item label="操作时间结束">
            <el-date-picker
              v-model="searchForm.operationTimeEnd"
              type="datetime"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="请选择"
            />
          </el-form-item>
          <!-- 操作按钮 -->
          <el-form-item style="margin-left: 980px;">
            <el-button type="primary" @click="clearSearchForm">清空</el-button>
            <el-button type="primary" @click="loadDataList">查询</el-button>
            <el-button type="primary" @click="router.push({ name: 'LogAdd' })">新增</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
      <Table :columns="columns" :fetch="loadDataList" :dataSource="tableData" :options="tableOptions">
      <!-- 表单列插槽插入 -->
      <!-- 日志id -->
      <template #slotLogId="{ index, row }">
        {{ row.logId }}
      </template>
      <!-- 管理员姓名 -->
      <template #slotAdminName="{ index, row }">
        {{ row.adminName }}
      </template>
      <!-- 操作内容 -->
      <template #slot_operation="{ index, row }">
        {{ row.operation }}
      </template>
      <!-- 操作时间 -->
      <template #slotOperationTime="{index,row}">
        {{ row.operationTime }}
      </template>
      <!-- 操作 -->
      <template #slotOperation="{index,row}">
        <el-link v-show="row.adminName!=0" type="danger" @click="deleteLog(row,true)">删除</el-link>
      </template>
    </Table>
  </el-card>
</template>

<script setup>
import { ref, getCurrentInstance,onMounted } from "vue";
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
    label: "日志id",
    prop: "logId",
    width: "200",
    scopedSlots: "slotLogId",
    fixed: false
  },
  {
    label: "管理员姓名",
    prop: "adminName",
    width: "200",
    scopedSlots: "slotAdminName",
    fixed: false
  },
  {
    label: "操作内容",
    prop: "operation",
    width: "250",
    scopedSlots: "slot_operation",
    fixed: false
  },
  {
    label: "操作时间",
    prop: "operationTime",
    width: "300",
    scopedSlots: "slotOperationTime",
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
    url: proxy.Api.loadLogList,
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
const deleteLog = async (row,needConfirm=false)=>{
  if(needConfirm){
    proxy.Confirm({
      message: "确定要删除日志吗？",
      okfun: ()=>deleteLog(row,false),
    });
    return;
  }
  let result = await proxy.Request.request({
    url: proxy.Api.deleteLog,
    params: {
      logId: row.logId,
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