<template>
hihi
</template>

<script>
import axios from "axios";

export default {
  name: "LoginCallback",
  methods: {
    redirect: function(url) {
      window.location.href = url;
    }
  },
  created() {
    const redirect = this.redirect;
    alert(this.$route.query.code);
    // axios.get('http://localhost:3000/login?code=' + this.$route.query.code + '&state=' + this.$route.query.state)
    axios.get('http://localhost/api/users/info/GITHUB/' + this.$route.query.code)
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