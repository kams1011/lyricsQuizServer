<template>


  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="../style/css/main.css" />
    <title>게임</title>
  </head>

  <body>
  <div class="checkout">
    <form class="form" autocomplete="off" novalidate>
      <fieldset>
        <label for="card-holder">제목</label>
        <input type="text" id="title" ref="title"/>
      </fieldset>
      <fieldset>
        <label for="card-holder">가수</label>
        <input type="text" id="singer" ref="singer"/>
      </fieldset>
      <fieldset>
        <label for="card-holder">곡 정보</label>
        <textarea id="information" ref="information"></textarea>
      </fieldset>
      <fieldset>
        <label for="card-holder">문제 이전 가사</label>
        <!-- <input type="text" id="card-holder" /> -->
        <textarea id="beforeLyrics" ref="beforeLyrics"></textarea>
      </fieldset>
      <fieldset>
        <label for="card-holder">문제 이후 가사</label>
        <textarea id="afterLyrics" ref="afterLyrics"></textarea>
      </fieldset>
      <fieldset>
        <label for="card-holder">정답</label>
        <textarea id="answer" ref="answer"></textarea>
      </fieldset>
      <fieldset>
        문제 시작 시간
        <select id="startMinutes" ref="startMinutes">
          <option v-for="startMinutes in 59" :key="startMinutes"> {{startMinutes}}</option>
        </select>
        :
        <select id="startSeconds" ref="startSeconds">
          <option v-for="startSeconds in 59" :key="startSeconds"> {{startSeconds}}</option>
        </select>
      </fieldset>
      <fieldset>
        문제 끝 시간
        <select id="endMinutes" ref="endMinutes">
          <option v-for="endMinutes in 59" :key="endMinutes"> {{endMinutes}}</option>
        </select>
        :
        <select id="endSeconds" ref="endSeconds">
          <option v-for="endSeconds in 59" :key="endSeconds"> {{endSeconds}}</option>
        </select>
      </fieldset>
      <fieldset>
        <label for="card-holder">제출 방식</label>
        <div class="type">
          <label for="youtube" class="type">유튜브</label>
          <input type="radio" name="type" class="type" id="youtube" ref="youtube" v-on:click="toggleFieldset('youtube')" value="youtube"/>
          <label for="file" class="type">파일</label>
          <input type="radio" name="type" class="type" id="file" ref="file" v-on:click="toggleFieldset('file')" value="file"/>
        </div>
      </fieldset>
      <fieldset id="youtubeField" style="display: none">
        <label for="card-holder">링크</label>
        <input type="text" ref="url" id="card-holder" />
      </fieldset>
      <fieldset id="fileField" style="display: none">
        <label for="card-holder">파일</label>
        <input type="file" ref="file" id="card-holder" />
      </fieldset>
      <button type="button" class="btn" @click="register"><i class="fa fa-lock"></i> submit</button>
    </form>
  </div>
  </body>
  </html>



</template>

<script>
import axios from "axios";

export default {
  name: "QuizRegister",
  methods: {
    toggleFieldset(type) {
      var youtubeField = document.getElementById("youtubeField");
      var fileField = document.getElementById("fileField");

      if (type === "youtube") {
        youtubeField.style.display = "block";
        fileField.style.display = "none";
      } else if (type === "file") {
        youtubeField.style.display = "none";
        fileField.style.display = "block";
      }
    },
    register: function () { //FIXME 이부분 정확히 수정하기.
      const jsonData = {
        singer: this.$refs['singer'].value,
        information: this.$refs['information'].value,
        startTime: this.$refs['startMinutes'].value + ':' + this.$refs['startSeconds'].value,
        endTime: this.$refs['endMinutes'].value + ':' + this.$refs['endSeconds'].value,
        beforeLyrics: this.$refs['beforeLyrics'].value,
        afterLyrics: this.$refs['afterLyrics'].value,
        answer: this.$refs['answer'].value,
        quizContentCreate: {
          QuizContentType: document.getElementsByName('type'),
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