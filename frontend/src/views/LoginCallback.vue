<template>
hihi
</template>

<script>
import axios from "axios";

export default {
  name: "LoginCallback",
  computed: {
    dynamicVariable() {
      return this.$store.getters.getDynamicVariable;
    },
  },
  methods: {
    redirect: function(url) {
      window.location.href = url;
    },
  },
  created() {
    const redirect = this.redirect;
    axios.get('http://localhost/api/users/login/' + this.$route.query.state + '/' + this.$route.query.code)
        .then(function(res) {
          if(!res.data) {
            alert('something went wrong. can\'t get access token.');
            // redirect('/')
          }
          redirect('/user?token=' + res.data + '&service=github')
        })
        .catch(function(err) {
          alert('something went wrong. request failed.');
          console.log(err)
          redirect('/')
        })
  },
}
</script>

<style scoped>

</style>