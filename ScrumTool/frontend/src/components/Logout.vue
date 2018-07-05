<template>
  <b-container class="Navigation" fluid>
    <navbar :logout_prop="true"></navbar>
  <b-jumbotron header="Thank you for using Scrum Tool!" lead="We hope to see you again!" >
  </b-jumbotron>
  </b-container>

</template>

<script>
  import Navbar from "./Navbar.vue"
    export default {
        components:{
          navbar:Navbar,
        },
        name: "Logout",
      mounted (){
        //redirect to user homepage if user is already logged in
        if (localStorage.getItem('auth_token')!=='null' && localStorage.getItem('userId')!=='null') this.$router.push({path: '/'});
      },

    }
</script>

<style scoped>
  .Title{
    position: absolute;
    top: 0;
    left: 0;
  }
</style>
