<template>

  <div ref="videoArea">
    <video ref="videoPlayer" class="video-js"></video>
  </div>
</template>

<script>
import videojs from 'video.js';
import 'video.js/dist/video-js.css';


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
    play(option){
      console.log(this.options.sources[0].src);
      this.player.src({ src: option.sources[0].src, type: option.sources[0].type });
      this.player.play();
    },
    playerSetting(options){
      this.player = videojs(this.$refs.videoPlayer, options, () => {
        this.player.log('onPlayerReady', this);
      });
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