<template>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stream Chat Interface</title>
    <component is="script" src="https://cdn.tailwindcss.com"></component>
    <link href="https://vjs.zencdn.net/8.10.0/video-js.css" rel="stylesheet" />
  </head>
  <body>
  <div class="flex-container">
    <!-- Video container -->
    <div class="video-container relative w-2/4 float-left">
      <div id="waiting-box" class="absolute top-0 left-0 w-full h-full z-10 bg-white bg-opacity-25 grid place-items-center">
<!--        <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-16 px-32 rounded inline-flex items-center" v-on:click="buttonClicked()">-->
        <button class="bg-gray-30 hover:bg-gray-400 text-gray-800 font-bold py-16 px-32 rounded inline-flex items-center" v-on:click="buttonClicked()">
          <span class="text-6xl">{{ this.isHost ? 'START' : 'READY' }}</span>
        </button>
      </div>
      <div class="video-placeholder">
<!--        <i class="fas fa-play-circle"></i> Video Player-->
        <video-player ref="video-player" :key="key" :options="videoOptions" @streaming-complete="streamingComplete()"/>
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
      <button type="button" class="chat-input" v-on:click="getInvitableUsers(this.isHost)">
        <i class="fab"></i>{{ this.isHost ? '초대하기' : 'Click' }}</button>
    </div>

    <div v-if="showInvitableUsersModal" class="modal">
      <div class="modal-content flex justify-between" v-for="item in invitableUsers" :key="item.userSeq" v-on:click="invite(item.nickName, item.userSeq)">
        <div class="w-full flex p-3 pl-4 items-center hover:bg-gray-300 rounded-lg cursor-pointer" >
          <h3>닉네임 : {{ item.nickName }} </h3>
        </div>
      </div>
    </div>
  </div>
  </body>
  </html>
</template>

<script>
import * as Stomp from "webstomp-client";
import * as SockJS from "sockjs-client";
import axios from "axios";
import VideoPlayer from '@/components/VideoPlayer.vue';


export default {
  name: 'App',
  components: {
    VideoPlayer
  },
  data() {
    return {
      key : 0,
      userName: "",
      message: "",
      recvList: [],
      isHost : false,
      ready : false,
      showInvitableUsersModal : false,
      roomId : this.$route.params.roomSeq,
      invitableUsers : [],
      techOrder: ['youtube'],
      videoOptions: {
        controls: false,
        fullscreen : true,
        sources: [
          {
            src: "",
            type: "",
            startTime : "",
            endTime : "",
          }
        ]
      }
    }
  },
  created() {
    this.roomSeq = this.$route.params.roomSeq;
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.isHostCheck();
    this.connect();
  },
  methods: {
    getInvitableUsers(isHost){
      if(isHost){
        axios.get('https://localhost:80/api/game/invitation/users', { withCredentials : true})
        .then(response => {
          console.log(response);
          if (response.data.data.length == 0) {
            alert('초대가능한 유저가 없습니다.');
          } else {
            this.invitableUsers = response.data.data;
            this.showInvitableUsersModal = true;
          }
        }).catch(error => {
          alert('초대 유저 불러오기에 실패했습니다.');
        });
      } else {
        alert('잘못된 접근입니다.');
      }
    },
    invite(nickName, userSeq){
      let isConfirmed = confirm(nickName + '님을 초대하시겠습니까?');
      if (isConfirmed) {
        axios.post('https://localhost:80/api/game/invitation?roomId=' + this.roomSeq + '&invitedUserSeq=' + userSeq,{},
            { withCredentials : true})
            .then(response => {
              alert('초대에 성공했습니다.');
        }).catch(error => {
          alert('초대에 실패했습니다.');
        });
      }
    },
    isHostCheck(){
      axios.get('https://localhost:80/api/game/host?roomId=' + this.roomSeq,
          { withCredentials : true
          }).then(response => {
        this.isHost = response.data.data;
      }).catch(error => {
        console.error(error);
      });
    },
    buttonClicked() {
      if (this.isHost) {
        this.readyOrStart("start", "게임을 시작합니다.");
      } else {
        this.readyOrStart("ready", "준비 완료");
      }
    },
    readyOrStart(action, message) {
      alert(message);
      this.sendActionToServer([action]);
    },
    sendActionToServer(action) {
      const url = `https://localhost:80/api/game/${action}?roomId=${this.roomSeq}`;
      axios.patch(url, {}, { withCredentials: true })
          .then(response => {
            const box = document.getElementById('waiting-box');
            box.remove();
            if (this.isHost){
              this.videoStreaming(response);
            }
          })
          .catch(error => { // FIXME 전역에러로 잡고있어서 해당 부분 제거
            // console.log(error);
            // alert('에러가 발생했습니다.');
          });
    },
    videoStreaming(response){
      let sources = '';
      let type = '';
      if (response.data.data.quizContentType === 'YOUTUBE') {
        type = "video/youtube";
      } else {
        type =  "video/webm";
      }
      sources = {
          src: response.data.data.url,
          type: type,

      };
      this.videoOptions.streamingStart = true;
      this.videoOptions.sources[0] = sources;
      this.videoOptions.sources[0].startTime = response.data.data.startTime;
      this.videoOptions.sources[0].endTime = response.data.data.endTime;
      //FIXME 중간에 5초 카운트다운 하는 영상 추가해주는 방법도 있음
      this.key++;
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
    streamingComplete(){
      const url = `https://localhost:80/api/game/streaming/complete/` + this.roomId;
      axios.patch(url, {}, { withCredentials: true })
          .then(response => {
            //FIXME
          })
          .catch(error => { // FIXME 전역에러로 잡고있어서 해당 부분 제거
            // console.log(error);
            // alert('에러가 발생했습니다.');
          });
    }
  },
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