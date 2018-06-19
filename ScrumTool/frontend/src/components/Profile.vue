<template>
  <b-container class="Navigation" fluid>
    <navbar :dashboard="false"></navbar>
    <b-breadcrumb :items="items"/>
    <br>
    <b-card img-src="https://picsum.photos/600/300/?image=25"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width:19rem;margin: 0 auto;float: none; margin-bottom: 10px;"
            class="mb-2">
        <div style="text-align: left;">
          <h2>{{userInfos.firstname}} {{userInfos.lastname}} </h2><br/>
          <p>Username: <b>{{userInfos.username}}</b></p>
          <p>Email   : <b>{{userInfos.mail}}</b><br>
          <p>Number of projects: <b>{{userInfos.numProjects}}</b></p>
        </div>
      <b-button variant="primary" v-show="loggedin_userId===userInfos.idUser" @click="gotoEdit">Edit Information</b-button>
    </b-card> 
  </b-container>
</template>

<script>
  import axios from 'axios'
  import Navbar from "./Navbar.vue"
  export default {
    components: {
      navbar: Navbar,
    },
    data () {
      return {
        userInfos: [],
        items: [{
          text: 'Home',
          href: '#/'
        },
          {
            text: 'Profile',
            active: true
          }],
        loggedin_userId: 0,
        current_username: '',
      }
    },

    methods: {
      gotoEdit() {
        this.$router.push({path: '/editprofile/' + this.current_username});
      },
      getProfile() {
        //evt.preventDefault();
        const self = this;
        axios.get(this.$url +'users/' + localStorage.getItem('userId') + '/profile/' + self.current_username, {
          headers: {"auth": localStorage.getItem('auth_token')}
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") {
                console.log("Unauthorized user");
              }
            }
            if (response.data.results) {
              //self.$set(self.userInfos, 'idUser', response.data.results.idUser);
              self.userInfos = response.data.results;
              console.log(self.userInfos);
            }
          })
          .catch(function (error) {
            console.log(error);
          });
        //console.log (typeof (this.loggedin_userId) + typeof (this.userInfos.idUser));
      },
    },
    mounted() {
      if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
      this.current_username=this.$route.params.id;
      this.loggedin_userId=parseInt(localStorage.getItem('userId'));
      this.getProfile();
    },
  }
</script>
<style scoped>

  .Navigation {
    position: absolute;
    top: 0;
    left: 0;
  }

  .container-fluid {
    padding-right: 0;
    padding-left:0;
  }


  .row {
    margin-right: 0px;
    margin-left: 0px;
  }
</style>


