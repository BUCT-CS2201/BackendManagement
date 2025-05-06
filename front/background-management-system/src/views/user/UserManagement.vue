<template>
  <el-card>
    <template #header>
      <div>
        <el-form ref="searchFomRef" :model="searchForm" label-width="80px" :inline="true">
          <el-form-item label="用户id">
            <el-input v-model="searchForm.userId" placeholder="请输入" clearable></el-input>
          </el-form-item>
          <el-form-item label="电话号码">
            <el-input v-model="searchForm.phoneNumber" placeholder="支持模糊搜索" clearable></el-input>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="searchForm.name" placeholder="支持模糊搜索" clearable></el-input>
          </el-form-item>
          <el-form-item label="身份证号">
            <el-input v-model="searchForm.idNumber" placeholder="支持模糊搜索" clearable></el-input>
          </el-form-item>
          <el-form-item label="角色类型">
            <el-select v-model="searchForm.roleType" placeholder="请选择" clearable>
              <el-option label="管理员" value="1"></el-option>
              <el-option label="用户" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="账户状态">
            <el-select v-model="searchForm.accountStatus" placeholder="请选择" clearable>
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-row>
            <el-col style="margin-left: 950px;">
              <el-button type="primary" @click="clearSearchForm">清空</el-button>
              <el-button type="primary" @click="loadDataList">查询</el-button>
              <el-button type="primary" @click="router.push({ name: 'userCreateOrUpdate' })">新增</el-button>
              <!-- <el-button type="primary" @click="exportExcel">导出</el-button> -->
            </el-col>
          </el-row>
        </el-form>
      </div>
    </template>
    <Table :columns="columns" :fetch="loadDataList" :dataSource="tableData" :options="tableOptions">
      <!-- 表单列插槽插入 -->
      <!-- 用户id -->
      <template #slotUserId="{ index, row }">
        {{ row.userId }}
      </template>
      <!-- 电话 -->
      <template #slotPhoneNumber="{ index, row }">
        {{ row.phoneNumber }}
      </template>
      <!-- 姓名 -->
      <template #slotName="{index,row}">
        {{ row.name }}
      </template>
      <!-- 身份证号 -->
      <template #slotIdNumber="{index,row}">
        {{ row.idNumber }}
      </template>
      <!-- 角色类型 -->
      <template #slotRoleType="{index,row}">
        <div>{{ row.roleType == 1 ? "管理员" : "用户" }}</div>
      </template>
      <!-- 账户状态类型 -->
      <template #slotAccountStatus="{index,row}">
        <div v-if="row.accountStatus === 0" style="color: #F56C6C;">禁用</div>
        <div v-else-if="row.accountStatus === 1" style="color: #67C23A;">启用</div>
      </template>
      <!-- 评论状态类型 -->
      <template #slotCommentStatus="{index,row}">
        <div v-if="row.commentStatus === 0" style="color: #F56C6C;">禁止</div>
        <div v-else-if="row.commentStatus === 1" style="color: #67C23A;">允许</div>
      </template>
      <!-- 操作 -->
      <template #slotOperation="{index,row}">
        <el-link v-show="row.roleType==0" type="primary"
          @click="router.push({name:'userCreateOrUpdate',query:{row:JSON.stringify(row)}})"
          style="margin-right: 20px">编辑</el-link>
        <el-link v-show="row.roleType==0" type="danger" @click="deleteUser(row,true)">删除</el-link>
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
    label: "用户id",
    prop: "userId",
    width: "200",
    scopedSlots: "slotUserId",
    fixed: false
  },
  {
    label: "电话号码",
    prop: "phoneNumber",
    width: "200",
    scopedSlots: "slotPhoneNumber",
    fixed: false
  },
  {
    label: "姓名",
    prop: "name",
    width: "300",
    scopedSlots: "slotName",
    fixed: false
  },
  {
    label: "身份证号",
    prop: "idNumber",
    width: "300",
    scopedSlots: "slotIdNumber",
    fixed: false
  },
  {
    label: "角色类型",
    prop: "roleType",
    width: "150",
    scopedSlots: "slotRoleType",
    fixed: false
  },
  {
    label: "账户状态",
    prop: "accountStatus",
    width: "150",
    scopedSlots: "slotAccountStatus",
    fixed: false
  },
  {
    label: "评论状态",
    prop: "commentStatus",
    width: "150",
    scopedSlots: "slotCommentStatus",
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
    url: proxy.Api.loadUserList,
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
const deleteUser = async (row,needConfirm=false)=>{
  if(needConfirm){
    proxy.Confirm({
      message: "确定要删除用户吗？",
      okfun: ()=>deleteUser(row,false),
    });
    return;
  }
  let result = await proxy.Request.request({
    url: proxy.Api.deleteUser,
    params: {
      userId: row.userId
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
