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
                  <input type="radio" name="myChoice" id="high" value="High" v-model="priority_picked" required>
                </b-col>
                <b-col>
                  <label for="medium">Medium</label>
                  <input type="radio" name="myChoice" id="medium" value="Medium" v-model="priority_picked" required>
                </b-col>
                <b-col>
                  <label for="low">Low</label>
                  <input type="radio" name="myChoice" id="low" value="Low" v-model="priority_picked" required>
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
      <b-card :title="cur_pbi.title" img-top tag="article" class="mb-2"
              header="Epic"
              header-tag="header"
              :footer="cur_pbi.priority"
              footer-tag="footer">
        <p class="card-text">
          {{cur_pbi.description}}
        </p>
        <b-btn v-b-toggle="'icollapse'+cur_pbi.idPBI" v-on:click="getEpicUserStories(cur_pbi.idPBI)" variant="primary">User Stories</b-btn>
        <b-collapse :id="'icollapse'+cur_pbi.idPBI" class="mt-2">
        <div>
          <b-card-group v-for="cur_us in currentUserStories[cur_pbi.idPBI]" :key="cur_us.idPBI" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
            <b-card class="mb-1">
            <b-card-header header-tag="header" class="p-1" role="tab">
              <b-btn block href="#"  v-b-toggle="'collapse'+cur_us.idPBI" variant="info">{{cur_us.title}}</b-btn>
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
    priorityToNumber(priority)
    {
      if(priority==="High")
      {
        priority=1;
      }
      else if(priority==="Medium")
      {
        priority=2;
      }
      else if(piority==="Low")
      {
        priority=3;
      }
      return priority;
    },
    priorityToString(priority)
    {
      if(priority===1)
      {
        priority="High";
      }
      else if(priority===2)
      {
        priority="Medium";

      }
      else if(priority===3)
      {
        priority="Low";
      }
      return priority;
    },
    newEpic (evt) {
      evt.preventDefault();
      const self = this;
      this.priority_picked=self.priorityToNumber(this.priority_picked);
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      }
      let data = {
        title: this.form.newTitle, description: this.form.newDescription, priority:this.priority_picked, Project_id: this.currentProject_id,
      }
      axios.post('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis?isEpic=true', data, config)
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
            response.data.results.priority=self.priorityToString(response.data.results.priority);
            self.currentPbis.push(response.data.results);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    getPBIS() {
      //evt.preventDefault();
      const self = this;
      axios.get('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + this.$route.params.id + '/pbis?isEpic=true', {
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
              arrayItem.priority=self.priorityToString(arrayItem.priority);
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
    axios.get('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + this.$route.params.id + '/pbis?isEpic=false&epicId='+epicId, {
    headers: {"auth": localStorage.getItem('auth_token')}
    })
    .then(function (response) {
      if (response.data.error) {
        if (response.data.error === "Unauthorized user") {
          console.log("Unauthorized user");
        }
      }
      if (response.data.results) {
        self.$set(self.currentUserStories,epicId,response.data.results);

      }
    })
    .catch(function (error) {
      console.log(error);
    })
},
  },
    mounted() {
      if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
      this.currentProject_id=this.$route.params.id;
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


.row {
  margin-right: 0px;
  margin-left: 0px;
}
</style>
