<template>
  <b-container fluid>
    <b-navbar fixed="top" toggleable="md" class="navbar navbar-expand-lg bg-dark navbar-dark navbar-static-top" variant="info" fluid>
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
      <b-navbar variant="faded" type="light">
        <b-navbar-brand>
          <router-link :to="{name: 'Index'}">
            <img src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png" width="30" height="30" alt="BV">
          </router-link>
        </b-navbar-brand>
        <span class="text-enhancement">
          <router-link :to="{name: 'Index'}" style="text-decoration: none; color:white;">
            ScrumTool
          </router-link>
        </span>
      </b-navbar>
      <b-collapse is-nav id="nav_collapse" v-if="logout_prop===false">
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <!-- Project notifications -->
          <b-nav-item-dropdown class="pcsprint" right>
            <template slot="button-content">
                  <em v-if="Notifications.length === 0">
                    <img src="https://support.rocketchatlauncher.com/wp-content/uploads/2017/03/bell.png" style="width:20px;">
                  </em>
                  <em v-else>
                    <img src="http://www.iconsplace.com/icons/preview/red/appointment-reminders-256.png" style="width:20px;">
                  </em>
            </template>
            <div v-if="Notifications.length === 0">
              <b-dropdown-item disabled>
                You don't have any notifications...
              </b-dropdown-item>
            </div>
            <div v-else v-for="notification in Notifications">
                <b-dropdown-item disabled>
                  <div v-if="notification.type === 'Accept/Decline'">
                    User <a v-on:click="goToProfile(notification.FromUsername);" href="#" >{{notification.FromUsername}}</a> invited you as {{notification.role}} in {{notification.projectTitle}}.
                    <br>
                    <b-button variant="primary" v-on:click="watchedNotification('accept', notification)">Accept</b-button>
                    <b-button variant="secondary" v-on:click="watchedNotification('decline', notification)">Decline</b-button>
                  </div>
                  <div v-else>
                    {{notification.text}}
                    <br>
                    <b-button variant="primary" v-on:click="watchedNotification('decline', notification)">OK</b-button>
                  </div>
                </b-dropdown-item>
            </div>
          </b-nav-item-dropdown>
          <!-- Setting notifications -->
          <b-nav-item-dropdown class="pcsprint" right>
            <template slot="button-content">
              <em>
                <img src="https://www.mindvoize.com/images/userImage.png" style="width:23px; position: relative; bottom: 0.5px">
              </em>
            </template>
            <b-dropdown-item-button v-on:click="profile">Profile</b-dropdown-item-button>
            <!--<b-dropdown-item-button v-on:click="editprofile">Edit Profile</b-dropdown-item-button>-->
            <b-dropdown-item-button v-on:click="logout">Sign out</b-dropdown-item-button>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
      <b-navbar-nav class="ml-auto" v-if="logout_prop===true">
        <b-row>
          <b-col></b-col>
          <b-col cols="8"></b-col>
          <b-col>
            <button type="button" class="btn btn-primary" v-on:click="gotoSignIn" style="margin-right:5px">Log In</button>
            <button type="button" class="btn btn-primary" v-on:click="gotoSignUp">Sign Up</button>
          </b-col>
        </b-row>
      </b-navbar-nav>
    </b-navbar>
    <br><br><br>
    <b-navbar v-if="dashboard" toggleable="md" class="navbar navbar-expand-lg navbar-static-top subnav" variant="primary" type="dark" fluid>
      <b-navbar-toggle target="subnav_collapse"></b-navbar-toggle>
      <b-collapse is-nav id="subnav_collapse">
        <b-navbar-nav style="padding-left: 15px;">
          <b-nav-item :to="{name: 'ProjectPageOverview', params: {id:$route.params.id}}">
            <p class="text-enhancement2"> Overview </p>
          </b-nav-item>
          <b-col></b-col>
          <b-nav-item :to="{name: 'Backlog', params: {id:$route.params.id}}">
            <p class="text-enhancement2"> Backlog </p>
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </b-container>
</template>

