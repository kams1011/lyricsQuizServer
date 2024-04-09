<template>
  <Input
      :button-name="'제출'"
      :placeholder="'정답을 입력해주세요'"
      @onClick="checkAnswer"
  />
</template>

<script>
import Input from '@/components/Input.vue';

export default {
  name: "GameAnswerInput",
  components: {Input},
  data() {
    return {
    }
  },
  props: {
    placeholder : '',
    buttonName : '',
  },
  methods: {
    close() {
      this.$emit('close');
    },
    checkAnswer(answer){
      console.log('나는 받은값 ', answer);
      this.$emit('onClick', answer);
    },
  }
}
</script>
