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
        <label for="card-holder">아이디</label>
        <input type="text" id="title" ref="id" v-model="id" readonly/>
      </fieldset>
      <fieldset>
        <label for="card-holder">로그인유형</label>
        <input type="text" id="singer" ref="loginType" v-model="loginType" readonly/>
      </fieldset>
      <fieldset>
        <label for="card-holder">닉네임</label>
        <input type="text" id="singer" ref="nickName" v-model="nickName"/>
      </fieldset>
      <button type="button" class="btn" @click="register"><i class="fa fa-lock"></i>Register</button>
    </form>
  </div>
  </body>
  </html>



</template>

<script>
import axios from "axios";

export default {
  name: "UserRegister",
  data() {
    return {
      id: '',
      loginType:'',
      nickName:'',
    };
  },
  mounted() {
    this.id = this.$route.params.id;
    this.loginType = this.$route.params.loginType;
  }, // FIXME 닉네임 입력받는 칸 유효성검사. 프로필 사진 업로드 추가
  methods: {
      register: function () { //FIXME 이부분 정확히 수정하기.
      const jsonData = {
        id: this.$refs['id'].value,
        loginType: this.$refs['loginType'].value,
        nickName: this.$refs['nickName'].value,
      };

      axios.post('http://localhost/api/users/signup', jsonData,
          { withCredentials : true
          }).then(response => {
            alert('회원가입에 성공했습니다.');
            window.location.href = "/";
          })
          .catch(error => {
            alert('회원가입에 실패했습니다.');
          });
    },
  }
}


</script>

<style scoped>
@import '../assets/css/quizRegister.css';
</style>