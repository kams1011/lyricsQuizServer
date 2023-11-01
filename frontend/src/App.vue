<template>
<!--  <img alt="Vue logo" src="./assets/logo.png">-->
<!--  <HelloWorld msg="Welcome to Your Vue.js App"/>-->
<!--  <Lobby msg="Welcome to Your Vue.js App"/>-->
<!--  <Room msg="Welcome to Your Vue.js App"/>-->
<!--  <QuizRegister msg="Welcome to Your Vue.js App"/>-->
  <Login msg="Welcome to Your Vue.js App"/>
</template>

<!--<script>-->
<!--// import HelloWorld from './components/HelloWorld.vue'-->
<!--import Lobby from './views/Lobby'-->

<!--export default {-->
<!--  name: 'App',-->
<!--  data(){-->
<!--    return {-->
<!--      price1 : 60,-->

<!--    }-->
<!--  },-->
<!--  methods : {-->
<!--    increase(){-->
<!--      this.price1 = 1234;-->
<!--    }-->
<!--  },-->
<!--  components: {-->
<!--    // HelloWorld,-->
<!--    Lobby-->
<!--  }-->
<!--}-->
<!--</script>-->


<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import Lobby from './views/Lobby'
import Room from "@/views/Room";
import QuizRegister from "@/views/QuizRegister";
import Login from "@/views/Login";
export default {
  name: 'App',
  data() {
    return {
      userName: "",
      message: "",
      recvList: []
    }
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    // this.connect()
  },
  methods: {
    sendMessage (e) {
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.send()
        this.message = ''
      }
    },
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          userName: this.userName,
          content: this.message
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = "http://localhost/join"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
          {},
          frame => {
            // 소켓 연결 성공
            this.connected = true;
            console.log('소켓 연결 성공', frame);
            // 서버의 메시지 전송 endpoint를 구독합니다.
            // 이런형태를 pub sub 구조라고 합니다.
            this.stompClient.subscribe("/send", res => {
              console.log('구독으로 받은 메시지 입니다.', res.body);

              // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
              this.recvList.push(JSON.parse(res.body))
            });
          },
          error => {
            // 소켓 연결 실패
            console.log('소켓 연결 실패', error);
            this.connected = false;
          }
      );
    }
  },
  components: {
    // HelloWorld,
    Lobby,
    Room,
    QuizRegister,
    Login
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px; /* room.vue에서 헤더가 이상하게 나오니까 해결하기*/
}
</style>
