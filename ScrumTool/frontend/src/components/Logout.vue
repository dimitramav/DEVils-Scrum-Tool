<template>
  <b-container class="Navigation" fluid>
    <navbar :logout_prop="true" class="pcsprint"></navbar>
  <b-jumbotron class="pcsprint" header="Thank you for using Scrum Tool!" lead="We hope to see you again!" >
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
  @import url('https://fonts.googleapis.com/css?family=Merienda');
  @import url('https://fonts.googleapis.com/css?family=VT323');
  @import url('https://fonts.googleapis.com/css?family=Quicksand');

  .ptitle{
    font-family: Merienda;
  }

  .pdeadline{
    font-family: VT323;font-size:24px; margin-top: 1%;
  }

  .pcsprint{
    font-family: Quicksand;
  }
  .Title{
    position: absolute;
    top: 0;
    left: 0;
  }
</style>
