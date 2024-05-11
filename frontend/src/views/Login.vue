<template>
  <div class="login-box">
    <h2>Social Login Button</h2>
    <a href="#" class="social-button" id="kakao-connect" v-on:click="goToOauthServer('KAKAO', KAKAO_URL, KAKAO_CLIENT_KEY, KAKAO_RESPONSE_TYPE)"><img src="../assets/logo/kakao.png"></a>
    <a href="#" class="social-button" id="naver-connect" v-on:click="goToOauthServer('NAVER', NAVER_URL, NAVER_CLIENT_KEY, NAVER_RESPONSE_TYPE)"><img src="../assets/logo/naver.png"></a>
    <a href="#" class="social-button" id="google-connect" v-on:click="goToOauthServer('GOOGLE', GOOGLE_URL, GOOGLE_CLIENT_KEY, GOOGLE_RESPONSE_TYPE)"><img src="../assets/logo/google.png"></a>
    <a href="#" class="social-button" id="github-connect" v-on:click="goToOauthServer('GITHUB', GITHUB_URL, GITHUB_CLIENT_KEY, GITHUB_RESPONSE_TYPE)"><img src="../assets/logo/github.png"></a>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      GITHUB_URL: process.env.VUE_APP_GITHUB_OAUTH_URL,
      GITHUB_CLIENT_KEY: process.env.VUE_APP_GITHUB_CLIENT_KEY,
      GITHUB_RESPONSE_TYPE: process.env.VUE_APP_GITHUB_RESPONSE_TYPE,

      GOOGLE_URL: process.env.VUE_APP_GOOGLE_OAUTH_URL,
      GOOGLE_CLIENT_KEY: process.env.VUE_APP_GOOGLE_CLIENT_KEY,
      GOOGLE_RESPONSE_TYPE: process.env.VUE_APP_GOOGLE_RESPONSE_TYPE,

      NAVER_URL: process.env.VUE_APP_NAVER_OAUTH_URL,
      NAVER_CLIENT_KEY: process.env.VUE_APP_NAVER_CLIENT_KEY,
      NAVER_RESPONSE_TYPE: process.env.VUE_APP_NAVER_RESPONSE_TYPE,

      KAKAO_URL: process.env.VUE_APP_KAKAO_OAUTH_URL,
      KAKAO_CLIENT_KEY: process.env.VUE_APP_KAKAO_CLIENT_KEY,
      KAKAO_RESPONSE_TYPE: process.env.VUE_APP_KAKAO_RESPONSE_TYPE,

      REDIRECT_URI: process.env.VUE_APP_REDIRECT_URI
    };
  },
  methods: {
    goToOauthServer(site, url, client_key, response_type){
      window.location.assign(
          url +'client_id=' + client_key + '&state=' + site + '&response_type=' + response_type + '&redirect_uri=' + this.REDIRECT_URI
      )
    }
  }
}
</script>

<style scoped>
@import '../assets/css/login.css';
</style>