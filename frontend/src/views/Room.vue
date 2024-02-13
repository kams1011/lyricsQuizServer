<template>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stream Chat Interface</title>
    <component is="script" src="https://cdn.tailwindcss.com"></component>
  </head>
  <body>
  <div class="flex-container">
    <!-- Video container -->
    <div class="video-container relative w-2/4 float-left">
      <div id="waiting-box" class="absolute top-0 left-0 w-full h-full z-10 bg-white bg-opacity-25 grid place-items-center">
        <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-16 px-32 rounded inline-flex items-center" v-on:click="ready(this.isHost)">
          <span class="text-6xl">{{ this.isHost ? 'START' : 'READY' }}</span>
        </button>
      </div>
      <div class="video-placeholder">
        <i class="fas fa-play-circle"></i> Video Player
      </div>
    </div>
    <!-- Chat container -->
    <div class="chat-container w-2/4 float-right">
      <!-- Chat messages -->
      <div class="chat-messages">
        <!-- ... other chat messages ... -->
        <div class="chat-message" v-for="message in recvList">
          <span class="username">{{message.senderNickName}} : </span> <span class="message">{{message.message}}</span>
        </div>
      </div>

<!--      &lt;!&ndash; Streamer info &ndash;&gt;-->
<!--      <div class="streamer-info">-->
<!--        <span>Capp</span> 🟢 302 | Twitter: @CappTheGod | discord | Towns | windy | <span>Capp</span>-->
<!--      </div>-->

      <!-- Chat input -->
      <input type="text" class="chat-input" v-model="message" placeholder="Send a message" @keydown.enter="send('TALK')">
      <button type="button" class="chat-input">
        <i class="fab"></i> Click</button>
    </div>
  </div>
  </body>
  </html>
</template>

<script>
import * as Stomp from "webstomp-client";
import * as SockJS from "sockjs-client";
import axios from "axios";
// import Cookies from 'js-cookie';

export default {
  name: 'App',
  data() {
    return {
      userName: "",
      message: "",
      recvList: [],
      isHost : false,
      roomId : this.$route.params.roomSeq,
    }
  },
  created() {
    this.roomSeq = this.$route.params.roomSeq;
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.isHostCheck();
    this.connect();
  },
  methods: {
    isHostCheck(){
      axios.get('https://localhost:80/api/game/host?roomId=' + this.roomSeq,
          { withCredentials : true
          }).then(response => {
        this.isHost = response.data.data;
      }).catch(error => {
        console.error(error);
      });
    },
    ready(isHost){
      if (isHost) {

      } else {

      }
      const box = document.getElementById('waiting-box');
      box.remove();
    },
    send(type) {
        console.log("Send message:" + this.message);
        if (this.stompClient && this.stompClient.connected) {
          const msg = {
            type: type,
            roomId : this.roomId,
            // senderNickName: 'test',
            message : this.message
          };
          this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {}); // 그냥 쿠키 넣어주면 될듯?
          this.message = '';
        }
    },
    start(){

    },
    connect() {
        const serverURL = "https://localhost:80/ws-stomp"
        let socket = new SockJS(serverURL);
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect(
            {},
            frame => {
              this.connected = true;
              console.log('소켓 연결 성공', frame);
              const subscribe = this.stompClient.subscribe("/sub/chat/room/" + this.roomId, res => {
                this.recvList.push(JSON.parse(res.body))
              });
              this.send('ENTER');
            },
            error => {
              console.log('소켓 연결 실패', error.headers);
              alert('방에 접속할 수 없습니다.');
              this.connected = false;
              window.location.href ='/';
            }
        );
    },
    videoStreaming(Event){ // Start Button을 클릭 시 영상을 재생함. Event를 받아 Event에 해당하는 영상을 따로 재생함.
      this.stompClient.connect({}, frame => {
        this.stompClient.subscribe('/topic/video', videoData => {
          const videoElement = document.getElementById('video');
          videoElement.src = 'data:video/mp4;base64,' + videoData.body;
          videoElement.play();
        });
      });
    }
  },
  components: {
  }
}
</script>

<style scoped>
@import '../assets/css/room.css';
body {
  background-color: #0e0e10;
  font-family: Arial, sans-serif;
}
.flex-container {
  display: flex;
  height: 100vh;
}
.video-container {
  flex: 1;
  background-color: #18181b;
  display: flex;
  align-items: center;
  justify-content: center;
}
.video-placeholder {
  width: 80%;
  height: 60%;
  background-color: #0e0e10;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #d1d1d1;
  font-size: 24px;
}
.chat-container {
  width: 400px;
  background-color: #18181b;
  color: #d1d1d1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.chat-messages {
  overflow-y: auto;
  background-color: white;
  padding: 16px;
}
.chat-message {
  background-color: #0e0e10;
  padding: 8px 16px;
  border-radius: 4px;
  margin-bottom: 4px;
  font-size: 14px;
}
.chat-message span {
  font-weight: bold;
}
.chat-message .username {
  color: #efeff1;
}
.chat-message .message {
  color: #a7a7a7;
}
.chat-input {
  border: none;
  padding: 12px 16px;
  font-size: 16px;
  color: #d1d1d1;
  width: 100%;
  box-sizing: border-box;
}
.chat-input::placeholder {
  color: #4b4b4b;
}
.streamer-info {
  color: #a7a7a7;
  font-size: 12px;
  text-align: center;
  padding: 12px 0;
}
.streamer-info span {
  color: #efeff1;
}
</style>