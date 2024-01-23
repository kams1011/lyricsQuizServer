<template>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stream Chat Interface</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
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
  </head>
  <body>
  <div class="flex-container">
    <!-- Video container -->
    <div class="video-container w-2/4 h-60 float-left">
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
      <input type="text" class="chat-input" v-model="message" placeholder="Send a message" @keydown.enter="send()">
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
// import Cookies from 'js-cookie';

export default {
  name: 'App',
  data() {
    return {
      userName: "",
      message: "",
      recvList: [],
      roomId : this.$route.params.roomSeq,
    }
  },
  created() {
    this.roomSeq = this.$route.params.roomSeq;
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect()
  },
  methods: {
    // sendMessage (e) {
    //   if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
    //     this.send()
    //     this.message = 'hiohi'
    //   }
    // },
    send() {
        console.log("Send message:" + this.message);
        if (this.stompClient && this.stompClient.connected) {
          const msg = {
            type: 'TALK',
            roomId : this.roomId,
            senderNickName: 'test', // 이부분 어차피 Cookie로 빼야겠네.
            message : this.message
          };
          this.stompClient.send("/pub/chat/message", JSON.stringify(msg), {}); // 그냥 쿠키 넣어주면 될듯?
          this.message = '';
        }
    },
    connect() {
        const serverURL = "https://localhost:80/ws-stomp?tmep=1234"
        let socket = new SockJS(serverURL);
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect(
            {},
            frame => {
              // 소켓 연결 성공
              this.connected = true;
              console.log('소켓 연결 성공', frame);
              // 서버의 메시지 전송 endpoint를 구독합니다.
              // 이런형태를 pub sub 구조라고 합니다.
              // this.stompClient.subscribe("/sub/chat/room/" + this.roomId, res => {
              //   console.log('구독으로 받은 메시지 입니다.', res.body);
              //   // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
              //   this.recvList.push(JSON.parse(res.body))
              // });
            },
            error => {
              // 소켓 연결 실패
              console.log('소켓 연결 실패', error);
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
</style>