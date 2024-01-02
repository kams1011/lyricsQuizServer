
<template>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Post Submission Form</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
    <style>
      /* Ensure that the form elements have full width */
      .form-input, .form-textarea, .form-select, .form-multiselect, .form-checkbox, .form-radio {
        width: 100%;
      }
    </style>
  </head>
  <body>
  <div class="bg-gray-100 p-6 max-w-2xl mx-auto my-8 rounded-lg shadow">
<!--    <div class="flex mb-4 type">-->
<!--      <button class="bg-gray-300 hover:bg-blue-700 text-gray-800 font-bold py-2 px-4 rounded mr-4" id="youtubeButton" v-on:click="toggleFieldset('youtube')">-->
<!--        <i class="fab fa-youtube-square"></i> Youtube URL-->
<!--      </button>-->
<!--      <div class="space-x-2">-->
<!--        <button class="bg-gray-300 hover:bg-gray-400 checked:bg-blue-500 text-gray-800 font-bold py-2 px-4 rounded" id="fileButton" v-on:click="toggleFieldset('file')">-->
<!--          <i class="fa fa-upload" aria-hidden="true"></i> File-->
<!--        </button>-->
<!--        &lt;!&ndash;      <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">&ndash;&gt;-->
<!--        &lt;!&ndash;        <i class="fas fa-link"></i> Link&ndash;&gt;-->
<!--        &lt;!&ndash;      </button>&ndash;&gt;-->
<!--        &lt;!&ndash;      <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">&ndash;&gt;-->
<!--        &lt;!&ndash;        <i class="fas fa-poll"></i> Poll&ndash;&gt;-->
<!--        &lt;!&ndash;      </button>&ndash;&gt;-->
<!--      </div>-->
<!--    </div>-->
    <div class="text-center mb-12 mt-12">
      <h1 class="font-extrabold text-4xl">방 생성하기</h1>
    </div>

    <input type="hidden" id="quizSeq" ref="quizSeq"/>
    <div class="mb-4">
      <input type="text" placeholder="방 제목을 입력하세요." class="form-input border-2  border-gray-300 p-2 rounded w-full" ref="roomName">
    </div>
    <div class="mb-4">
      <input type="text" placeholder="게임을 선택해주세요." class="form-input border-2 border-gray-300 p-2 rounded w-full cursor-pointer" id="gameInfo" @click="openModal" readonly>
    </div>
    <div class="mb-4 text-center">
      인원 선택 :
      <select name="attendeeLimit" class="mb-4 pl-4 pr-5 border-2 border-gray-300 rounded-md" ref="attendeeLimit">
        <option value="2">2명</option>
        <option value="3">3명</option>
        <option value="4">4명</option>
        <option value="5">5명</option>
        <option value="6">6명</option>
      </select>
    </div>
    <div class="text-center mb-4">
      <label for="isSecretRoom">비밀 방 여부</label>
      <input type="checkbox" placeholder="비밀 방 여부" class="ml-6 mr-6" ref="isSecretRoom" id="isSecretRoom" @change="toggle()">
      <input type="text" placeholder="비밀 번호" class="form-input border-2 border-gray-300 p-2 rounded" style="display: none" ref="password" id="password">
    </div>

    <div class="flex justify-between items-center">
      <!--    <button class="text-blue-500 hover:text-blue-700 text-sm">Markdown Mode</button>-->
      <div class="space-x-4">
        <!--      <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">Save Draft</button>-->
        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" @click="register()">등록</button>
      </div>
    </div>
  </div>

  <div id="app">
    <QuizSummaryModal :show="modalShow" @close="closeModal"  @submit="choiceQuiz">
      <!-- 모달 내용 -->
    </QuizSummaryModal>
  </div>
  </body>



</template>

<script>
import axios from "axios";
import QuizSummaryModal from "@/views/QuizSummaryModal";

export default {
  name: "RoomRegister",
  data() {
    return {
      modalVisible: false,
      modalShow : false,
      modalData: [],
    };
  },


  //FIXME 게임 생성 완료 시 게임 방으로 넘어가는 로직 구현.




  methods: {
    register: function () {
      const jsonData = {
        roomName : this.$refs['roomName'].value,
        quizSeq: this.$refs['quizSeq'].value,
        isSecretRoom: this.$refs['isSecretRoom'].checked,
        password: this.$refs['password'].value,
        attendeeLimit: this.$refs['attendeeLimit'].value,
      };
      axios.post('https://localhost:80/api/game', jsonData,
          { withCredentials : true
          }).then(response => {

        console.log(response.data); // 서버 응답 처리
      }).catch(error => {
        console.error(error); // 오류 처리//
      });
    },
    openModal() {
      this.modalShow = true;
    },
    closeModal() {
      this.modalShow = false;
    },
    choiceQuiz(data){
      document.getElementById('quizSeq').value = data.quizSeq;
      let gameInfo = document.getElementById('gameInfo');
      gameInfo.value = data.singer + ' - ' + data.title
    },
    toggle(){
      const display = this.$refs['password'].style.display;
      if(display === "none"){
        this.$refs['password'].style.display = 'inline';
      } else {
        this.$refs['password'].style.display = 'none';
      }
    }

  },
  components: {
    QuizSummaryModal,
  },
}
</script>

<style scoped>

</style>