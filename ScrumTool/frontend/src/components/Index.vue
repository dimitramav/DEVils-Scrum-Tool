<template>
  <div>
    <template v-if="isLoggedIn===false">
      <b-container class="Title" style="background-color:#2d2d2d;" fluid >
        <b-row>
          <p></p>
        </b-row>
        <b-row>
          <b-col>
            <b-img style ="width: 30%" src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png"></b-img>
          </b-col>
          <b-col cols="8">
            <h1 align="center" style="font-family:cursive;margin: 0;padding: 0;color: white;"> Scrum Tool </h1>
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
          <img src="../assets/ScrumWordCloud.jpg" class="wordcloud"">
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
        <b-navbar toggleable="md" class="navbar navbar-expand-lg bg-dark navbar-dark navbar-static-top" variant="info" fluid>
          <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
          <b-navbar variant="faded" type="light">
            <b-navbar-brand href="#">
              <img src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png" width="60" height="60" alt="BV">
            </b-navbar-brand>
          </b-navbar>
          <b-collapse is-nav id="nav_collapse">
            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
              <b-nav-item-dropdown right>
                <template slot="button-content">
                  <em>
                    <img src="https://support.rocketchatlauncher.com/wp-content/uploads/2017/03/bell.png" style="width:27px;">
                  </em>
                </template>
                <b-dropdown-item href="#">#Notification 1</b-dropdown-item>
                <b-dropdown-item href="#">#Notification 2</b-dropdown-item>
                <b-dropdown-item href="#">#Notification 3</b-dropdown-item>
                <b-dropdown-item href="#">#Notification 4</b-dropdown-item>
              </b-nav-item-dropdown>
              <b-nav-item-dropdown right>
                <template slot="button-content">
                  <em>
                    <img src="https://www.mindvoize.com/images/userImage.png" style="width:30px;">
                  </em>
                </template>
                <b-dropdown-item href="#">Profile</b-dropdown-item>
                <b-dropdown-item-button v-on:click="logout">Sign out</b-dropdown-item-button>
              </b-nav-item-dropdown>
            </b-navbar-nav>
          </b-collapse>
        </b-navbar>
        <b-row>
          <p></p>
        </b-row>
        <b-row style="padding-top:10px;">
          <b-col>
            <h2> Current Projects</h2>
          </b-col>
          <b-col>
            <br>
          </b-col>
          <b-col>
            <br>
          </b-col>
          <b-col>
            <div>
              <b-dropdown id="ddown1" size="lg" variant ="info" text="Create a New Project" class="m-md-2">
                <b-form inline style="margin: 10px;">
                  <h4>Title:</h4>
                  <label class="sr-only" for="inlineFormInputName2"></label>
                  <b-input class="" id="inlineFormInputName2" placeholder="New Project's Name" />
                  <b-button variant="success" style="margin-top: 10px; width: 100%;">Add to Projects</b-button>
                </b-form>
              </b-dropdown>
            </div>
          </b-col>
        </b-row>
        <b-row>
          <div class="line">.</div>
        </b-row>
        <b-row>
          <br>
        </b-row>

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



        <b-row>
          <br>
        </b-row>

        <b-row>
          <b-col>
            <h2>Done Projects</h2>
          </b-col>
          <b-col>
            <br>
          </b-col>
          <b-col>
            <br>
          </b-col>
          <b-col>
            <br>
          </b-col>
        </b-row>

        <b-row>
          <br>
        </b-row>
        <b-row>
          <div class="line">.</div>
        </b-row>

        <b-card-group v-for="done_project in doneProjects" :key="done_project.idProject" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
          <b-card :title="done_project.title" img-top tag="article" style="max-width: 15rem;" class="mb-2">
            <p class="card-text">
              Deadline: {{done_project.deadlineDate}}
            </p>
            <b-button href="#" variant="default">Proceed</b-button>
          </b-card>
        </b-card-group>
      </b-container>

    </template>
  </div>
</template>


<script>import axios from 'axios'
import json from '../assets/team.json'
export default {
  data() {
    return {
      isLoggedIn: false,
      currentProjects: [],
      doneProjects:[],
      teamData: json.team,
    }
  },
  methods: {
    gotoSignIn() {
      this.$router.push({path: '/signin'});
    },
    gotoSignUp() {
      this.$router.push({path: '/signup'});
    }
    ,
    getProjects () {
      //evt.preventDefault();
      const self = this;
      axios.get('http://localhost:8765/app/api/users/'+localStorage.getItem('userId') + '/projects?isDone=false&limit=10&offset=0', {
        headers: { "auth": localStorage.getItem('auth_token') }
      })
        .then(function (response) {
          console.log(response);
          if (response.data.error) {
            if (response.data.error = "Unauthorized user") {
              self.isLoggedIn=false;
            }
          }
          else self.isLoggedIn=true;
          if (response.data.results) {
            self.currentProjects = response.data.results;
          }
        })
        .catch(function (error) {
          console.log(error);
        })

      axios.get('http://localhost:8765/app/api/users/'+localStorage.getItem('userId') + '/projects?isDone=true&limit=10&offset=0', {
        headers: { "auth": localStorage.getItem('auth_token') }
      })
        .then(function (response) {
          console.log(response);
          if (response.data.error) {
            if (response.data.error = "Unauthorized user") {
              self.isLoggedIn=false;
            }
          }
          else self.isLoggedIn=true;
          if (response.data.results) {
            self.doneProjects = response.data.results;
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    logout() {
      localStorage.setItem('userId','null');
      localStorage.setItem('auth_token', 'null');
      this.isLoggedIn=false;
    },
  },
  mounted (){
    if (localStorage.getItem('auth_token')==='null' || localStorage.getItem('userId')==='null') return;
    this.getProjects();
  },
}
</script>

<style scoped>
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
    top:498%;
    left:0;
  }

  .stamp {
    border-style: double;
    border-color: grey;
    border-width: 4px;
  }

  .About{
    position: absolute;
    top:900%;
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
    margin-top: -6px;
    margin-bottom: 10px;
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

