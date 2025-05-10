<template>
  <div>
    <el-form ref="searchFomRef" :model="searchForm" label-width="80px" :inline="true">
      <el-form-item label="文物名称:">
        <el-input v-model="searchForm.name" placeholder="请输入" clearable></el-input>
      </el-form-item>
      <el-form-item label="文物id:">
        <el-input v-model="searchForm.relicId" placeholder="请输入" clearable></el-input>
      </el-form-item>
      <el-form-item label="文物朝代:">
        <el-select v-model="selectedEra" placeholder="请选择时代" filterable clearable>
          <el-option
              v-for="item in eras"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="文物分类:">
        <el-select v-model="selectedType" placeholder="请选择类型" filterable clearable>
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
    <template #slotMaterials="{ index, row }">
      {{ row.materials }}
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
  </Table>
</template>

<script>
export default {
  data() {
    return {
      selectedEra: '', // 年代选中的值
      selectedType: '',
      eras: [ // 下拉选项数据
        { value: 'newStoneAge', label: '新石器时代' },
        { value: 'longshanCulture', label: '龙山文化' },
        { value: 'shang', label: '商' },
        { value: 'shangEarly', label: '商代前期' },
        { value: 'shangLate', label: '商代后期' },
        { value: 'zhou', label: '周' },
        { value: 'westernZhou', label: '西周' },
        { value: 'westernZhouEarly', label: '西周早期' }
      ],
      types: [ // 下拉选项数据
        { value: '陶瓷', label: '陶瓷' },
        { value: '绘画', label: '绘画' },
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
    prop: "materials",
    width: "200",
    scopedSlots: "slotMaterials",
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


</script>

<style  scoped>
.el-form{
  .el-form-item{
    width: 300px;
  }
}
</style>
