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
    let state = this.$route.query.state;
    let code = this.$route.query.code;
    if (state === undefined){
      var hash = window.location.hash;
      var hashParams = new URLSearchParams(hash.substring(1));
      code = hashParams.get('access_token');
      state = hashParams.get('state');
    }
    axios.get('http://localhost/api/users/login/' + state + '/' + code)
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