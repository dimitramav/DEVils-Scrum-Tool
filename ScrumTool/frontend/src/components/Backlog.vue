<template>
  <b-container class="Navigation" fluid>

    <navbar></navbar>
    <br>
  <b-row>

    <b-card-group v-for="cur_pbi in currentPbis" :key="cur_pbi.idPBI" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
      <b-card :title="cur_pbi.title" img-top tag="article" style="max-width: 15rem;" class="mb-2">
        <p class="card-text">
          Description: {{cur_pbi.description}}
        </p>
        <div>

          <b-btn v-b-toggle="'collapse'+cur_pbi.idPBI" variant="primary">Toggle Collapse</b-btn>
          <b-collapse :id="'collapse'+cur_pbi.idPBI" class="mt-2">
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
      logOut: null,
      currentPbis: [],
      teamData: json.team,
    }
  },
  methods: {
    getPBIS() {
      //evt.preventDefault();
      const self = this;
      axios.get('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + 3 + '/pbis?isEpic=true', {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          if (response.data.results) {
            self.currentPbis = response.data.results;
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  },
    mounted() {
      if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
      this.getPBIS();
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
