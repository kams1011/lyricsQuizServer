<template>

  <div ref="videoArea">
<!--    <video ref="videoPlayer" class="video-js" data-setup='{ "techOrder": ["youtube"]}'></video>-->
    <video ref="videoPlayer" class="video-js"></video>
  </div>
</template>



<script>
import videojs from 'video.js';
require('videojs-youtube'); // add any plugin first and then
import 'video.js/dist/video-js.css';
// import { videoPlayer } from 'vue-video-player';


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
    // play(option){
    //   this.player.src({ src: option.sources[0].src, type: option.sources[0].type });
    //   this.player.play();
    // },
    playerSetting(){
      console.log('option!');
      console.log(this.options);
      this.player = videojs(this.$refs.videoPlayer, this.options, () => {
        this.player.log('onPlayerReady', this);
        this.player.play();
      });

    }
  },
  data() {
    return {
      player: null
    }
  },
  mounted(options) {
    this.playerSetting();
  },
  beforeDestroy() {
    if (this.player) {
      this.player.dispose();
    }
  }
}
</script>