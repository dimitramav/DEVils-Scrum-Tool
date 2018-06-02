<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
    <b-row>
      <b-col class="text-right">
        <div>
          <b-dropdown id="ddown1" size="lg" variant ="info" text="Create New Epic" class="m-md-2">
            <b-form inline style="margin: 10px;" @submit="newEpic">
              <h4>Title:</h4>
              <label class="sr-only" for="newEpicName"></label>
              <b-form-input id="newEpicName" placeholder="New Epic's Name" v-model="form.newTitle" required/>
              <h5>Description:</h5>
              <label class="sr-only" for="newEpicDescription"></label>
              <b-form-input id="newEpicDescription" placeholder="New Epic's Description" v-model="form.newDescription" required/>
              <h5>Priority:</h5>
              <b-row class="text-center">
                <b-col>
                  <label for="high">High</label>
                  <input type="radio" id="high" value="High" v-model="priority_picked">
                </b-col>
                <b-col>
                  <label for="medium">Medium</label>
                  <input type="radio" id="medium" value="Medium" v-model="priority_picked">
                </b-col>
                <b-col>
                  <label for="low">Low</label>
                  <input type="radio" id="low" value="Low" v-model="priority_picked">
                </b-col>
              </b-row>
              <b-button variant="success" type="submit" style="margin-top: 10px; width: 100%;">Add to Epics</b-button>
            </b-form>
          </b-dropdown>
        </div>
      </b-col>
    </b-row>
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
      form: {
        newTitle: '',
        newDescription: '',
      },
      priority_picked: '',
      logOut: null,
      currentPbis: [],
      currentUserStories: [[],[]],
      teamData: json.team,
    }
  },
  methods: {

    newEpic (evt) {
      evt.preventDefault();
      const self = this;
      if(this.priority_picked==="High")
      {
        this.priority_picked=1;
      }
      else if(this.priority_picked=="Medium")
      {
        this.priority_picked=2;
      }
      else if(this.priority_picked=="Low")
      {
        this.priority.picked=3;
      }
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      }
      let data = {
        title: this.form.newTitle, description: this.form.newDescription, priority:this.priority_picked,
      }
      axios.post('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + 3 + '/pbis?isEpic=true', data, config)
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
            else if (response.data.error === "Unauthorized projects") {
              console.log("Unauthorized projects");
            }
            else if (response.data.error === "null") {
              console.log("Null token");
            }
          }
          if (response.data.results) {
            console.log(response.data.results);
            self.currentProjects.push(response.data.results);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
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
    axios.get('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + 3 + '/pbis?isEpic=false&epicId='+epicId, {
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
