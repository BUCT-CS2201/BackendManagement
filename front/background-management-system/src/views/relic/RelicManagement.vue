<template>
  <el-card>
    <template #header>
      <div>
        <el-form ref="searchFomRef" :model="searchForm" label-width="80px" :inline="true">
          <el-form-item label="文物名称:">
            <el-input v-model="searchForm.name" placeholder="请输入" clearable></el-input>
          </el-form-item>
          <el-form-item label="文物id:">
            <el-input v-model="searchForm.relicId" placeholder="请输入" clearable></el-input>
          </el-form-item>
          <el-form-item label="文物朝代:">
            <el-select v-model="searchForm.dynasty" placeholder="请选择时代" filterable clearable>
              <el-option
                  v-for="item in eras"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="文物分类:">
            <el-select v-model="searchForm.type" placeholder="请选择类型" filterable clearable>
              <el-option
                  v-for="item in types"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-row>
            <el-col style="margin-left: 950px;">
              <el-button type="primary" @click="clearSearchForm">清空</el-button>
              <el-button type="primary" @click="loadDataList">查询</el-button>
              <el-button type="primary" @click="router.push({ name: 'relicCreateOrUpdate' })">新增</el-button>
              <!-- <el-button type="primary" @click="exportExcel">导出</el-button> -->
            </el-col>
          </el-row>
        </el-form>
      </div>
    </template>
    <Table :columns="columns" :fetch="loadDataList" :dataSource="tableData" :options="tableOptions">
      <!-- 文物id-->
      <template #slotRelicId="{ index, row }">
        {{ row.relicId }}
      </template>
      <!--文物名称-->
      <template #slotName="{ index, row }">
        {{ row.name }}
      </template>
      <!--文物分类-->
      <template #slotType="{ index, row }">
        {{ row.type }}
      </template>
      <!--文物描述-->
      <template #slotDescription="{ index, row }">
        {{ row.description }}
      </template>
      <!--文物尺寸-->
      <template #slotSize="{ index, row }">
        {{ row.size }}
      </template>
      <!--文物材质-->
      <template #slotMatrials="{ index, row }">
        {{ row.matrials }}
      </template>
      <!--文物朝代-->
      <template #slotDynasty="{ index, row }">
        {{ row.dynasty }}
      </template>
      <!--文物作者-->
      <template #slotAuthor="{ index, row }">
        {{ row.author }}
      </template>
      <!--文物入库时间-->
      <template #slotEntryTime="{ index, row }">
        {{ row.entryTime }}
      </template>
      <!-- 操作 -->
      <template #slotOperation="{index,row}">
        <el-link type="primary"
                 @click="router.push({name:'relicCreateOrUpdate',query:{row:JSON.stringify(row)}})"
                 style="margin-right: 20px">编辑</el-link>
        <el-link type="danger" @click="deleteRelic(row,true)">删除</el-link>
      </template>
    </Table>
  </el-card>

</template>

<script>
export default {
  data() {
    return {
      dynasty: '', // 年代选中的值
      type: '',
      eras: [ // 下拉选项数据
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
      ],
      types: [ // 下拉选项数据
        { value: '陶瓷', label: '陶瓷' },
        { value: '绘画', label: '绘画' },
        { value: '册页', label: '册页' },
        { value: '扇', label: '扇|扇面' },
        { value: '算盘', label: '算盘' },
        { value: '法书', label: '法书' },
        { value: '铭刻', label: '铭刻' },
        { value: '青铜器', label: '青铜器' },
        { value: '珐琅', label: '珐琅' },
        { value: '漆器', label: '漆器' },
        { value: '雕塑', label: '雕塑' }
      ]
    };
  }
};
</script>

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
    label: "文物id",
    prop: "relicId",
    width: "200",
    scopedSlots: "slotRelicId",
    fixed: false
  },
  {
    label: "文物名称",
    prop: "name",
    width: "200",
    scopedSlots: "slotName",
    fixed: false
  },
  {
    label: "文物分类",
    prop: "type",
    width: "200",
    scopedSlots: "slotType",
    fixed: false
  },
  {
    label: "文物描述",
    prop: "description",
    width: "200",
    scopedSlots: "slotDescription",
    fixed: false
  },
  {
    label: "文物尺寸",
    prop: "size",
    width: "200",
    scopedSlots: "slotSize",
    fixed: false
  },
  {
    label: "文物材质",
    prop: "matrials",
    width: "200",
    scopedSlots: "slotMatrials",
    fixed: false
  },
  {
    label: "文物朝代",
    prop: "dynasty",
    width: "200",
    scopedSlots: "slotDynasty",
    fixed: false
  },
  {
    label: "文物作者",
    prop: "author",
    width: "200",
    scopedSlots: "slotAuthor",
    fixed: false
  },
  {
    label: "文物入库时间",
    prop: "entryTime",
    width: "200",
    scopedSlots: "slotEntryTime",
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
    url: proxy.Api.loadRelicList,
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
const deleteRelic = async (row,needConfirm=false)=>{
  if(needConfirm){
    proxy.Confirm({
      message: "确定要删除文物吗？",
      okfun: ()=>deleteRelic(row,false),
    });
    return;
  }
  let result = await proxy.Request.request({
    url: proxy.Api.deleteRelic,
    params: {
      relicId: row.relicId,
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


