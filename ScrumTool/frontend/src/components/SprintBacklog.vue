<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
    <b-container fluid>
      <!--Sprint Infos-->
      <b-row>
        <b-col class="center">
        <h1>Sprint #{{currentSprint.numSprint}}</h1>
        </b-col>
      </b-row>
      <hr>
      <br>
      <b-row class="text-muted">
        <b-col>
          <h4><i>Sprint Goal: {{currentSprint.goal}}</i></h4>
        </b-col>
        <b-col>
          <h4><i>Sprint Plan: {{currentSprint.plan}}</i></h4>
        </b-col>
        <b-col>
          <h4><i>Duration: {{currentSprint.deadline}}</i> </h4>
        </b-col>
      </b-row>
      <hr>
      <br>
      <!--board-->
      <b-row>

      </b-row>
      <b-row>
        <b-col>
          <h2>User Stories</h2>
        </b-col>
        <b-col>
          <h2>TO DO</h2>
        </b-col>
        <b-col>
          <h2>DOING</h2>
        </b-col>
        <b-col>
          <h2>DONE</h2>
        </b-col>
      </b-row>
    </b-container>
  </b-container>
</template>

<script>
  import axios from 'axios'
  import Navbar from "./Navbar.vue"
  export default {
    components: {
      navbar: Navbar,
    },
    data() {
      return {
        currentSprint:[],
        currentProject_id:'',
      }
    },
    methods: {
      getSprintInfos() {
        const self = this;
        axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/sprints?isCurrent=true', {
          headers: {"auth": localStorage.getItem('auth_token')}
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") {
                console.log("Unauthorized user");
              }
            }
            if (response.data.results) {
              self.currentSprint = response.data.results;
              console.log(self.currentSprint);
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      },

    },
    mounted(){
      this.currentProject_id=parseInt(this.$route.params.id);
      this.getSprintInfos();

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
  .modal-backdrop.in {
    opacity: 0.9;
  }
</style>
