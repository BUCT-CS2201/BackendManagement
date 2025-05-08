<script setup>
const isEdit = ref(false); // 是否为编辑状态（通过路由参数判断）

onMounted(() => {
  const row = route.query.row ? JSON.parse(route.query.row) : null;
  if (row) {
    isEdit.value = true; // 编辑状态
    dataForm.museumId = row.museumId; // 编辑时赋值
  } else {
    dataForm.museumId = null; // 新增时强制清空
  }
});
</script>
