<template>
    <el-aside>
        <el-menu router :unique-opened="true">
            <el-menu-item index="/manage/user">
                <el-icon style="color:#0483d4;margin-left: 8px;">
                    <User style="width: 100px;height: 50px;" />
                </el-icon>
                <span style="margin-left: 13px;">用户管理</span>
            </el-menu-item>

            <el-sub-menu index="ReviewManagement">
                <template #title>
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <View style="width: 100px;height: 50px;" />
                    </el-icon>
                    <span  style="margin-left: 13px; font-size: 18px; color: #0483d4;">审核管理</span>
                </template>

                <el-menu-item index="/manage/commentReview">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <ChatDotSquare style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">评论审核</span>
                </el-menu-item>
                <el-menu-item index="/manage/imageReview">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <Picture style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">图片审核</span>
                </el-menu-item> 
                <el-menu-item index="/manage/videoReview">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <VideoPlay style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">视频审核</span>
                </el-menu-item>
            </el-sub-menu>
            
            <el-sub-menu index="DataManagement">
                <template #title>
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <Refresh style="width: 100px;height: 50px;" />
                    </el-icon>
                    <span  style="margin-left: 13px; font-size: 18px; color: #0483d4;">数据管理</span>
                </template>

                <el-menu-item index="/manage/museum">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <OfficeBuilding style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">博物馆</span>
                </el-menu-item>

                <el-menu-item index="/manage/relic">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <Collection style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">藏品信息</span>
                </el-menu-item> 

                <el-menu-item index="/manage/notice">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <Document style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">公告</span>
                </el-menu-item>
            </el-sub-menu>

            <el-menu-item index="/manage/log">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <ChatSquare style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">管理员日志</span>
                </el-menu-item>

            <el-menu-item index="/manage/database">
                <el-icon style="color:#0483d4;margin-left: 8px;">
                    <Suitcase style="width: 100px;height: 50px;" />
                </el-icon>
                <span style="margin-left: 13px;">数据备份与恢复</span>
            </el-menu-item>
        </el-menu>
    </el-aside>
</template>

<script setup>

</script>

<style>
.el-aside {
    background-color: rgb(232.8, 233.4, 234.6);
    width: 200px;
}

.el-menu {
    background-color: rgb(232.8, 233.4, 234.6);
    border-right: none;
}
.el-menu-item{
    font-size: 18px;
    color: #0483d4;
}

.el-menu-item:hover , .el-sub-menu__title:hover{
    background-color: rgb(199.5, 201, 204);
}

.el-menu-item.is-active{
    background: rgb(199.5, 201, 204) !important;
}

</style>