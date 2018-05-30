<template>
  <b-container class="Navigation" fluid>

    <navbar></navbar>
    <br>
  <b-row>
    <b-card-group v-for="cur_project in currentProjects" :key="cur_project.idProject" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
      <b-card :title="cur_project.title" img-top tag="article" style="max-width: 15rem;" class="mb-2">
        <p class="card-text">
          Deadline: {{cur_project.deadlineDate}}
        </p>
        <div>
          <b-btn  @click="showCollapse=cur_project.title"
                  class="collapsed"
                 aria-controls="collapse">
            Toggle Collapse
          </b-btn>
          <b-collapse class="mt-2" v-model="showCollapse===cur_project.title" id="collapse">
            <b-card>
              <p class="card-text">Collapse contents Here</p>
            </b-card>
          </b-collapse>
        </div>
      </b-card>
    </b-card-group>
  </b-row>
  </b-container>

</template>


<script>import axios from 'axios'
import Navbar from "./Navbar.vue"
import json from '../assets/team.json'
export default {
  components: {
    navbar: Navbar,
  },
  data() {
    return {
      form: {
        newTitle: '',
        deadlineDate: '',
        isDone: false
      },
      logOut: null,
      currentProjects: [],
      doneProjects: [],
      teamData: json.team,
      showCollapse: null,
    }
  },
  methods: {
    getProjects() {
      //evt.preventDefault();
      const self = this;
      axios.get('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects?isDone=false&limit=10&offset=0', {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          if (response.data.results) {
            self.currentProjects = response.data.results;
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  },
    mounted() {
      if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
      this.getProjects();
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
</style>
