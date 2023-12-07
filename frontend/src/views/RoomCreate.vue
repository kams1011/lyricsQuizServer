
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
    <div class="flex mb-4 type">
      <button class="bg-gray-300 hover:bg-blue-700 text-gray-800 font-bold py-2 px-4 rounded mr-4" id="youtubeButton" v-on:click="toggleFieldset('youtube')">
        <i class="fab fa-youtube-square"></i> Youtube URL
      </button>
      <div class="space-x-2">
        <button class="bg-gray-300 hover:bg-gray-400 checked:bg-blue-500 text-gray-800 font-bold py-2 px-4 rounded" id="fileButton" v-on:click="toggleFieldset('file')">
          <i class="fa fa-upload" aria-hidden="true"></i> File
        </button>
      </div>
    </div>
    <div>
      <fieldset id="fileField" style="display: none">
        <input type="file" label="file" class="form-input border-2 border-gray-300 p-2 rounded w-full mb-5" ref="file" v-on:change="fileUpload">
        <label for="file" ref="fineInfo"></label>
      </fieldset>
      <!-- FILE 아니면 YOUTUBE -->
    </div>

    <div class="mb-4">
      <input type="text" placeholder="제목" class="form-input border-2  border-gray-300 p-2 rounded w-full" ref="title">
    </div>
    <div class="mb-4">
      <input type="text" placeholder="가수" class="form-input border-2 border-gray-300 p-2 rounded w-full" ref="singer">
    </div>
    <div class="mb-4">
      <textarea placeholder="곡 정보" class="form-textarea border-2 border-gray-300 p-2 rounded mx-auto w-full" rows="4" ref="information"></textarea>
    </div>
    <div class="flex justify-between items-center">
      <!--    <button class="text-blue-500 hover:text-blue-700 text-sm">Markdown Mode</button>-->
      <div class="space-x-4">
        <!--      <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">Save Draft</button>-->
        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" v-on:click="register()">등록</button>

        <!-- 모달 -->

        <div>
          <button @click="fetchData">데이터 가져오기</button>

          <!-- 데이터를 표시할 모달 -->
          <div v-if="modalVisible" class="modal">
            <div class="modal-content">
              <div v-for="item in modalData" :key="item.id">
                <p>{{ item.artist }}</p>
                <p>{{ item.songInfo }}</p>
              </div>
              <!-- 페이징 처리 등의 추가적인 UI 기능 구현 -->
              <button @click="closeModal">닫기</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </body>

</template>

<script>
import axios from "axios";

export default {
  name: "RoomRegister",
  data() {
    return {
      modalVisible: false,
      modalData: [],
    };
  },
  methods: {
    fetchData() {
      // Axios를 사용하여 데이터 요청
      axios.get('API_URL') // API_URL은 실제 API의 주소로 대체해야 합니다.
          .then(response => {
            // 받아온 데이터를 모달 데이터에 저장하고 모달을 표시
            this.modalData = response.data;
            this.modalVisible = true;
          })
          .catch(error => {
            console.error('데이터 가져오기 실패:', error);
          });
    },
    closeModal() {
      // 모달을 닫을 때 모달 데이터와 모달 표시 여부 초기화
      this.modalData = [];
      this.modalVisible = false;
    },
  },
}
</script>

<style scoped>

</style>