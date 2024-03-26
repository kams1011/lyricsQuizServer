<template>
  <div>
    <video ref="videoPlayer" class="video-js"></video>
  </div>
</template>

<script>
import videojs from 'video.js';

export default {
  name: 'VideoPlayer',
  props: {
    options: {
      type: Object,
      default() {
        return {};
      }
    }
  },
  methods: {
    play(VideoOptions){
      console.log("AAAAAAAAAAAAAAAAAA");
      console.log(this.player);
      this.player.sources[0].src = "https://www.shutterstock.com/shutterstock/videos/1075423076/preview/stock-footage-collage-of-eyes-beautiful-people-of-different-ages-and-multiethnic-close-up-montage-of-positive.webm";
      // this.player.setAttribute("src", "https://www.shutterstock.com/shutterstock/videos/1075423076/preview/stock-footage-collage-of-eyes-beautiful-people-of-different-ages-and-multiethnic-close-up-montage-of-positive.webm");
      this.player.play();

      if (this.player) {
        // this.playerSetting(VideoOptions);
        // this.player.dispose();
        // this.player.src = "https://www.shutterstock.com/shutterstock/videos/1075423076/preview/stock-footage-collage-of-eyes-beautiful-people-of-different-ages-and-multiethnic-close-up-montage-of-positive.webm";
        // this.player.play();
        // this.$refs.videoPlayer.src = "https://www.shutterstock.com/shutterstock/videos/1075423076/preview/stock-footage-collage-of-eyes-beautiful-people-of-different-ages-and-multiethnic-close-up-montage-of-positive.webm";
        // this.$refs.videoPlayer.play();
        // 이게 결국 video태그를 직접 조작하는거라 수정필요

      }
    },
    playerSetting(VideoOptions){
      console.log('a');
      console.log(this.player);
      this.player = videojs(this.$refs.videoPlayer, VideoOptions, () => {
        this.player.log('onPlayerReady', this);
      });
      console.log(this.player)
    }
  },
  data() {
    return {
      player: null
    }
  },
  mounted() {
    this.playerSetting();
  },
  beforeDestroy() {
    if (this.player) {
      this.player.dispose();
    }
  }
}
</script>