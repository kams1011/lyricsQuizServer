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
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    // this.connect()
  },
  /*methods: {*/
  /*  sendMessage (e) {*/
  /*    if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){*/
  /*      this.send()*/
  /*      this.message = ''*/
  /*    }*/
  /*  },*/
  /*  send() {*/
  //     console.log("Send message:" + this.message);
  //     if (this.stompClient && this.stompClient.connected) {
  //       const msg = {
  //         userName: this.userName,
  //         content: this.message
  //       };
  //       this.stompClient.send("/receive", JSON.stringify(msg), {});
  //     }
  //   },
  //   connect() {
  //     const serverURL = "http://localhost/join"
  //     let socket = new SockJS(serverURL);
  //     this.stompClient = Stomp.over(socket);
  //     console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
  //     this.stompClient.connect(
  //         {},
  //         frame => {
  //           // 소켓 연결 성공
  //           this.connected = true;
  //           console.log('소켓 연결 성공', frame);
  //           // 서버의 메시지 전송 endpoint를 구독합니다.
  //           // 이런형태를 pub sub 구조라고 합니다.
  //           this.stompClient.subscribe("/send", res => {
  //             console.log('구독으로 받은 메시지 입니다.', res.body);
  //
  //             // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
  //             this.recvList.push(JSON.parse(res.body))
  //           });
  //         },
  //         error => {
  //           // 소켓 연결 실패
  //           console.log('소켓 연결 실패', error);
  //           this.connected = false;
  //         }
  //     );
  //   }
  // },
  components: {
    // HelloWorld,
    // Lobby,
    // Room,
    // QuizRegister,
    // Login
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