<script>
  import axios from 'axios'
  import json from '../assets/team.json'
  export default {
    data() {
      return {
        Notifications: [],
      }
    },
    name: "Navbar",
    methods: {
      goToProfile(name) {
        this.$router.push({
          name: 'Profile',
          params: {
            id: name
          }
        });
      },
      logout() {
        localStorage.setItem('userId', 'null');
        localStorage.setItem('username', 'null');
        localStorage.setItem('auth_token', 'null');
        this.$router.push({
          path: '/logout'
        });
      },
      profile() {
        this.$router.push({
          name: 'Profile',
          params: {
            id: localStorage.getItem('username')
          }
        });
      },
      /*editprofile() {
        this.$router.push({path: '/editprofile'});
      },*/
      gotoSignIn() {
        this.$router.push({path: '/signin'});
      },
      gotoIndex() {
        this.$router.push({path: '/'});
      },
      gotoSignUp() {
        this.$router.push({path: '/signup'});
      },
      getNotifications () {
        const self = this;
        axios.get(this.$url+ 'users/'+ localStorage.getItem('userId') +'/notifications', {
          headers: { "auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json' }
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") {
                console.log("Unauthorized user");
              }
              else if (response.data.error === "Unauthorized notifications") {
                console.log("Unauthorized notifications");
              }
              else if (response.data.error === "null") {
                console.log("Null token");
              }
            }
            if (response.data.results) {
              self.Notifications = response.data.results;
              console.log("Got the notifications");
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      },
      watchedNotification (functionality, notificationItem) {
        const self = this;
        if (functionality === 'accept') {
          axios.put(this.$url+ 'users/'+ localStorage.getItem('userId') +'/notifications', notificationItem.idNotification,
            { headers: { "auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json' }
          })
            .then(function (response) {
              if (response.data.error) {
                   console.log(response.data.error);
              }
              if (response.data.results) {
                if (response.data.results === 1){
                  var index = -1;
                  for (var x in self.Notifications){
                    if (self.Notifications[x].idNotification === notificationItem.idNotification){
                      index = x;
                      break;
                    }
                  }
                  if (index >= 0) {
                    self.Notifications.splice(index,1);
                    console.log("Notification removed");
                  }
                }
                else { console.log("Notification did not delete"); }
              }
            })
            .catch(function (error) {
              console.log(error);
            })
          let data = { mail: notificationItem.ToUserEmail, role: notificationItem.role }
          axios.post(this.$url +'users/'+ localStorage.getItem('userId') +'/projects/'+ notificationItem.Project_id + '/members', data, { headers: { "auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json' }
          })
            .then(function (response) {
              if (response.data.error) {
                console.log(response.data.error);
              }
              if (response.data.results) {
                console.log("User inserted as member in the project");
                //self.$router.push({path: '/'});
                location.reload(true);
              }
            })
            .catch(function (error) {
              console.log(error);
            })
          console.log(functionality);
        }
        else if (functionality === 'decline') {
          axios.put(this.$url+ 'users/'+ localStorage.getItem('userId') +'/notifications', notificationItem.idNotification,
            { headers: { "auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json' }
          })
            .then(function (response) {
              if (response.data.error) {
                   console.log(response.data.error);
              }
              if (response.data.results) {
                if (response.data.results === 1){
                  var index = -1;
                  for (var x in self.Notifications){
                    if (self.Notifications[x].idNotification === notificationItem.idNotification){
                      index = x;
                      break;
                    }
                  }
                  if (index >= 0) {
                    self.Notifications.splice(index,1);
                    console.log("Notification removed");
                  }
                }
                else { console.log("Notification did not delete"); }
              }
            })
            .catch(function (error) {
              console.log(error);
            })
        }
      },
    },
    mounted () {
      this.getNotifications();
    },
    props: {
      dashboard: Boolean,
      logout_prop : Boolean,
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
    background-color: lavender;
    margin-top: -6px;
    margin-bottom: 10px;
  }

  .container-fluid {
    padding-right: 0;
    padding-left: 0;
  }

  .text-enhancement {
    font-family: 'Merienda', cursive;
    padding-left: 15px;
    font-size: 24px;
    color: white;
  }

  .text-enhancement2 {
    font-family: 'Quicksand', cursive;
    font-size: 17px;
    font-weight: normal;
    color: white;
    margin-top: 5px;
    margin-bottom: 5px;
  }

  .subnav {
    background-color: #494965!important;
    padding-top: 4px;
    padding-bottom: 2px;
    line-height: 2px;
  }

  .navbar {
    min-height: 20px;
    max-height: 500px;
  }

  .navbar-toggle {
    /* (80px - button height 34px) / 2 = 23px */
    margin-top: 23px;
    padding: 9px 10px !important;
  }
</style>
