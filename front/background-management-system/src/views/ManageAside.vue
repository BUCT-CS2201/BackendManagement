<template>
    <el-aside>
        <el-menu router>
            <el-menu-item index="/manage/user">
                <el-icon style="color:#0483d4;margin-left: 8px;">
                    <User style="width: 100px;height: 50px;" />
                </el-icon>
                <span style="margin-left: 13px;">用户管理</span>
            </el-menu-item>
            <el-menu-item index="/manage/review">
                <el-icon style="color:#0483d4;margin-left: 8px;">
                    <Unlock style="width: 100px;height: 50px;" />
                </el-icon>
                <span style="margin-left: 13px;">讲解审核</span>
            </el-menu-item>
            
            <el-sub-menu>
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

                <el-menu-item index="/manage/comment">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <ChatSquare style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">用户点评</span>
                </el-menu-item>

                <el-menu-item index="/manage/news">
                    <el-icon style="color:#0483d4;margin-left: 8px;">
                        <Document style="width: 20px; height: 20px;" />
                    </el-icon>
                    <span style="margin-left: 8px;">新闻</span>
                </el-menu-item>
            </el-sub-menu>

            <el-menu-item index="/manage/backup">
                <el-icon style="color:#0483d4;margin-left: 8px;">
                    <Suitcase style="width: 100px;height: 50px;" />
                </el-icon>
                <span style="margin-left: 13px;">数据备份</span>
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