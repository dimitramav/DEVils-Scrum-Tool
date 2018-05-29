<template>
  <div>
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

