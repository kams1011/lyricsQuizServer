<template>
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>구역 나누기 예제</title>
  </head>
  <body>
  <header>
    <button>로그인</button>
    <button>로그아웃</button>
  </header>

  <main>
    <div class="left-section">
      <div class="video-container">
        <!-- 동영상 영역 -->
      </div>
    </div>

    <div class="right-section">
      <div class="top-right">
        <!-- 오른쪽 상단 영역 -->
      </div>
      <div class="bottom-right">
        <!-- 오른쪽 하단 영역 (채팅 영역) -->
        <div class="chat-area">
          <div class="chat-messages">
            <!-- 채팅 메시지 표시 영역 -->
          </div>
          <div class="chat-input">
            <input type="text" placeholder="채팅을 입력하세요">
            <button>전송</button>
          </div>
        </div>
      </div>
    </div>
  </main>
  </body>
  </html>
</template>

<script>
export default {
  name: "ChatRoom.vue"
}
</script>

<style scoped>
@import '../assets/css/room.css';
</style>