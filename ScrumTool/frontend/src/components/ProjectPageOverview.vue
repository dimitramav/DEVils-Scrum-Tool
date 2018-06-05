<template>
  <div v-if = "true">
    <b-container class="Navigation" fluid>

      <navbar :dashboard="true"></navbar>
      <!--<sidebar></sidebar>-->
      <br>
      <b-row>
        <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
      </b-row>
      <b-row style="padding-top:10px;">
        <b-col class="text-left">
          <h2>{{projectOverview.project.title}}</h2>
        </b-col>
      </b-row>
      <b-row>
        <div class="line">.</div>
      </b-row>

      <b-row>
        <b-col>

          <div style="height: 100%">
            <b-container fluid>
              <b-jumbotron>
                <br>
                <b-row>
                  <h2>Current sprint #{{projectOverview.currentSprintNum}}</h2>
                </b-row>
                <br>
                <b-row>
                  <b-col>
                    <b-card title="TODO">
                      <p class="card-text">{{projectOverview.todo}}</p>
                      <p class="card-text" style="color: red">Issues: {{projectOverview.todoIssues}}</p>
                    </b-card>
                  </b-col>
                  <b-col>
                    <b-card title="DOING">
                      <p class="card-text">{{projectOverview.doing}}</p>
                      <p class="card-text" style="color: red">Issues: {{projectOverview.doingIssues}}</p>
                    </b-card>
                  </b-col>
                  <b-col>
                    <b-card title="DONE">
                      <p class="card-text">{{projectOverview.done}}</p>
                      <p class="card-text" style="color: red">Issues: {{projectOverview.doneIssues}}</p>
                    </b-card>
                  </b-col>
                </b-row>
                <br><br><br>
                <b-row>
                  <b-col>
                    <b-card no-body class="text-left">
                      <p class="card-text">Days Remaining:  {{diffDays}}</p>
                    </b-card>
                  </b-col>
                  <b-col></b-col>
                </b-row>
                <br><br><br>
                <b-row style="padding-top:10px;">
                  <b-col class="text-left">
                    <h5>Progress</h5>
                  </b-col>
                </b-row>
                <b-progress :value="donePercentage" show-progress class="mb-3"></b-progress>
                <br>
                <b-button v-if="false" variant="primary">Go to Sprint Page</b-button>
                <b-button v-else variant="primary" :to="{name: 'NewSprint', params: {id:$route.params.id}}">Create new Sprint</b-button>
                <br><br>

              </b-jumbotron>
            </b-container>
          </div>
        </b-col>
        <b-col>

          <b-container fluid>
            <b-jumbotron>
              <br>
              <b-row>
                <h2>Team</h2>
              </b-row>
              <br>
              <b-list-group v-for="teamMember in Team" v-bind:data="teamMember"
                            v-bind:key="teamMember.mail">
                <b-list-group-item class="flex-column align-items-start">
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">{{teamMember.role}}</h5>
                  </div>
                  <p align="left">
                    Name: {{teamMember.lastname}} {{teamMember.firstname}}
                    <br>
                    Email: {{teamMember.mail}}
                  </p>
                </b-list-group-item>
              </b-list-group>
              -->
              <br>
              <template>
                <div>
                  <b-form inline @submit="getUsers">
                    Add User &nbsp;
                    <b-input class="mb-2 mr-sm-2 mb-sm-0" id="inlineFormInputName2" placeholder="email" />
                    as &nbsp;
                    <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                                   :value="null"
                                   :options="{ '1': 'Scrum Master', '2': 'Developer'}"
                                   id="inlineFormCustomSelectPref">
                      <option slot="first" :value="null">Choose role</option>
                    </b-form-select>
                    <b-button variant="primary">Invite</b-button>
                  </b-form>
                </div>
              </template>
              <br><br>

            </b-jumbotron>
          </b-container>
        </b-col>
      </b-row>

    </b-container>
  </div>
  <div v-else>
    <b-container class="Navigation" fluid>
      <navbar></navbar>
      <br>
      <b-row>
        <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
      </b-row>
      <b-row style="padding-top:10px;">
        <b-col class="text-left">
          <h2>Insurance App</h2>
        </b-col>
      </b-row>
      <b-row>
        <div class="line">.</div>
      </b-row>
      <br>
      <b-button variant="primary">Create new Sprint</b-button>
    </b-container>
  </div>
