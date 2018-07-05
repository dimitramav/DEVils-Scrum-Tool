<template>
  <div>
    <b-container class="Navigation" fluid>
      <navbar :dashboard="true"></navbar>
      <!--<sidebar></sidebar>-->
      <br>
      <b-row style="padding-top:10px; margin-bottom: -10px">
        <b-col>
          <b-row>
            <b-col class="text-left">
              <h2 class="ptitle">{{projectOverview.project.title}}</h2>
            </b-col>
            <b-col></b-col>
            <b-col></b-col>
            <b-col>
              <p class="pcsprint" style="font-weight:bold;">Project End:</p>
            </b-col>
            <b-col>
              <p class="pcsprint"> {{projectOverview.project.deadlineDate}} </p>
            </b-col>
            <b-col></b-col>
            <b-col v-if="(projectOverview.project.isDone === false )" class="text-right">
              <b-dropdown v-if="isProductOwner()===true" style="margin-left: 45px; height: 35px; width: 35%; left:10%" size="mr-sm-2" right>
                <template slot="button-content" class="pcsprint">
                  <b-img src="https://cdn3.iconfinder.com/data/icons/3d-printing-icon-set/512/Edit.png" style="width:20px; margin-right: 5px;"/> Edit Project
                </template>
                <template>
                  <b-form inline style="margin: 10px;" @submit="updateProject">
                    <p> New Project's Title</p>
                    <b-form-input type="text" style="margin-top: -10px" v-model="form.newTitle" required></b-form-input>
                    <p style="margin-top: 5px">New Project's Deadline</p>
                    <div>
                      <b-form-input id="inputLive" v-model.trim="form.deadlineDate" type="date" :state="dateState"></b-form-input>
                      <b-form-invalid-feedback id="inputLiveFeedback">
                        Project's Deadline cannot be a past or current date.
                      </b-form-invalid-feedback>
                    </div>
                    <b-button letiant="success" type="submit" :disabled="dateState===false" style="margin-top: 10px; width: 100%;">Save changes</b-button>
                  </b-form>
                </template>
              </b-dropdown>
            </b-col>
            <b-col></b-col>
            <br>
            <br>
          </b-row>
        </b-col>
      </b-row>

      <b-row>
        <div class="line">.</div>
      </b-row>
      <!--{{projectOverview}}-->
      <b-row>
        <b-col>

          <div v-if="(projectOverview.project.isDone === false )" style="height: 100%">
            <b-container fluid>
              <b-jumbotron style="background-color: #f7f7f7;">
                <div v-if="(projectOverview.currentSprintNum === 0)">
                  <b-button class="pcsprint" letiant="primary" :to="{name: 'NewSprint', params: {id:$route.params.id}}"> Create New Sprint </b-button>
                </div>
                <div v-else>
                  <b-row style="margin-top: -5%">
                    <h2 class="pcsprint">Current sprint #{{projectOverview.currentSprintNum}}</h2>
                  </b-row>
                  <br>
                  <b-row>
                    <b-col>
                      <b-card title="TODO" class="pcsprint">
                        <p class="card-text">{{projectOverview.todo}}</p>
                      </b-card>
                    </b-col>
                    <b-col>
                      <b-card title="DOING" class="pcsprint">
                        <p class="card-text">{{projectOverview.doing}}</p>
                      </b-card>
                    </b-col>
                    <b-col>
                      <b-card title="DONE" class="pcsprint">
                        <p class="card-text">{{projectOverview.done}}</p>
                      </b-card>
                    </b-col>
                  </b-row>
                  <br>
                  <b-row style="padding-top:10px;">
                    <b-col class="text-left">
                      <h5 class="pcsprint">Progress </h5>
                      <b-progress :value="donePercentage" :max="max" class="mb-3"></b-progress>
                    </b-col>
                    <b-col md="4" class="py-4">
                      <b-btn class="pcsprint" id="exButton1" letiant="outline-danger">Issues</b-btn>
                      <b-tooltip target="exButton1"> {{issues}}</b-tooltip>
                    </b-col>
                  </b-row>

                  <b-row>

                    <b-col></b-col>
                  </b-row>
                  <br>
                  <b-row>
                    <b-col>
                      <p class="pcsprint" style="font-weight:bold;">Sprint End: {{sdeadline}}</p>
                    </b-col>
                    <b-col>
                      <div v-if="( diffDays > -1)">
                        <p class="pcsprint" style="font-weight:bold;">Days Remaining: {{diffDays}}</p>
                      </div>
                    </b-col>
                  </b-row>
                  <br>
                  <br>
                  <div v-if="( diffDays < 0)">
                    <b-button letiant="primary" :to="{name: 'NewSprint', params: {id:$route.params.id}}"> Create New Sprint! </b-button>
                  </div>
                  <div v-else>
                    <b-button class="pcsprint" letiant="success" :to="{name: 'SprintBacklog', params: {id:$route.params.id}}"> Go to Sprint Page </b-button>
                  </div>
                </div>
              </b-jumbotron>
            </b-container>
          </div>
          <div v-else style="height: 100%">
            <b-container fluid>
              <b-jumbotron>
                <b-row>
                  <p style="font-size: 180%; color: #264d73"> This Project has finished... Go back to Home Page to create a new one! </p>
                </b-row>
                <b-button letiant="primary" href="#/" style="margin-top: 10%"> Home Page </b-button>
              </b-jumbotron>
            </b-container>
          </div>

        </b-col>
        <b-col>
          <b-container fluid>
            <b-jumbotron style="background-color: #f7f9fc;">
              <b-row style="margin-top: -5%">
                <h2 class="pcsprint">Team</h2>
              </b-row>
              <br>

              <b-container fluid>
                <b-row>

                  <b-col cols="11">
                    <div id="scrollspy-nested" style="position:relative;height:328px;overflow-y:auto">

                      <b-list-group v-for="teamMember in Team" v-bind:data="teamMember" v-bind:key="teamMember.mail">
                        <b-list-group-item class="flex-column align-items-start">
                          <div class="d-flex w-100 justify-content-between pcsprint">
                            <h5 class="mb-1" style="font-weight: normal;">{{teamMember.role}}</h5>
                          </div>
                          <p align="left" class="pcsprint">
                            Name: {{teamMember.lastname}} {{teamMember.firstname}}
                            <br> Email: {{teamMember.mail}}
                          </p>
                        </b-list-group-item>
                      </b-list-group>

                    </div>
                  </b-col>
                </b-row>
              </b-container>

              <br>
              <template>
                <div v-if="(projectOverview.project.isDone === false )">
                  <b-form v-if="isProductOwner()===true" inline @submit="addMembers">
                    Add User&nbsp;
                    <b-form-group id="emailForm" label-for="email" :invalid-feedback="validEmail===false ? 'Invalid User' : ''">
                      <b-form-input class="mb-2 mr-sm-2 mb-sm-0" id="emailInput" type="email" v-model="newMember.mail" @change="checkEmail" :state="validEmail" placeholder="email" required>
                      </b-form-input>
                    </b-form-group>
                    as&nbsp;
                    <b-form-select v-model="newMember.role" :options="opts" class="mb-2 mr-sm-2 mb-sm-0" id="inlineFormCustomSelectPref">
                      <template slot="first">
                        <!-- this slot appears above the options from 'options' prop -->
                        <option :value="null" disabled>- Select role -</option>
                      </template>
                    </b-form-select>
                    <br>
                    <b-button class="pcsprint" type="submit" letiant="primary" :disabled="validEmail===false">Invite</b-button>
                  </b-form>
                </div>
              </template>
            </b-jumbotron>
          </b-container>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>


