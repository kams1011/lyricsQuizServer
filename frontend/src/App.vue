<template>
  <head>
<!--    <script src="https://cdn.tailwindcss.com"></script>-->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>

  </head>
<!--  <HelloWorld msg="Welcome to Your Vue.js App"/>-->
<!--  <Lobby msg="Welcome to Your Vue.js App"/>-->
<!--  <Room msg="Welcome to Your Vue.js App"/>-->
<!--  <QuizRegister msg="Welcome to Your Vue.js App"/>-->
<!--  <Login msg="Welcome to Your Vue.js App"/>-->
  <div id="app">
    <router-link to="/"></router-link>
    <router-link to="/login"></router-link>
    <router-link to="/login/callback"></router-link>
    <router-link to="/user/register"></router-link>
    <router-link to="/quiz/register"></router-link>
    <router-link to="/room"></router-link>
    <!-- 여기에 현재 경로에 맞는 컴포넌트가 동적으로 렌더링됩니다 -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      userName: "",
      message: "",
      recvList: [],
    }
  },
  created() {
  },
  components: {
  }
}
</script>

<style>
/*#app {*/
/*  font-family: Avenir, Helvetica, Arial, sans-serif;*/
/*  -webkit-font-smoothing: antialiased;*/
/*  -moz-osx-font-smoothing: grayscale;*/
/*  text-align: center;*/
/*  color: #2c3e50;*/
/*  margin-top: 60px; !* room.vue에서 헤더가 이상하게 나오니까 해결하기*!*/
/*}*/
</style>
