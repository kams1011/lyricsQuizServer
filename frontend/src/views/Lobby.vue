<template>
  <head>
    <meta charset="UTF-8">
    <title>CodePen - Dashboard UI </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
<!--    <link rel="stylesheet" href="./src/assets/css/style.css">-->
    <link rel="stylesheet" type="text/css" href="https://unpkg.com/phosphor-icons@1.4.2/src/css/icons.css"></head>
  <body>
  <main class="main">
    <div class="responsive-wrapper">
      <div class="main-header">
        <h1>로비</h1>
        <div class="search">
          <input type="text" placeholder="방 제목, 닉네임을 검색해보세요">
          <button type="submit">
            <i class="ph-magnifying-glass-bold"></i>
          </button>
        </div>
      </div>
      <div class="horizontal-tabs">
        <a href="#">같이 할 사람을 찾아보세요</a>
        <!-- <a href="#">Profile</a>
        <a href="#">Password</a> -->
        <!-- <a href="#">Team</a>
        <a href="#">Plan</a>
        <a href="#">Billing</a>
        <a href="#">Email</a>
        <a href="#">Notifications</a>
        <a href="#" class="active">Integrations</a>
        <a href="#">API</a> -->
      </div>
      <div class="content-header">
        <div class="content-header-intro">
          <h2>현재 방 목록</h2>
          <p>게임을 시작한 방은 음영처리됩니다.</p>
        </div>
        <div class="content-header-actions">
          <a href="#" class="button">
            <i class="ph-faders-bold"></i>
            <span>Filters</span>
          </a>
          <a href="#" class="button">
            <i class="ph-plus-bold"></i>
            <span>방 만들기</span>
          </a>
        </div>
      </div>
      <div class="content">
        <!-- <div class="content-panel">
          <div class="vertical-tabs">
            <a href="#" class="active">View all</a>
            <a href="#">Developer tools</a>
            <a href="#">Communication</a>
            <a href="#">Productivity</a>
            <a href="#">Browser tools</a>
            <a href="#">Marketplace</a>
          </div>
        </div> -->
        <div class="content-main">
          <div class="card-grid">
            <article class="card" v-for="item in itemList" :key="item.gameRoomSeq" @click="enter(item.gameRoomSeq)">
              <div class="card-header">
                <div>
                  <span><img src="https://assets.codepen.io/285131/zeplin.svg"></span>
                  <h3>{{ item.roomName }}</h3>
                </div>
                <div v-if="item.gameStatus === 'READY'">
                  대기
                </div>
                <div v-if="item.gameStatus === 'IN_PROGRES'">
                  진행중
                </div>
                <!-- <label class="toggle">
                  <input type="checkbox" checked>
                  <span></span>
                </label> -->
              </div>
              <div class="card-body">
                <p>주제 : {{ item.topic }}</p>
              </div>
              <div class="card-footer">
                <a href="#">{{ item.attendeeCount }} / {{ item.attendeeLimit }}</a>
              </div>
            </article>
          </div>
        </div>
      </div>
    </div>
  </main>
  <!-- partial -->
  <component is="script"  src="https://unpkg.com/phosphor-icons"/>
<!--  <component is="script"  src="../js/script.js"/>-->
  </body>
</template>

<script>
import axios from "axios";

export default {
  name: "Lobby",
  data() {
    return{
      itemList: [],
    }
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList: function () {
      const keyword = '';
      axios.get('https://localhost:80/api/game?keyword=' + keyword,
          { withCredentials : true
          }).then(response => {
            console.log(response.data);
        this.itemList = response.data.data;
      }).catch(error => {
        console.error(error); // 오류 처리//
      });
    },
    enter(roomSeq){
      //FIXME 여기서 Back에 Enter 요청.
      this.$router.push({ path: `/room/${roomSeq}` });
    }
  }


}
</script>

<style scoped>
@import '../assets/css/style.css';
</style>