<script>
  import axios from 'axios'
  import Sidebar from "./Sidebar.vue"
  import Navbar from "./Navbar.vue"

  export default {
    components: {
      navbar: Navbar,
      sidebar: Sidebar,
    },
    computed: {
      dateState () {
        let mindate = new Date();
        let dd = ("0" + mindate.getDate()).slice(-2);
        let mm = ("0" + (mindate.getMonth()+1)).slice(-2);
        let yyyy = mindate.getFullYear();
        mindate=yyyy+"-"+mm+"-"+dd;
        if (this.form.deadlineDate==='') return null;
        return this.form.deadlineDate>mindate
      }

    },

    data() {
      return {
        sel: null,
        max: 100,
        opts: [
          { value: '1', text: 'Scrum Master' },
          { value: '2', text: 'Developer' }
        ],
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
          role: null
        },

        invitation: {
          idNotification: null,
          Project_id: 0,
          projectTitle: null,
          role: null,
          FromUsername: null,
          ToUserEmail: '',
          type: ''
        },

        form: {
          idProject: 0,
          newTitle: '',
          deadlineDate: '',
          isDone: false
        },

        validEmail: null,
        sdeadline: null,
        diffDays: 0,
        issues: 0,
        Team: []
      }
    },

    methods: {
      getSprintInfo () {
        const self = this;
        axios.get (this.$url+'users/'+ localStorage.getItem('userId') +'/projects/'+ this.$route.params.id,{
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
              let totalTasks = self.projectOverview.todo + self.projectOverview.doing + self.projectOverview.done;
              self.donePercentage = self.projectOverview.done / totalTasks * 100;
              self.issues = self.projectOverview.todoIssues + self.projectOverview.doingIssues + self.projectOverview.doneIssues;
              self.calcDeadline ();
              console.log("Got the results");
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      },

      updateProject () {
        const self = this;
        let data = { idProject: this.projectOverview.project.idProject, title: this.form.newTitle, isDone: this.form.isDone, deadlineDate: this.form.deadlineDate }
        axios.put(this.$url+'users/'+ localStorage.getItem('userId') +'/projects/'+ this.$route.params.id, data, {
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
              self.projectOverview.project = response.data.results;
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      },

      calcDeadline () {
        const self = this;
        let today = new Date();
        let dd = today.getDate();
        let mm = today.getMonth()+1;
        let yyyy = today.getFullYear();
        //console.log("-------");
        //console.log("current date");
        //console.log(today);
        //console.log("-------");
        //console.log("sprint date");
        //console.log(self.projectOverview.currentSprintExpDate);
        let d = new Date(self.projectOverview.currentSprintExpDate);
        //console.log("-------");
        //console.log(d);
        //let jsDate=new Date(Date.parse(self.projectOverview.currentSprintExpDate.replace ('-', '/', 'g')));
        let oneDay = 24*60*60*1000;
        self.diffDays=Math.floor((d.getTime() - today.getTime())/(oneDay));
        self.sdeadline=self.projectOverview.currentSprintExpDate;
        // Check the days interval
        //self.diffDays += 2
      },

      getMembers () {
        const self=this;
        axios.get(this.$url+ 'users/'+ localStorage.getItem('userId') +'/projects/' + this.$route.params.id + '/members', {
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
        if (self.newMember.role === '1'){
          self.invitation.role = "Scrum Master";
        }
        else if (self.newMember.role === '2'){
          self.invitation.role = "Developer";
        }
        self.invitation.Project_id = this.$route.params.id;
        self.invitation.projectTitle = self.projectOverview.project.title;
        self.invitation.FromUsername = localStorage.getItem('username');
        self.invitation.ToUserEmail = self.newMember.mail;
        self.invitation.type = "Accept/Decline";
        axios.post(this.$url+ 'users/'+ localStorage.getItem('userId') +'/notifications', self.invitation, {
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
              console.log("Invitation send");
              self.newMember.mail='';
              self.validEmail=null;
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      },
      isProductOwner(){
        const self = this;
        let loggedUserID = localStorage.getItem('userId');
        console.log("IS PRODUCT OWNER?");
        console.log(loggedUserID);
        let teamMember;
        let i=0;
        for(teamMember in self.Team){
          if (self.Team[i].idUser == loggedUserID){
            console.log("Role in project:");
            if(self.Team[i].role === "Product Owner"){
              return true;
            }
            else {
              return false;
            }
          }
          i++;
        }
        return false;
      }
      ,
      checkEmail () {
        if (this.newMember.mail==='') {
          this.validEmail=null;
          return;
        }
        const self = this;
        // Check if the user is among the members already in project
        let i;
        for (i = 0; i < self.Team.length; i++){
          if (self.Team[i].mail === self.newMember.mail){
            this.validEmail=false;
            return;
          }
        }
        // Now check the users in database
        axios.post(this.$url + 'exists', {
          mail: self.newMember.mail,
        })
          .then(function (response) {
            self.validEmail=(response.data.exists===1);
          })
          .catch(function (error) {
            console.log(error);
          })
      },
    }
    ,
    mounted () {
      this.getSprintInfo();
      this.getMembers();
    },
  }

</script>


<style scoped>
  @import url('https://fonts.googleapis.com/css?family=Merienda');
  @import url('https://fonts.googleapis.com/css?family=VT323');
  @import url('https://fonts.googleapis.com/css?family=Quicksand');

  .ptitle{
    font-family: Merienda;
  }

  .pdeadline{
    font-family: VT323;font-size:24px; margin-top: 1%;
  }

  .pcsprint{
    font-family: Quicksand;
  }

  .Navigation {
    position: absolute;
    top: 0;
    left: 0;
  }
  .line {
    width: 99%;
    font-size: 1px;
    line-height: 2px;
    background-color: #e6edf2;
    margin-top: 9px;
    margin-bottom: 9px;
    margin-left: 1.2%;
    margin-right: 1.2%;
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

