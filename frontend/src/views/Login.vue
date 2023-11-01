<template>
  <div class="login-box">
    <h2>Social Login Button</h2>
    <a href="#" class="social-button" id="facebook-connect"> <span>Connect with Facebook</span></a>
    <a href="#" class="social-button" id="google-connect"> <span>Connect with Google</span></a>
    <a href="#" class="social-button" id="twitter-connect"> <span>Connect with Twitter</span></a>
    <a href="#" class="social-button" id="github-connect" v-on:click="goToGithub"> <span>Connect with GitHub</span></a>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      GITHUB_URL: process.env.VITE_GITHUB_OAUTH_URL,
      GITHUB_CLIENT_KEY: process.env.VITE_GITHUB_CLIENT_KEY,
      GOOGLE_URL: process.env.VITE_GOOGLE_URL_OAUTH_URL,
      GOOGLE_CLIENT_KEY: process.env.VITE_GOOGLE_URL_CLIENT_KEY,
      NAVER_URL: process.env.VITE_NAVER_OAUTH_URL,
      NAVER_CLIENT_KEY: process.env.VITE_NAVER_CLIENT_KEY,
      KAKAO_URL: process.env.VITE_KAKAO_OAUTH_URL,
      KAKAO_CLIENT_KEY: process.env.VITE_KAKAO_CLIENT_KEY,
    };
  },
  methods: {
    goToGithub() {
        window.location.assign(
            `${data.GITHUB_URL}?client_id=${data.GITHUB_CLIENT_KEY}`
        )
    }
  }
}
</script>

<style scoped>
@import '../assets/css/login.css';
</style>