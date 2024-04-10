<template>
  <div>
  <el-pagination
      layout="prev, pager, next"
      :current-page = page
      :page-size = pageSize
      :total=totalItems
      @current-change="setPage"
  >
  </el-pagination>
  </div>
</template>
<script>
export default {
  props: {
    pageSize: '',
    totalItems: '',
  },
  data() {
    return {
      page: 1,
    };
  },
  methods: {
    setPage(val) {
      this.$emit('page-change', val);
      this.page = val;
    },
  },
};
</script>