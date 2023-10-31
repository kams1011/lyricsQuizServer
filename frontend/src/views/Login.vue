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
  methods: {
    goToGithub() {
        window.location.assign(
            `https://github.com/login/oauth/authorize?client_id={clientId}`
        )
    }
  }
}
</script>

<style scoped>
@import '../assets/css/login.css';
</style>