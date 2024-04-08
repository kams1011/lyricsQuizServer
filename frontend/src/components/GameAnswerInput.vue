<template>
  <Input
      :button-name="'제출'"
      :placeholder="'정답을 입력해주세요'"
      v-model="keyword"
      :onClick="checkAnswer"
  />
</template>

<script>
import Input from '@/components/Input.vue';

export default {
  name: "GameAnswerInput",
  components: {Input},
  data() {
    return {
      keyword : '',
    }
  },
  props: {
    placeholder : '',
    buttonName : '',
    // show: {
    //   type: Boolean,
    //   required: true,
    // },
  },
  methods: {
    close() {
      this.$emit('close');
    },
    checkAnswer(){ // FIXME 2023-04-09 여기부터
      console.log('나는 받은값 ', this.keyword);
      this.$emit('onClick', this.keyword);
    },
  }
}
</script>
