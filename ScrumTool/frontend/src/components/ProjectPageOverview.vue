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
        <b-col>
        	<b-row>
        		<b-col class="text-left">
				<h2>{{projectOverview.project.title}}</h2>
			</b-col>

			<b-col class="text-right">
					<b-dropdown style="margin-left: 45px; height: 35px; width: 35%; left:10%" size="mr-sm-2" right>
						<template slot="button-content">
							<b-img src="https://cdn3.iconfinder.com/data/icons/3d-printing-icon-set/512/Edit.png" style="width:20px; margin-right: 5px"/> Edit Project
						</template>

						<template>
							<div style="margin-right: 10px; margin-left: 10px">
								<p> New Project's Title</p>
								<b-form-input v-model="text1" type="text" placeholder=" " style="margin-top: -10px"></b-form-input>
								<p style="margin-top: 5px">New Project's Deadline</p>
								<b-form-input v-model="text1" type="text" placeholder=" New Deadline" style="margin-top: -10px"></b-form-input>
								<b-button variant="success" style="margin-top: 10px; width: 100%;">Save changes</b-button>
							</div>
						</template>
					</b-dropdown>
				</b-col>
			</b-row>
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
                  <b-form inline @submit="addMembers">
                    Add User &nbsp;
                    <b-form-group id="emailForm"
                                  label-for="email"
                                  :invalid-feedback="validEmail===false ? 'Invalid User' : ''">
                      <b-form-input class="mb-2 mr-sm-2 mb-sm-0"
                                        id="emailInput"
                                        type="email"
                                        v-model="newMember.mail"
                                        @change="checkEmail"
                                        :state="validEmail"
                                        placeholder="email" required>
                       </b-form-input>
                    </b-form-group>
                    as &nbsp;
                    <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                                   :value="null"
                                   :options="{ '1': 'Scrum Master', '2': 'Developer'}"
                                   v-model="newMember.role"
                                   id="inlineFormCustomSelectPref">
                      <option slot="first" :value="null">Choose role</option>
                    </b-form-select>
                    <b-button type="submit" variant="primary" :disabled="validEmail===false" >Invite</b-button>
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
          href: '#/'
        }, {
          text: '',
          href: '#'
        }, {
          text: 'Overview',
          active: true
        }],

        newMember: {
          mail: '',
          role: ''
        },

        validEmail: null,
        diffDays: -1,
        Team: []
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
        var jsDate=new Date (Date.parse(self.projectOverview.currentSprintExpDate.replace ('-', '/', 'g')));
        var oneDay = 24*60*60*1000;
        self.diffDays=Math.floor(Math.abs((today.getTime() - jsDate.getTime())/(oneDay)));
        // Check the days interval
        self.diffDays += 2
      },

      getMembers () {
        const self=this;
        axios.get('http://localhost:8765/app/api/users/'+ localStorage.getItem('userId') +'/projects/' + this.$route.params.id + '/members', {
          headers: { "auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json' }
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
            self.Team = response.data.results;
            console.log("Got the members");
          }
        })
        .catch(function (error) {
          console.log(error);
        })
      },

      addMembers () {
        const self = this;
        if (self.newMember.role == '1'){
          self.newMember.role = "Scrum Master";
        }
        else if (self.newMember.role == '2'){
          self.newMember.role = "Developer";
        }
        axios.post('http://localhost:8765/app/api/users/'+ localStorage.getItem('userId') +'/projects/' + this.$route.params.id + '/members', self.newMember, {
          headers: { "auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json' }
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
            self.Team.push(response.data.results);
            console.log("New member inserted");
          }
        })
        .catch(function (error) {
          console.log(error);
        })
      },

      checkEmail () {
        if (this.newMember.mail==='') {
          this.validEmail=null;
          return;
        }
        const self = this;
        // Check if the user is among the members already in project
        var i;
        for (i = 0; i < self.Team.length; i++){
          if (self.Team[i].mail == self.newMember.mail){
            this.validEmail=false;
            return;
          }
        }
        // Now check the users in database
        axios.post('http://localhost:8765/app/api/exists', {
          mail: self.newMember.mail,
        })
          .then(function (response) {
            self.validEmail=(response.data.exists===1);
          })
          .catch(function (error) {
            console.log(error);
          })
      },
    },

    mounted () {
      this.getSprintInfo();
      this.getMembers();
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

