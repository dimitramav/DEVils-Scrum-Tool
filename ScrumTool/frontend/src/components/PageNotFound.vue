<template>
  <b-container class="Navigation" fluid>
      <navbar :dashboard="false"></navbar>
      <b-row class="padding">
        <b-col></b-col>
        <b-col>
          <h1 class="notfound-style">404</h1>
          <h1>Oops! Page not found</h1>
          <h1><a href="/#/">Back to homepage</a></h1>
        </b-col>
        <b-col></b-col>
      </b-row>
  </b-container>
</template>

<script>
  import Navbar from "./Navbar.vue"
    export default {
      components: {
        navbar: Navbar,
      },
        name: "PageNotFound"
    }
</script>

<style scoped>


  .Navigation {
    position: absolute;
    top: 0;
    left: 0;
  }

  .container-fluid {
    padding-right: 0;
    padding-left: 0;
  }

  .padding {
    padding-top: 50px;
  }

  .notfound-style {
    font-size: 250px;
  }

  a {
    text-decoration: none;
  }

</style>
