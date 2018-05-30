<template>
  <b-container class="Title" fluid>
    <b-row>
      <p></p>
    </b-row>
    <b-row>
      <b-col>
        <b-img style ="width: 30%" v-on:click="gotoIndex" src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png"></b-img>
      </b-col>
      <b-col cols="8">
      </b-col>
      <b-col>
        <button type="button" class="btn btn-primary" v-on:click="gotoSignIn">Log In</button>
        <button type="button" class="btn btn-primary" v-on:click="gotoSignUp">Sign Up</button>
      </b-col>
    </b-row>
    <b-row align-h="center">
    </b-row>
<br>
  <b-jumbotron header="Thank you for using Scrum Tool!" lead="We hope to see you again!" >
  </b-jumbotron>
  </b-container>

</template>

<script>
    export default {
        name: "Logout",
        methods: {
          gotoSignIn() {
            this.$router.push({path: '/signin'});
          },
          gotoIndex() {
            this.$router.push({path: '/'});
          },
          gotoSignUp() {
            this.$router.push({path: '/signup'});
          },
        },
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
