<template>
</template>

<script>
import axios from "axios";
import {inject} from "vue";

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

    axios.get(process.env.VUE_APP_SERVER_URL + '/api/users/login?loginType=' + state + '&code=' + code,
        { withCredentials : true})
        .then(function(res) {
          if(!res.data) {
            redirect('/')
          }
          redirect('/');
        })
        .catch(function(err) {
          let user = err.response.data.data;
          console.log(err);
          alert('회원가입이 필요합니다.');

          let email = user.email;
          let loginType = user.loginType;
          redirect('/user/register/' + loginType + '/' + email);
        })
  },
}
</script>

<style scoped>

</style>