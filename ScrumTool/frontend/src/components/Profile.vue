<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>

  </b-container>
</template>


<script>
  import axios from 'axios'
  import Navbar from "./Navbar.vue"
  import json from '../assets/team.json'
  export default {
    components: {
      navbar: Navbar,
    },
  data () {
    return {
      userInfos: '',
    }
  },
  methods: {
    getProfile() {
      //evt.preventDefault();
      const self = this;
      axios.get(this.$url + localStorage.getItem('userId') + '/profile/' + this.current_username, {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          if (response.data.results) {
            self.userInfos = response.data.results;
            console.log(self.userInfos);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  },
    mounted() {
      if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
      this.current_username=this.$route.params.id;
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

