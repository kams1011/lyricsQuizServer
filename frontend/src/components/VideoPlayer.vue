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
      this.player = videojs(this.$refs.videoPlayer, this.options, () => {
        this.player.log('onPlayerReady', this);
      });
      this.player.one('loadedmetadata', function (){
        this.currentTime(this.stringToSeconds(this.options.sources[0].startTime));
        this.play();
      })
      this.player.on('timeupdate', function (){
        if(this.currentTime() > this.stringToSeconds(this.options.sources[0].endTime)) {
          this.pause();

          //FIXME 여기서 Redis에 재생 완료된걸 호출.
        }
      })
    },

    stringToSeconds(string) {
      let timeParts = string.split(':');

      let minutes = parseInt(timeParts[1], 10);
      let seconds = parseInt(timeParts[2], 10);

      let totalSeconds = minutes * 60 + seconds;

      return totalSeconds;
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

<style>
.video-js .vjs-tech {
  pointer-events: none;
}
</style>