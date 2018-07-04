<template>
  <b-container fluid>
    <b-navbar toggleable="md" class="navbar navbar-expand-lg bg-dark navbar-dark navbar-static-top" variant="info" fluid>
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
      <b-navbar variant="faded" type="light">
        <b-navbar-brand href="/#/">
          <img src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png" width="30" height="30" alt="BV">
        </b-navbar-brand>
        <span class="text-enhancement"><a href="/#/" style="text-decoration: none; color:white;">Scrum Tool</a></span>
      </b-navbar>
      <b-collapse is-nav id="nav_collapse">
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <!-- Project notifications -->
          <b-nav-item-dropdown right>
            <template slot="button-content">
              <em>
                <img src="https://support.rocketchatlauncher.com/wp-content/uploads/2017/03/bell.png" style="width:20px;">
              </em>
            </template>
            <b-dropdown-item disabled>
              Mark Zuckerberg invited you as Scrum Master in Facebook .
              <br>
              <b-button variant="primary" type="submit">Accept</b-button>
              <b-button variant="secondary" type="submit">Decline</b-button>
            </b-dropdown-item>
            <b-dropdown-item disabled>
              Bill Gates invited you as Scrum Master in Microsoft .
              <br>
              <b-button variant="primary" type="submit">Accept</b-button>
              <b-button variant="secondary" type="submit">Decline</b-button>
            </b-dropdown-item>

          </b-nav-item-dropdown>
          <!-- Setting notifications -->
          <b-nav-item-dropdown right>
            <template slot="button-content">
              <em>
                <img src="https://www.mindvoize.com/images/userImage.png" style="width:23px; position: relative; bottom: 0.5px">
              </em>
            </template>
            <b-dropdown-item-button v-on:click="profile">Profile</b-dropdown-item-button>
            <b-dropdown-item-button v-on:click="editprofile">Edit Profile</b-dropdown-item-button>
            <b-dropdown-item-button v-on:click="logout">Sign out</b-dropdown-item-button>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

    <b-navbar v-if="dashboard" toggleable="md" class="navbar navbar-expand-lg navbar-static-top subnav" variant="primary" type="dark" fluid>
      <b-navbar-toggle target="subnav_collapse"></b-navbar-toggle>
      <b-collapse is-nav id="subnav_collapse">
        <b-navbar-nav style="padding-left: 15px;">
          <b-nav-item :to="{name: 'ProjectPageOverview', params: {id:$route.params.id}}">
            <p class="text-enhancement2"> Overview </p>
          </b-nav-item>
          <b-nav-item :to="{name: 'Backlog', params: {id:$route.params.id}}">
            <p class="text-enhancement2"> Backlog </p>
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>s

  </b-container>
</template>

<script>

  export default {
    data() {
      return {
        Notifications: [],
      }
    },
    name: "Navbar",
    methods: {
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
      editprofile() {
        this.$router.push({path: '/editprofile'});
      },
      getNotifications () {
        const self = this;
        self.Notifications = [{"idNotification": 1, "Project_id": 5, "projectTitle": "Steam", "role": "Developer", "FromUsername": "vaggosGar", "ToUserEmail": "kostas@gmail.com", "type": "0"},
          {"idNotification": 2, "Project_id": 6, "projectTitle": "Origin", "role": "Scrum Master", "FromUsername": "kostaskotronis", "ToUserEmail": "orestis@gmail.com", "type": "0"}];
      },
    },
    mounted () {
      console.log("aaaaaaaaaaaaaaaa");
      this.getNotifications();
    },
    props: {
      dashboard: Boolean,
    },
  }
</script>

<style scoped>
  @import url('https://fonts.googleapis.com/css?family=Merienda');
  @import url('https://fonts.googleapis.com/css?family=Shrikhand');
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
    font-family: 'Shrikhand', cursive;
    font-size: 15px;
    color: white;
    margin-top: 5px;
    margin-bottom: 5px;
  }

  .subnav {
    background-color: #486170!important;
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
