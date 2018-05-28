<template>
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
</template>

<script>
    export default {
        name: "Navbar",
        methods: {
          logout() {
            localStorage.setItem('userId','null');
            localStorage.setItem('auth_token', 'null');
            this.$router.push({path: '/'});
          },
        }
    }
</script>

<style scoped>

</style>
