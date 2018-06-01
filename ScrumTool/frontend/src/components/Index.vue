<template>
  <div>
    <template v-if="notLoggedIn()===true">
      <b-container class="Title" style="background-color:#2d2d2d;" fluid >
        <b-row>
          <p></p>
        </b-row>
        <b-row>
          <b-col>
            <b-img style ="width: 30%" v-on:click="gotoIndex" src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png"></b-img>
          </b-col>
          <b-col cols="8">
            <h1 align="center" style="font-family:cursive,Courier New;margin: 0;padding: 0;color: white;"> Scrum Tool </h1>
          </b-col>
          <b-col>
            <button type="button" class="btn btn-primary" v-on:click="gotoSignIn">Log In</button>
            <button type="button" class="btn btn-primary" v-on:click="gotoSignUp">Sign Up</button>
          </b-col>
        </b-row>
        <b-row align-h="center">
          <p style="font-family:Courier New; color:white; font-style: italic;font-weight:bold" align="center">Be agile together! </p>
        </b-row>
        <b-row class="justify-content-md-center" style="margin: 0 auto;float: none;margin-bottom: 10px;">
            <b-col col lg="1">
              <a class="active" href="#home" style="font-family:Courier New; color:white; font-style: italic;font-weight:bold" align="center">home</a>
            </b-col>
            <b-col col lg="1">
              <a href="#features" style="font-family:Courier New; color:white; font-style: italic;font-weight:bold" align="center">features</a>
            </b-col>
            <b-col col lg="1">
              <a href="#about" style="font-family:Courier New; color:white; font-style: italic;font-weight:bold" align="center">about</a>
            </b-col>
        </b-row>

        <div id="home" class="Home">
          <img src="../assets/ScrumWordCloud.jpg" class="wordcloud">
          <b-button variant="primary" size="lg" type="button" v-on:click="gotoSignUp">Join Free Now</b-button>
          <p><br/></p>
        </div>

        <b-container class="Features" id="features" style="background-color: #ffffff;" fluid>
          <b-row align-h="center">
            <h2 style="font-family:Courier New;"> Features </h2>
          </b-row>
          <b-row>
            <p></p>
          </b-row>
          <b-row>
            <b-col>
              <b-list-group style="text-align:center; font-weight: normal;font-family:cursive;color:#2d2d2d;">
                <b-list-group-item>Implement scrum methodology</b-list-group-item>
                <b-list-group-item>Free of charge forever</b-list-group-item>
                <b-list-group-item>Variety of tools to choose by</b-list-group-item>
                <b-list-group-item>Create , participate , communicate in projects</b-list-group-item>
                <b-list-group-item>Update Product and Sprint Backlogs</b-list-group-item>
                <b-list-group-item>Manage your sprints and deadlines</b-list-group-item>
                <b-list-group-item>Review old and current sprints </b-list-group-item>
                <b-list-group-item>Edit profiles and view others</b-list-group-item>
                <b-list-group-item>Invite members , assign roless</b-list-group-item>
                <b-list-group-item>Create Epics , Tasks , Stories</b-list-group-item>
              </b-list-group>
            </b-col>
            <b-col align-self="center">
              <iframe width="560" height="315" src="https://www.youtube.com/embed/9TycLR0TqFA" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
            </b-col>
          </b-row>
        </b-container>

        <b-container class="About Us" id="about" style="background-color: #fbfbff;" fluid>
          <b-row align-h="center">
            <h2 style="font-family:Courier New;"> About Us </h2>
          </b-row>
          <b-row>
            <p></p>
          </b-row>
          <b-row>
            <b-card-group v-for="teamMember in teamData" :key="teamMember.member" deck style="margin: 0 auto;float: none; margin-bottom: 10px;">
              <b-card :title="teamMember.name"
                      :img-src="teamMember.img"
                      img-alt="Img"
                      style="max-width: 9rem"
                      class="mb-2">
                <p class="card-text" style="font-style: italic;">
                  {{teamMember.dev}}, {{teamMember.lang}}
                  <br><br> <i style="color: rgba(47,75,72,0.85)"> Phrase:<br>{{teamMember.phrase}}</i></p>
              </b-card>
            </b-card-group>
          </b-row>
          <b-row>
            <p></p>
          </b-row>
          <b-row align-h="center">
            <p class="description">
              - We are a team of junior developers , developing our first project , implementing a web cooperative platform for software management. This platform helps teams to implement software projects based on agile methodology (SCRUM) via backlogs, issues, epics and sprints.
            </p>
          </b-row>

          <b-container class="bottom" style="background-color:#2d2d2d;" fluid>
            <b-row class="justify-content-md-center" style="margin: 0 auto;float: none;margin-bottom: 10px;">
                <b-col col lg="1">
                  <a class="active" href="#home" style="font-family:Courier New; color:white; font-style: italic;font-weight:bold" align="center">home</a>
                </b-col>
                <b-col col lg="1">
                  <a href="#features" style="font-family:Courier New; color:white; font-style: italic;font-weight:bold" align="center">features</a>
                </b-col>
                <b-col col lg="1">
                  <a href="#about" style="font-family:Courier New; color:white; font-style: italic;font-weight:bold" align="center">about</a>
                </b-col>
            </b-row>
            <b-row class="justify-content-md-center" style="margin: 0 auto;float: none;margin-bottom: 10px;">
                <b-col col lg="2">
                  <a class="active" href="#home" style="font-family:Courier New; color:white; font-style: italic;font-weight:bold;" align="center"> terms of use</a>
                </b-col>
                <b-col col lg="2">
                  <a href="#features" style="font-family:Courier New; color:white; font-style: italic;font-weight:bold" align="center">privacy policy</a>
                </b-col>
            </b-row>
          </b-container>
        </b-container>
      </b-container>
    </template>

