<template>
    <el-card>
        <template #header>
            <div>
                <el-form ref="searchFomRef" :model="searchForm" label-width="100px" :inline="true">
                    <el-form-item label="图片id">
                        <el-input v-model="searchForm.imageId" placeholder="请输入" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="用户id">
                        <el-input v-model="searchForm.userId" placeholder="请输入" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="关联评论id">
                        <el-input v-model="searchForm.commentId" placeholder="请输入" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="上传时间起始">
                        <el-date-picker v-model="searchForm.uploadTimeStart" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
                    </el-form-item>
                    <el-form-item label="上传时间结束">
                        <el-date-picker v-model="searchForm.uploadTimeEnd" type="datetime"
                            value-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择" />
                    </el-form-item>
                    <el-form-item label="审核状态">
                        <el-select v-model="searchForm.status" placeholder="请选择" clearable>
                            <el-option label="审核中" value="0"></el-option>
                            <el-option label="已过审" value="1"></el-option>
                            <el-option label="未过审" value="2"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-row>
                        <el-col style="margin-left: 1050px; margin-top: -25px;">
                            <el-button type="primary" @click="clearSearchForm">清空</el-button>
                            <el-button type="primary" @click="loadDataList">查询</el-button>
                            <!-- <el-button type="primary" @click="exportExcel">导出</el-button> -->
                        </el-col>
                    </el-row>
                </el-form>
            </div>
        </template>
        <Table :columns="columns" :fetch="loadDataList" :dataSource="tableData" :options="tableOptions">
            <!-- 表单列插槽插入 --> 
            <!-- 图片id -->
            <template #slotImageId="{ index, row }">
                {{ row.imageId }}
            </template>
            <!-- 评论id -->
            <template #slotCommentId="{ index, row }">
                {{ row.commentId }}
            </template>
            <!-- 用户Id -->
            <template #slotUserId="{ index, row }">
                {{ row.userId }}
            </template>
            <!-- 图片内容 -->
            <template #slotContent="{ index, row }">
                <el-image style="width: 100px; height: 100px" :src="loadImageURL(row.imageId,row.imageSuffix)" :fit="fit"></el-image>
            </template>
            <!-- 审核状态 -->
            <template #slotStatus="{ index, row }">
                <div v-if="row.status === 0" style="color: #E6A23C;">审核中</div>
                <div v-else-if="row.status === 1" style="color: #67C23A;">已过审</div>
                <div v-else-if="row.status === 2" style="color: #F56C6C;">未过审</div>
            </template>
            <!-- 上传时间 -->
            <template #slotUploadTime="{ index, row }">
                {{ row.uploadTime }}
            </template>
            <!-- 状态修改时间 -->
            <template #slotUpdateTime="{ index, row }">
                {{ row.updateTime }}
            </template>
            <!-- 操作 -->
            <template #slotOperation="{ index, row }">
                <el-dropdown @command="(status) => updateImageStatus(row.imageId,row.commentId,status)">
                    <el-link style="color: #409EFF">修改图片状态</el-link>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="0">审核中</el-dropdown-item>
                            <el-dropdown-item command="1">已过审</el-dropdown-item>
                            <el-dropdown-item command="2">未过审</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </template>
        </Table>
    </el-card>
</template>

<script setup>
import { ref, getCurrentInstance, onMounted } from "vue";
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
        label: "图片Id",
        prop: "imageId",
        width: "100",
        scopedSlots: "slotImageId",
        fixed: false
    },
    {
        label: "评论Id",
        prop: "commentId",
        width: "100",
        scopedSlots: "slotCommentId",
        fixed: false
    },
    {
        label: "用户Id",
        prop: "userId",
        width: "100",
        scopedSlots: "slotUserId",
        fixed: false
    },
    {
        label: "评论内容",
        prop: "content",
        width: "300",
        scopedSlots: "slotContent",
    },
    {
        label: "审核状态",
        prop: "status",
        width: "100",
        scopedSlots: "slotStatus",
    },
    {
        label: "上传时间",
        prop: "uploadTime",
        width: "100",
        scopedSlots: "slotUploadTime",
        fixed: false
    },
    {
        label: "状态修改时间",
        prop: "updateTime",
        width: "150",
        scopedSlots: "slotUpdateTime",
        fixed: false
    },
    {
        label: "操作",
        width: "200",
        scopedSlots: "slotOperation",
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
        url: proxy.Api.loadImageList,
        params: params,
    });
    if (!result) {
        return;
    }
    Object.assign(tableData.value, result.data);
};
/**
 * 加载图片URL
 */
const loadImageURL = (imageId,imageSuffix)=>{
    var url =  proxy.Api.devDomain+proxy.Api.loadImageBaseURL+"?imageName="+imageId+"."+imageSuffix;
    console.log("url:"+url)
    return url
}
/**
 * 修改图片状态
 */
const updateImageStatus = async(imageId,commentId,status)=> {
    let result = await proxy.Request.request({
        url: proxy.Api.updateImageStatus,
        params: {
            imageId: imageId,
            commentId: commentId,
            status: status,
        }
    });
    if (!result) {
        return;
    }
    proxy.Message.success("修改成功");
    loadDataList(); 
}
/**
 * 清空查询条件
 */
const clearSearchForm = () => {
    searchForm.value = {};
}
onMounted(() => {
    loadDataList();
})

</script>

<style scoped>
.el-card {
    width: auto;
}

.el-form {
    .el-form-item {
        width: 300px;
    }
    .status-change-text {
  color: #409EFF;
  cursor: pointer;
}

}
</style>