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

        <a href="#" class="button" v-on:click="allowInvitation()">{{ this.isAllowed ? '초대 허용중' : '초대 거부중' }}</a>
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
          <a href="/room/create" class="button">
            <i class="ph-plus-bold"></i>
            <span>방 만들기</span>
          </a>
          <a href="/quiz/register" class="button">
            <i class="ph-plus-bold"></i>
            <span>문제 등록하기</span>
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
            <article class="card" v-for="item in itemList" :key="item.gameRoomSeq" @click="secretRoomCheck(item.isSecretRoom, item.gameRoomSeq)">
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
              </div>
              <div class="card-body">
                <p>주제 : {{ item.topic }}</p>
              </div>
              <div class="card-footer">
                <h3>{{ item.attendeeCount }} / {{ item.attendeeLimit }}</h3>
              </div>
            </article>
          </div>
        </div>
      </div>
    </div>

    <!-- 비밀번호를 입력받는 모달 창 -->
    <div v-if="showPasswordModal" class="modal">
      <div class="modal-content flex justify-between">
        <input placeholder="비밀번호를 입력하세요" type="password" v-model="password" />
        <button @click="submitPassword">확인</button>
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
      password: '',
      roomId:'',
      showPasswordModal : false,
      isAllowed : false,
      itemList: [],
    }
  },
  mounted() {
      this.initInvitationInfo();
      this.getList();
  },
  methods: {
    initInvitationInfo : function () {
      axios.get('https://localhost:80/api/game/invitation',
          { withCredentials : true
          }).then(response => {
            console.log('hihi');
            console.log(response);
            this.isAllowed = response.data.data;
      }).catch(error => {
        console.error(error);
      });
    },
    allowInvitation(){
      axios.patch('https://localhost:80/api/game/invitation?isAllowed=' + !this.isAllowed, {},{ withCredentials : true})
          .then(response => {
            this.isAllowed = !this.isAllowed;
            if (this.isAllowed) {
              alert('초대를 허용했습니다.');
            } else {
              alert('초대를 거부했습니다.');
            }
          }).catch(error => {
            alert('초대 허용에 실패했습니다. 사유 : ' + error.response.data.message);
          });
    },
    getList: function () {
      const keyword = '';
      axios.get('https://localhost:80/api/game?keyword=' + keyword,
          { withCredentials : true
          }).then(response => {
        this.itemList = response.data.data;
      }).catch(error => {
        console.error(error);
      });
    },
    secretRoomCheck(isSecretRoom, roomSeq){
      this.roomId = roomSeq;
      if (isSecretRoom) {
        this.showPasswordModal = true;
      } else {
        this.enter(roomSeq);
      }
    },
    enter(roomSeq){
      axios.patch('https://localhost:80/api/game/room?roomId=' + roomSeq +'&password=' + this.password, {},{ withCredentials : true}).then(response => {
        this.$router.push({ path: `/room/${roomSeq}` });
      }).catch(error => {
        alert(error.response.data.message);
        window.location.reload();
      })
    },
    submitPassword() {
      const jsonData = {
        roomId : this.roomId,
        password: this.password,
      };
      // 비밀번호 확인 로직 추가
      axios.post('https://localhost:80/api/game/password', jsonData,
          { withCredentials : true
          }).then(response => {
        this.enter(this.roomId);
      }).catch(error => {
        alert("비밀번호가 일치하지 않습니다.");
        this.initialize();
      });
    },
    initialize(){
      this.roomId = '';
      this.showPasswordModal = false;
      this.password = '';
    }
  }


}
</script>

<style scoped>
@import '../assets/css/style.css';

.modal {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #fefefe;
  padding: 15px;
  border: 1px solid #888;
  width: 80%;
  max-width: 350px; /* 모달의 최대 너비 설정 */
  position: relative;
}

</style>