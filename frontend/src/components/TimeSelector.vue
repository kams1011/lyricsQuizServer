<template>
  <select v-model="selectedValue" class="border-2 border-gray-300 p-1 rounded w-1/5 mr-1 ml-1">
    <option disabled selected> {{ this.placeholder }} </option>
    <option ref='optionValue' v-for="i in 60" :key="i" :value="i - 1">{{ i - 1 }}</option>
  </select>
</template>

<script>

export default {
  name: "TimeSelector",
  props: {
    placeholder : '',
  },
  data() {
    return {
      selectedValue: null
    };
  },
  watch: {
    selectedValue(newValue) {
      this.$emit('input', newValue);
    }
  }
}
</script>
<style scoped>

</style>