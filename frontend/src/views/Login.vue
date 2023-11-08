<template>
  <div class="login-box">
    <h2>Social Login Button</h2>
    <a href="#" class="social-button" id="kakao-connect" v-on:click="goToOauthServer('KAKAO', KAKAO_URL, KAKAO_CLIENT_KEY)"> <span>카카오 로그인</span></a>
    <a href="#" class="social-button" id="naver-connect" v-on:click="goToOauthServer('NAVER', NAVER_URL, NAVER_CLIENT_KEY)"> <span>네이버 로그인</span></a>
    <a href="#" class="social-button" id="google-connect"> <span>Connect with Google</span></a>
    <a href="#" class="social-button" id="github-connect" v-on:click="goToOauthServer('GITHUB', GITHUB_URL, GITHUB_CLIENT_KEY)"> <span>Connect with GitHub</span></a>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      GITHUB_URL: process.env.VUE_APP_GITHUB_OAUTH_URL,
      GITHUB_CLIENT_KEY: process.env.VUE_APP_GITHUB_CLIENT_KEY,
      GOOGLE_URL: process.env.VUE_APP_GOOGLE_URL_OAUTH_URL,
      GOOGLE_CLIENT_KEY: process.env.VUE_APP_GOOGLE_URL_CLIENT_KEY,
      NAVER_URL: process.env.VUE_APP_NAVER_OAUTH_URL,
      NAVER_CLIENT_KEY: process.env.VUE_APP_NAVER_CLIENT_KEY,
      KAKAO_URL: process.env.VUE_APP_KAKAO_OAUTH_URL,
      KAKAO_CLIENT_KEY: process.env.VUE_APP_KAKAO_CLIENT_KEY,
    };
  },
  methods: {
    goToOauthServer(site, url, client_key){
      window.location.assign(
          url + '&client_id=' + client_key + '&state=' + site
      )
    }
  }
}
</script>

<style scoped>
@import '../assets/css/login.css';
</style>