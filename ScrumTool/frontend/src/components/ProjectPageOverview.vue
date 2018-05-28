<template>
  <div>
    <b-container class="Navigation" fluid>

      <navbar></navbar>
      <sidebar></sidebar>

      <br>
      <b-row style="padding-top:10px;">
        <b-col class="text-left">
          <h2>Insurance App</h2>
        </b-col>
      </b-row>
      <b-row>
        <div class="line">.</div>
      </b-row>
      <template>
        <b-container>
          <b-row>
            <b-col>
              <b-jumbotron>
                <b-row>
                  <h2>Current sprint</h2>
                </b-row>
                <b-row>
                  <b-col>
                    <b-card title="TODO">

                    </b-card>
                  </b-col>
                  <b-col>
                    <b-card title="DOING">

                    </b-card>
                  </b-col>
                  <b-col>
                    <b-card title="DONE">

                    </b-card>
                  </b-col>
                </b-row>
                <br>
                <b-card>
                  <p style="text-align: left">Days Remaining:</p>
                </b-card>
                <br>
                <b-button variant="primary">Go to Sprint Page</b-button>
                <br>
              </b-jumbotron>
            </b-col>
            <b-col>
              <b-jumbotron>
                <b-row>
                  <h2>Team</h2>
                </b-row>
              </b-jumbotron>
            </b-col>
          </b-row>
        </b-container>
      </template>
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
    }
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

</style>

