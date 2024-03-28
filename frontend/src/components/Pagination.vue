<template>
  <div>
  <el-pagination
      layout="prev, pager, next"
      :current-page=page
      :page-size=pageSize
      :total=totalItems
      @current-change="setPage"
  >
  </el-pagination>
  </div>
</template>

<script>
export default {
  props: {
    page: '',
    pageSize: '',
    totalItems: '',
  },
  data() {
    return {
    };
  },
  methods: {
    setPage(val) {
      this.page = val;
    },
  },
};
</script>