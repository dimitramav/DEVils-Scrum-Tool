<template>
  <b-container class="Navigation" fluid>
    <navbar :dashboard="false"></navbar>
    <br>
    <b-card v-if="userInfos.mail != null"
            img-src="https://picsum.photos/600/300?image=445"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width:19rem;margin: 0 auto;float: none; margin-bottom: 10px;"
            class="mb-2">
            
              <div style="text-align: left;">
                <h2 class="card-title"> {{userInfos.firstname}} {{userInfos.lastname}} </h2>
                <p> Username: <b>{{userInfos.username}}</b></p>
                <p> Email   : <b>{{userInfos.mail}}</b><br>
                <p v-if="userInfos.country != null"> Country : <b>{{userInfos.country}}</b> </p>
                <p v-if="userInfos.job != null"> Job     : <b>{{userInfos.job}}</b></p>
                <p v-if="userInfos.company != null"> Works at: <b>{{userInfos.company}}</b> </p>
                <p> Number of projects: <b>{{userInfos.numProjects}}</b></p>
              </div>
            
            <b-button variant="primary" v-show="loggedin_userId===userInfos.idUser" @click="gotoEdit">Edit Information</b-button>
            <b-row> <p></p></b-row>
            <b-card-footer class="text-muted" > {{userInfos.description}} </b-card-footer>
    </b-card>
    <b-card v-else
            img-src="https://picsum.photos/600/300?image=1062"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width:19rem;margin: 0 auto;float: none; margin-bottom: 50%;"
            class="mb-2">        
              <div style="text-align: left;">
                <h2 class="card-title"> User Not Found</h2>
              </div>
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
        this.$router.push({path: '/editprofile'});
      },
      getProfile() {
        const self = this;
        axios.get(this.$url +'users/' + localStorage.getItem('userId') + '/profile/' + self.current_username, {
          headers: {"auth": localStorage.getItem('auth_token')}
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
              else console.log(response.data.error);
            }
            else if (response.data.results) self.userInfos = response.data.results;
            else console.log("Unresolved response: " + response);
          })
          .catch(function (error) {
            console.log(error);
          });
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