<!--USER HOMEPAGE    -->
    <template v-else>
      <b-container class="Navigation" fluid>

        <navbar></navbar>
        <b-container>
        <b-row>
          <b-col class="text-right">
            <div>
              <b-dropdown id="ddown1" size="lg" variant ="info" text="Create a New Project" class="m-md-2">
                <b-form inline style="margin: 10px;" @submit="newProject">
                  <h4>Title:</h4>
                  <label class="sr-only" for="newProjectName"></label>
                  <b-form-input id="newProjectName" placeholder="New Project's Name" v-model="form.newTitle" required/>
                  <h5>Deadline:</h5>
                  <label class="sr-only" for="newProjectDate"></label>
                  <b-form-input id="newProjectDate" type="date" v-model="form.deadlineDate" required/>
                  <b-button variant="success" type="submit" style="margin-top: 10px; width: 100%;">Add to Projects</b-button>
                </b-form>
              </b-dropdown>
            </div>
          </b-col>
        </b-row>
        <b-row style="padding-top:10px;">
          <b-col class="text-left">
            <h2 class="text-enhancement"> Current Projects</h2>
          </b-col>
        </b-row>
        <b-row>
          <div class="line">.</div>
        </b-row>
        <br>

        <b-row>
          <b-card-group v-for="cur_project in currentProjects" :key="cur_project.idProject" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
            <b-card :title="cur_project.title" img-top tag="article" style="max-width: 15rem;" class="mb-2">
              <p class="card-text">
                Deadline: {{cur_project.deadlineDate}}
              </p>
              <b-button variant="primary">Proceed</b-button>
            </b-card>
          </b-card-group>
        </b-row>
        <br>
        <div v-if="doneLength !== 0">
        <b-row style="padding-top:10px;">
          <b-col class="text-left">
            <h2 class="text-enhancement">Done Projects</h2>
          </b-col>
        </b-row>
        <b-row>
          <div class="line">.</div>
        </b-row>
        <br>
        <b-row>
        <b-card-group v-for="done_project in doneProjects" :key="done_project.idProject" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
          <b-card :title="done_project.title" img-top tag="article" style="max-width: 15rem;" class="mb-2">
            <p class="card-text">
              Deadline: {{done_project.deadlineDate}}
            </p>
            <b-button variant="primary">Proceed</b-button>
          </b-card>
        </b-card-group>
        </b-row>
        <br>
        </div>
      </b-container>
      </b-container>
    </template>
  </div>

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
      doneProjects:[],
      teamData: json.team,
      numProjects: 0,
      doneLength: 0
    }
  },
  methods: {
    gotoSignIn() {
      this.$router.push({path: '/signin'});
    },
    gotoIndex() {
      this.$router.push({path: '/'});
    },
    gotoSignUp() {
      this.$router.push({path: '/signup'});
    },
    notLoggedIn() {
      return (localStorage.getItem('auth_token')==='null' || localStorage.getItem('userId')==='null' || this.logOut);
    },
    getProjects () {
      //evt.preventDefault();
      const self = this;
      axios.get('http://localhost:8765/app/api/users/'+localStorage.getItem('userId') + '/projects?isDone=false&limit=12&offset=0', {
        headers: { "auth": localStorage.getItem('auth_token') }
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          if (response.data.results) {
            self.currentProjects = response.data.results;
            self.numProjects = self.currentProjects.length;
            console.log(self.numProjects);
          }
        })
        .catch(function (error) {
          console.log(error);
        })

      axios.get('http://localhost:8765/app/api/users/'+localStorage.getItem('userId') + '/projects?isDone=true&limit=10&offset=0', {
        headers: { "auth": localStorage.getItem('auth_token') }
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          if (response.data.results) {
            self.doneProjects = response.data.results;
            self.doneLength = self.doneProjects.length;
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    newProject (evt) {
      evt.preventDefault();
      const self = this;
      let config = {
        headers: { "auth": localStorage.getItem('auth_token'), "Content-Type":'application/json' }
      }
      let data = {
        title: this.form.newTitle, isDone: this.form.isDone, deadlineDate: this.form.deadlineDate
      }
      axios.post('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects', data, config)
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
    logout() {
      localStorage.setItem('userId','null');
      localStorage.setItem('auth_token', 'null');
      this.logOut=true;
    },
  },
  mounted (){
    if (localStorage.getItem('auth_token')==='null' || localStorage.getItem('userId')==='null') return;
    this.getProjects();
  },
}
</script>

<style scoped>

  @import url('https://fonts.googleapis.com/css?family=Merienda');

  .Title{
    position: absolute;
    top: 0;
    left: 0;
  }

  .Home{
    position: absolute;
    background-color: #fafaff;
    width: 100vw;
    left: 0;
  }
  .wordcloud{
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 51%;
  }

  .Features{
    position: absolute;
    top:540%;
    left:0;
  }

  .stamp {
    border-style: double;
    border-color: grey;
    border-width: 4px;
  }

  .About{
    position: absolute;
    top:960%;
    left:4px;
  }

  .devs{
    height: 50%;
    width: 50%;
  }

  .bottom{
    position: absolute;
    left:0%;
    width: 100%;
  }

  .description{
    font-size: 14px;
    width: 500px;
    text-align: justify;
    word-wrap: break-word;
    padding: 8px;
    text-indent: 50px;
    text-align: justify;
    letter-spacing: 3px;
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
    background-color: lavender;
    margin-top: 10px;
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

  .text-enhancement {
    font-family: 'Merienda', cursive;
  }

</style>