</template>


<script>
  import axios from 'axios'
  import Sidebar from "./Sidebar.vue"
  import Navbar from "./Navbar.vue"
  import json from '../assets/team.json'

  export default {
    components: {
      navbar: Navbar,
      sidebar: Sidebar,
    },
    data() {
      return {
        projectOverview: {
          project: {
            idProject: 0,
            title: '',
            isDone: false,
            deadlineDate: ''
          },
          currentSprintId: 0,
          currentSprintNum: 0,
          currentSprintExpDate: '',
          todo: 0,
          doing: 0,
          done: 0,
          todoIssues: 0,
          doingIssues: 0,
          doneIssues: 0
        },

        donePercentage: 0,
        selected: [], // Must be an array reference!

        items: [{
          text: 'Home',
          href: '#'
        }, {
          text: '',
          href: '#'
        }, {
          text: 'Overview',
          active: true
        }],

        //currentSprintId: 1997,
        //currentSprintExpDate: '',
        diffDays: -1,
        Team: [{role: "Senior Developer", lastname: "Anastasiou", firstname:"Aggelos", mail: "aggelos@gmail.com"}, {role: "Junior Developer", lastname: "Helen", firstname: "Brown", mail: "helb@gmail.com"}]
        //todo: 6,
        //doing: 10,
        //done: 2,
        //todoIssues: 99,
        //doingIssues: 1,
        //doneIssues: 0,
      }
    },

    methods: {
      getSprintInfo () {
        const self = this;
        axios.get ('http://localhost:8765/app/api/users/'+ localStorage.getItem('userId') +'/projects/'+ this.$route.params.id,{
          headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
        })
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
            self.projectOverview = response.data.results;
            self.items[1].text = self.projectOverview.project.title;
            var totalTasks = self.projectOverview.todo + self.projectOverview.doing + self.projectOverview.done;
            self.donePercentage = self.projectOverview.done / totalTasks * 100;
            self.calcDeadline ();
            console.log("Got the results");
          }
        })
        .catch(function (error) {
          console.log(error);
        })
      },

      calcDeadline () {
        const self = this;
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1;
        var yyyy = today.getFullYear();
        var jsDate=new Date (Date.parse(self.projectOverview.project.deadlineDate.replace ('-', '/', 'g')));
        var oneDay = 24*60*60*1000;
        self.diffDays=Math.floor(Math.abs((today.getTime() - jsDate.getTime())/(oneDay)));
      },

      getUsers () {
        const self=this; // προσαρμοσε το axios για users αντι για projects
        axios.get('http://localhost:8765/app/api/users/'+localStorage.getItem('userId') + '/projects?isDone=false&limit=9&offset=0', {
          headers: { "auth": localStorage.getItem('auth_token') }
        })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Wrong user") {
              console.log("Wrong user");
            }
          }
          if (response.data.results) {
            self.Team = response.data.results;
          }
        })
        .catch(function (error) {
          console.log(error);
        })
      },
    },

    mounted () {
      this.getSprintInfo();
//      this.getUsers ();
    },
  }

</script>


<style scoped>

  .Navigation {
    position: absolute;
    top: 0;
    left: 0;
  }
  .line {
    width: 99%;
    font-size: 1px;
    line-height: 2px;
    background-color: lavender;
    margin-top: -6px;
    margin-bottom: 10px;
  }

  .row {
    margin-right: 0px;
    margin-left: 0px;
  }
  .container-fluid {
    padding-right: 0;
    padding-left: 0;
  }
</style>

