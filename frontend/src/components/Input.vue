<template>
    <div class="modal-content">
      <div class="mb-8"><span @click="close" class="close mr-2">&times;</span></div>
      <input type="text" :placeholder="placeholder" class="rounded-none rounded-r-lg bg-gray-50 border text-gray-900 focus:ring-blue-500 focus:border-blue-500 flex-1 min-w-0 w-5/6 text-sm border-gray-300 p-2.5  dark:bg-gray-700 dark:border-gray-600
      dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 mb-5 ml-4 mr-4" v-model:value="keyword">
      <span @click="buttonClick" class="button">{{ this.buttonName }}</span>
      <slot></slot>
<!--      <div class="w-full flex p-3 pl-4 items-center hover:bg-gray-300 rounded-lg cursor-pointer" v-for="item in quizSummary" :key="item.quizSeq" @click="choice(item.quizSeq, item.title, item.singer)">-->
<!--        <div>-->
<!--          <div class="font-bold text-lg">{{item.title}}</div>-->
<!--          <div class="text-xs text-gray-500">-->
<!--            <span class="mr-2" >가수 : {{item.singer}}</span>-->
<!--            <span class="mr-2">시작 시간 : {{item.startTime}}</span>-->
<!--            <span class="mr-2">끝 시간 : {{item.endTime}}</span>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--      <Pagination :total-items="this.totalPage" :page-size="this.pageSize" @page-change="handlePageChange"/>-->
    </div>
</template>

<script>
import axios from "axios";
// import Pagination from "@/components/Pagination";

export default {
  name: "Input",
  components: {},
  data() {
    return {
      keyword : '',
      // quizSummary: [],
      // quizSeq: '',
      // currentPage: 1,
      // totalPage: '',
      // pageSize: 4,
    }
  },
  props: {
    placeholder : '',
    buttonName : '',
    onClick: Function,
    // show: {
    //   type: Boolean,
    //   required: true,
    // },
  },
  methods: {
    close() {
      this.$emit('close');
    },
    buttonClick() {
      if (this.onClick) {
        this.onClick();
      }
    }
  }
}
</script>

<style scoped>
.modal {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #fefefe;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 600px; /* 모달의 최대 너비 설정 */
  position: relative;
}

.close {
  color: #aaa;
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 20px;
  cursor: pointer;
}
</style>