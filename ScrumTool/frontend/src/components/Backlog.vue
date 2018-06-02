<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
  <b-row>

    <b-card-group v-for="cur_pbi in currentPbis" :key="cur_pbi.idPBI" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
      <b-card :title="cur_pbi.title" img-top tag="article" style="max-width: 15rem;" class="mb-2"
              header="Epic"
              header-tag="header"
              :footer="cur_pbi.priority"
              footer-tag="footer">
        <p class="card-text">
          {{cur_pbi.description}}
        </p>
        <b-btn v-b-toggle="'collapse'+cur_pbi.idPBI" v-on:click="getEpicUserStories(cur_pbi.idPBI)" variant="primary">Toggle Collapse</b-btn>
        <b-collapse :id="'collapse'+cur_pbi.idPBI" class="mt-2">
        <div>
          <b-card-group v-for="cur_us in currentUserStories[cur_pbi.idPBI]" :key="cur_us.idPBI" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
          <b-card no-body class="mb-1">
            <b-card-header header-tag="header" class="p-1" role="tab">
              <b-btn block href="#"  v-b-toggle="'collapse'+cur_us.idPBI" variant="info">Accordion 1</b-btn>
            </b-card-header>
            <b-collapse :id="'collapse'+cur_us.idPBI" visible accordion="my-accordion" role="tabpanel">
              <b-card-body>
                <p class="card-text">
                   {{cur_us.description}}
                </p>
              </b-card-body>
            </b-collapse>
          </b-card>
          </b-card-group>
          <!--
          <b-btn v-b-toggle="'collapse'+cur_pbi.idPBI" v-on:click="getEpicUserStories(cur_pbi.idPBI)" variant="primary">Toggle Collapse</b-btn>
          <b-collapse :id="'collapse'+cur_pbi.idPBI" class="mt-2">
            <b-card>
              <p class="card-text">Description:{{currentUserStories[0].description}} </p>
            </b-card>
          </b-collapse>
          -->
        </div>
        </b-collapse>
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
      currentUserStories: [[],[]],
      teamData: json.team,
    }
  },
  methods: {

    getPBIS() {
      //evt.preventDefault();
      const self = this;
      axios.get('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + 5 + '/pbis?isEpic=true', {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          if (response.data.results) {
             //prepei na to spasw se synartisi
            response.data.results.forEach(function(arrayItem)
            {
              if(arrayItem.priority===1)
              {
                arrayItem.priority="High";
              }
              else if(arrayItem.priority===2)
              {
                arrayItem.priority="Medium";

              }
              else if(arrayItem.priority===3)
              {
                arrayItem.priority="Low";
              }
            });
            self.currentPbis = response.data.results;
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    getEpicUserStories(epicId) {
      const self = this;
    axios.get('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + 5 + '/pbis?isEpic=false&epicId='+epicId, {
    headers: {"auth": localStorage.getItem('auth_token')}
    })
    .then(function (response) {
      if (response.data.error) {
        if (response.data.error === "Unauthorized user") {
          console.log("Unauthorized user");
        }
      }
      if (response.data.results) {
        self.currentUserStories[epicId] = response.data.results;


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
