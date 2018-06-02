<template>
  <b-navbar  toggleable="md" class="navbar navbar-expand-lg bg-dark navbar-dark navbar-static-top" variant="info" fluid>
    <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
    <b-navbar variant="faded" type="light">
      <b-navbar-brand href="/#/">
        <img src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png" width="60" height="60" alt="BV">
      </b-navbar-brand>
      <span class="text-enhancement"><a href="/#/" style="text-decoration: none; color:white;">Scrum Tool</a></span>
    </b-navbar>
    <!-- Project navigation items -->
    <b-nav tabs v-if="isProject" class="ml-auto" style="position:relative;top:27px; left:29%" >
      <b-nav-item active href="/#/projectpageoverview" > <label style="color:grey;"> Overview </label> </b-nav-item>
      <b-nav-item href="/#/backlog" >  <label style="color:grey;"> Procuct Backlog</label> </b-nav-item>
      <b-nav-item href="/#/oldsprints">  <label style="color:grey;"> Sprints </label> </b-nav-item>
    </b-nav>

    <b-collapse is-nav id="nav_collapse">
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <!-- Project notifications -->
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
        <!-- Setting notifications -->
        <b-nav-item-dropdown right>
          <template slot="button-content">
            <em>
              <img src="https://www.mindvoize.com/images/userImage.png" style="width:30px; position: relative; bottom: 2.5px">
            </em>
          </template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
          <b-dropdown-item-button v-on:click="logout">Sign out</b-dropdown-item-button>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
    export default {
        name: "Navbar",
        data() {
              return{
                isProject: true
            }
        },
        methods: {
          logout() {
            localStorage.setItem('userId','null');
            localStorage.setItem('auth_token', 'null');
            this.$router.push({path: '/logout'});
          },
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

  .container-fluid {
    padding-right: 0;
    padding-left: 0;
  }


  .text-enhancement {
    font-family: 'Merienda', cursive;
    padding-left: 15px;
    font-size: 32px;
    color: white;
  }
</style>
