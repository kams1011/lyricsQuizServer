
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
        <!--      <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">-->
        <!--        <i class="fas fa-link"></i> Link-->
        <!--      </button>-->
        <!--      <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">-->
        <!--        <i class="fas fa-poll"></i> Poll-->
        <!--      </button>-->
      </div>
    </div>
    <div>
      <fieldset id="fileField" style="display: none">
        <input type="file" class="form-input border-2 border-gray-300 p-2 rounded w-full mb-5" ref="file">
      </fieldset>
      <fieldset id="youtubeField" style="display: none">
        <input type="text" placeholder="URL" class="form-input border-2 border-gray-300 p-2 rounded w-full mb-5" ref="url">
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
    <div class="mb-4 mx-auto justify-center">
      <input type="text" placeholder="재생 시작 분" class="form-input border-2 border-gray-300 p-1 rounded w-1/5 mr-2 ml-2" ref="startMinutes"> :
      <input type="text" placeholder="재생 시작 초" class="form-input border-2 border-gray-300 p-1 rounded w-1/5 ml-2 mr-2" ref="startSeconds"> ~
      <input type="text" placeholder="재생 끝 분" class="form-input border-2 border-gray-300 p-1 rounded w-1/5 mr-2 ml-2" ref="endMinutes"> :
      <input type="text" placeholder="재생 끝 초" class="form-input border-2 border-gray-300 p-1 rounded w-1/5 ml-2 mr-2" ref="endSeconds">
    </div>
    <div class="mb-4">
      <textarea placeholder="이전 가사" class="form-textarea border-2 border-gray-300 p-2 rounded mx-auto w-full" rows="4" ref="beforeLyrics"></textarea>
    </div>
    <div class="mb-4">
      <textarea placeholder="이후 가사" class="form-textarea border-2 border-gray-300 p-2 rounded mx-auto w-full" rows="4" ref="afterLyrics"></textarea>
    </div>
    <div class="mb-4">
      <textarea placeholder="정답" class="form-textarea border-2 border-gray-300 p-2 rounded mx-auto w-full" rows="4" ref="answer"></textarea>
    </div>
    <div class="flex justify-between items-center">
      <!--    <button class="text-blue-500 hover:text-blue-700 text-sm">Markdown Mode</button>-->
      <div class="space-x-4">
        <!--      <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded">Save Draft</button>-->
        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" v-on:click="register()">등록</button>
      </div>
    </div>
  </div>
  </body>

</template>



<script>
import axios from "axios";

export default {
  name: "QuizRegister",
  data() {
    return {
      type: 'FILE',
    };
  },
  methods: {
    fillZero(number){
      return (number < 10 ? '0' : '') + number;
    },
    toggleFieldset(type) {
      let youtubeButton = document.getElementById("youtubeButton");
      let youtubeField = document.getElementById("youtubeField");
      let fileButton = document.getElementById("fileButton");
      let fileField = document.getElementById("fileField");

      if (type === "youtube") {
        youtubeButton.classList.remove('bg-gray-300');
        youtubeButton.classList.add('bg-blue-600');
        fileButton.classList.remove('bg-blue-600');
        fileButton.classList.add('bg-gray-300');
        youtubeField.style.display = "block";
        fileField.style.display = "none";
        this.type = 'YOUTUBE';
      } else if (type === "file") {
        fileButton.classList.remove('bg-gray-300');
        fileButton.classList.add('bg-blue-600');
        youtubeButton.classList.remove('bg-blue-600');
        youtubeButton.classList.add('bg-gray-300');
        youtubeField.style.display = "none";
        fileField.style.display = "block";
        this.type = 'FILE';
      }
    },
    register: function () { //FIXME 이부분 정확히 수정하기.

      let startDate = '00:' +
              this.fillZero(this.$refs['startMinutes'].value) + ':' +
              this.fillZero(this.$refs['startSeconds'].value);
      let endDate = '00:' +
          this.fillZero(this.$refs['endMinutes'].value) + ':' +
          this.fillZero(this.$refs['endSeconds'].value);
      const jsonData = {
        title : this.$refs['title'].value,
        singer: this.$refs['singer'].value,
        information: this.$refs['information'].value,
        startTime: startDate,
        endTime: endDate,
        beforeLyrics: this.$refs['beforeLyrics'].value,
        afterLyrics: this.$refs['afterLyrics'].value,
        answer: this.$refs['answer'].value,
        quizContentCreate: {
          quizContentType: this.type,
          url: this.$refs['url'].value
        }
      };
      axios.post('http://localhost/api/quiz', jsonData,
          { withCredentials : true
          }).then(response => {
        console.log(response.data); // 서버 응답 처리
      })
          .catch(error => {
            console.error(error); // 오류 처리
          });
    },
  }
}


</script>



<style scoped>
@import '../assets/css/quizRegister.css';
</style>