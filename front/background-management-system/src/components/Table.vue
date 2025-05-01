<template>
    <div>
        <el-table ref="dataTable" :data="dataSource.list || []" :height="tableHeight" 
        :border="options.border" highlight-current-row
        @row-click="handleRowClick" @selection-change="handleSelectionChange">
            <!-- 选择框 -->
            <!-- <el-table-column type="selection" width="55" /> -->
            <!-- 序号 -->
            <el-table-column v-if="options.showIndex" type="index" label="序号" width="60" align="center"></el-table-column>
            <!-- 数据列 -->
            <template v-for="(column,index) in columns">
                <!-- 有插槽 -->
                <template v-if="column.scopedSlots">
                    <el-table-column :key="index" :prop="column.prop" :label="column.label" :align="column.align || 'center'" :width="column.width" :fixed="column.fixed">
                        <template #default="scope" >
                            <slot :name="column.scopedSlots" :index="scope.$index" :row="scope.row"></slot>
                        </template>
                    </el-table-column>
                </template>
                <!-- 无插槽 -->
                <template v-else>
                    <el-table-column :key="index" :prop="column.prop" :label="column.label" :align="column.align || 'center'" :width="column.width" :fixed="column.fixed">
                    </el-table-column>
                </template>
            </template>
        </el-table>
        <!-- 分页 -->
        <div v-if="showPagination">
            <el-pagination v-if="dataSource.pageTotal" background :total="dataSource.pageTotal"
            :page-sizes="[10,50,100,1000]" :page-size="dataSource.pageSize" :current-page.sync="dataSource.pageNo"
            layout="total,sizes,prev,pager,next,jumper" @size-change="handlePageSizeChange"
            @current-change="handlePageNoChange" style="text-align: right;margin-top: 15px;">
            </el-pagination>
        </div>
    </div>
</template>
<script setup>
    import {ref} from 'vue'

    // 接收父组件传入的函数
    const emit = defineEmits(['rowSelected','rowClick'])
    // 接收父组件传入的数据
    const props = defineProps({
        // 数据
        dataSource: Object,
        // 是否分页
        showPagination:{
            type:Boolean,
            default:true
        },
        // 表格样式
        options:{
            type:Object,
            default: {}
        },
        // 列数组
        columns: Array,
        // 获取数据的函数
        fetch: Function ,
        // 是否初始化加载数据
        initFetch: {
            type:Boolean,
            default:true
        },
        // 选中时要执行的函数
        selected: Function
    })

    // 表高
    const tableHeight = ref(
        props.options.tableHeight?props.options.tableHeight : 'auto'
    )

    // 初始化数据
    const init = () => {
        if(props.initFetch){
            props.fetch()
        }
    }
    init()

    // 表数据
    const dataTable = ref()
    // 清除选中
    const clearSelection = () => {
        dataTable.value.clearSelection()
    }
    // 行选中
    const setCurrentRow = (rowKey,rowValue)=>{
        let row = props.dataSource.list.find(item=>{
            return item[rowKey] === rowValue
        })
        dataTable.value.setCurrentRow(row)
    }
    // 将子组件暴露给父组件
    defineExpose({clearSelection,setCurrentRow})

    // 行点击
    const handleRowClick = (row)=>{
        // 执行父组件传入的函数
        emit('rowClick',row)
    }
    // 行多选
    const handleSelectionChange = (row)=>{
        // 执行父组件传入的函数
        emit('rowSelected',row)
    }
    // 切换每页大小
    const handlePageSizeChange = (size)=>{
        props.dataSource.pageSize = size
        props.dataSource.pageNo = 1
        // 重新加载数据
        props.fetch()
    }
    // 切换页码
    const handlePageNoChange = (pageNo)=>{
        props.dataSource.pageNo = pageNo
        // 重新加载数据
        props.fetch()
    }
    // 复选事件
    const selectedHandler = (row,index)=>{
        if(props.selected){
            props.selected(row,index)
        }
    }
</script>

<style scoped>

</style>