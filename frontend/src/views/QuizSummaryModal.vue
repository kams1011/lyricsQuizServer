<template>
  <div v-if="show" class="modal">
    <div class="modal-content">
      <div class="mb-8"><span @click="close" class="close mr-2">&times;</span></div>
<!--      <input type="search" class="w-full px-4 py-1 text-gray-800 rounded-full focus:outline-none border-gray-300" name="q">-->
      <input type="text" placeholder="제목을 입력하세요" class="rounded-none rounded-r-lg bg-gray-50 border text-gray-900 focus:ring-blue-500 focus:border-blue-500 flex-1 min-w-0 w-5/6 text-sm border-gray-300 p-2.5  dark:bg-gray-700 dark:border-gray-600
      dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 mb-5 ml-4 mr-4" ref="keyword">
      <span @click="getQuizList" class="button">검색</span>
      <div class="w-full flex p-3 pl-4 items-center hover:bg-gray-300 rounded-lg cursor-pointer" v-for="item in quizSummary" :key="item.quizSeq" @click="choice(item.quizSeq, item.title, item.singer)">
        <div>
          <div class="font-bold text-lg">{{item.title}}</div>
          <div class="text-xs text-gray-500">
            <span class="mr-2" >가수 : {{item.singer}}</span>
            <span class="mr-2">시작 시간 : {{item.startTime}}</span>
            <span class="mr-2">끝 시간 : {{item.endTime}}</span>
          </div>
        </div>
      </div>

      <slot></slot>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "QuizSummaryModal",
  data() {
    return {
      quizSummary: [],
      quizSeq : '',
    }
  },
  props: {
    show: {
      type: Boolean,
      required: true,
    },
  },
  methods: {
    close() {
      this.$emit('close');
    },
    getQuizList: function () {
      const keyword = this.$refs['keyword'].value;
      axios.get('http://localhost/api/game/quiz?keyword=' + keyword,
          { withCredentials : true
          }).then(response => {
            this.quizSummary = response.data.data;
        console.log(response.data); // 서버 응답 처리
      }).catch(error => {
        console.error(error); // 오류 처리//
      });
    },
    choice(quizSeq, title, singer){
      this.$emit('submit', {
        quizSeq : quizSeq,
        title : title,
        singer : singer,
      });
      this.$emit('close');
    },
    getQuizSeq: function (quizSeq) {

    }
  },
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