<template>
  <div v-if = "true">
    <b-container class="Navigation" fluid>

      <navbar></navbar>
      <!--<sidebar></sidebar>-->
      <br>
      <b-row>
        <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
      </b-row>
      <b-row style="padding-top:10px;">
        <b-col class="text-left">
          <h2>Insurance App</h2>
          <p> {{$route.params.id}} </p>
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
                  <h2>Current sprint #{{sprintid}}</h2>
                </b-row>
                <br>
                <b-row>
                  <b-col>
                    <b-card title="TODO">
                      <p class="card-text">{{todo}}</p>
                      <p class="card-text" style="color: red">Issues: {{todoIssues}}</p>
                    </b-card>
                  </b-col>
                  <b-col>
                    <b-card title="DOING">
                      <p class="card-text">{{doing}}</p>
                      <p class="card-text" style="color: red">Issues: {{doingIssues}}</p>
                    </b-card>
                  </b-col>
                  <b-col>
                    <b-card title="DONE">
                      <p class="card-text">{{done}}</p>
                      <p class="card-text" style="color: red">Issues: {{doneIssues}}</p>
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
                <b-progress :value="value" show-progress class="mb-3"></b-progress>
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
                  <b-form inline>
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
  import Sidebar from "./Sidebar.vue"
  import Navbar from "./Navbar.vue"

  export default {
    components: {
      navbar: Navbar,
      sidebar: Sidebar,
    },
    data() {
      return {
        value: 75,
        selected: [], // Must be an array reference!

        items: [{
          text: 'Home',
          href: '#'
        }, {
          text: 'Insurance App',
          href: '#'
        }, {
          text: 'Overview',
          active: true
        }],

        sprintid: 1997,
        sprintexpdate: '',
        diffDays: -1,
        Team: [{role: "Very Senior Dev", lastname: "Raptis", firstname:"Eyaggelos", mail: "sokolatitsa1949@math.gr"}, {role: "Junior Dev", lastname: "Takhs", firstname: "Mathiopoylos", mail: "kaikaikaikaikai@sys.ch"}],
        todo: 6,
        doing: 10,
        done: 2,
        todoIssues: 99,
        doingIssues: 1,
        doneIssues: 0,
      }
    },

    methods: {
      getSprintInfo () {
        axios.get ('app/api/projects/'+this.$route.params.id+'/sprints')
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Wrong Project") {
              console.log("Wrong Project");
            }
          }
          if (response.data.results) {
            this.sprintid = response.data.idSprint;
            this.sprintexpdate = this.currentProjects.deadline;
            console.log("Got the results");
          }
        })
        .catch(function (error) {
          console.log(error);
        })
      },

      calcDeadline () {
        var today=new Date ();
        var dd = today.getDate();
        var mm = today.getMonth()+1;
        var yyyy = today.getFullYear();

        var l=new Date (2018, 5, 4); //anti gia l mpainei to this.springexpdate
        var oneDay=24*60*60*1000;
        this.diffDays=Math.floor(Math.abs((today.getTime() - l.getTime())/(oneDay)));
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
      this.calcDeadline ();
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

