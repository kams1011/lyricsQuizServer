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
        <input type="text" id="card-holder" />
      </fieldset>
      <fieldset>
        <label for="card-holder">가수</label>
        <input type="text" id="card-holder" />
      </fieldset>
      <fieldset>
        <label for="card-holder">곡 정보</label>
        <textarea></textarea>
      </fieldset>
      <fieldset>
        <label for="card-holder">문제 이전 가사</label>
        <!-- <input type="text" id="card-holder" /> -->
        <textarea></textarea>
      </fieldset>
      <fieldset>
        <label for="card-holder">문제 이후 가사</label>
        <textarea></textarea>
      </fieldset>
      <fieldset>
        <label for="card-holder">정답</label>
        <textarea></textarea>
      </fieldset>
      <fieldset>
        <label for="card-holder">제출 방식</label>
        <div class="type">
          <label for="youtube" class="type">유튜브</label>
          <input type="radio" name="type" class="type" id="youtube" v-on:click="toggleFieldset('youtube')" />
          <label for="file" class="type">파일</label>
          <input type="radio" name="type" class="type" id="file" v-on:click="toggleFieldset('file')" />
        </div>
      </fieldset>
      <fieldset id="youtubeField" style="display: none">
        <label for="card-holder">링크</label>
        <input type="text" id="card-holder" />
      </fieldset>
      <fieldset id="fileField" style="display: none">
        <label for="card-holder">제목</label>
        <input type="file" id="card-holder" />
      </fieldset>
      <button class="btn"><i class="fa fa-lock"></i> submit</button>
    </form>
  </div>
  </body>
  </html>



</template>

<script>
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
    }
  }
}


</script>

<style scoped>
@import '../assets/css/quizRegister.css';
</style>