<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
    <b-container>
      <!--Sprint Infos-->
      <b-row>
        <h2>Sprint Title </h2>
      </b-row>
      <hr>
      <br>
      <b-row class="text-muted">
        <b-col>
          <h4><i>Sprint Goal:</i></h4>
        </b-col>
        <b-col>
          <h4><i>Sprint Plan:</i></h4>
        </b-col>
        <b-col>
          <h4><i>Duration: </i> </h4>
        </b-col>
      </b-row>
      <hr>
      <br>
      <!--board-->
      <b-row>

      </b-row>
      <b-row>
        <b-col>
          <h2>User Stories</h2>
        </b-col>
        <b-col>
          <h2>TO DO</h2>
        </b-col>
        <b-col>
          <h2>DOING</h2>
        </b-col>
        <b-col>
          <h2>DONE</h2>
        </b-col>
      </b-row>
    </b-container>
  </b-container>
</template>

<script>
  import axios from 'axios'
  import Navbar from "./Navbar.vue"
  export default {
    components: {
      navbar: Navbar,
    }
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
    padding-left:0;
  }


  .row {
    margin-right: 0px;
    margin-left: 0px;
  }
  .modal-backdrop.in {
    opacity: 0.9;
  }
</style>
