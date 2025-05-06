<template>
    <el-card>
        <template #header>
            <div>
                <el-form ref="searchFomRef" :model="searchForm" label-width="100px" :inline="true">
                    <el-form-item label="管理员姓名">
                        <el-input v-model="searchForm.adminName" placeholder="请输入" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="操作时间起始">
                        <el-date-picker v-model="searchForm.operationTimeStart" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
                    </el-form-item>
                    <el-form-item label="操作时间结束">
                        <el-date-picker v-model="searchForm.operationTimeEnd" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
                    </el-form-item>
                </el-form>
            </div>
        </template>
    </el-card>
</template>

<script setup>
import { ref} from "vue";

// 查询表单
const searchForm = ref({});

</script>

<style scoped>
.el-card {
    width: auto;
}

.el-form {
    .el-form-item {
        width: 300px;
    }
}
</style>