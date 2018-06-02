<template>
  <div v-if = "true">
    <b-container class="Navigation" fluid>

      <navbar></navbar>
      <!--<sidebar></sidebar>-->

      <br>
      <b-row>
        <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
      </b-row>
      <b-row style="padding-top:10px;">
        <b-col class="text-left">
          <h2>Insurance App</h2>
          <p> {{$route.params.id}} </p>
        </b-col>
      </b-row>
      <b-row>
        <div class="line">.</div>
      </b-row>


      <b-row>
        <b-col>

          <div style="height: 100%">
            <b-container fluid>
              <b-jumbotron>
                <br>
                <b-row>
                  <h2>Current sprint</h2>
                </b-row>
                <br>
                <b-row>
                  <b-col>
                    <b-card title="TODO">
                      <p class="card-text">1024</p>
                    </b-card>
                  </b-col>
                  <b-col>
                    <b-card title="DOING">
                      <p class="card-text">5</p>
                    </b-card>
                  </b-col>
                  <b-col>
                    <b-card title="DONE">
                      <p class="card-text">15</p>
                    </b-card>
                  </b-col>
                </b-row>
                <br><br><br>
                <b-row>
                  <b-col>
                    <b-card no-body class="text-left">
                      <p class="card-text">Days Remaining:  15</p>
                    </b-card>
                  </b-col>
                  <b-col></b-col>
                </b-row>
                <br><br><br>
                <b-row style="padding-top:10px;">
                  <b-col class="text-left">
                    <h5>Progress</h5>
                  </b-col>
                </b-row>
                <b-progress :value="value" show-progress class="mb-3"></b-progress>
                <br>
                <b-button v-if="true" variant="primary">Go to Sprint Page</b-button>
                <b-button v-else variant="primary">Create new Sprint</b-button>
                <br><br>

              </b-jumbotron>
            </b-container>
          </div>
        </b-col>
        <b-col>


          <b-container fluid>
            <b-jumbotron>
              <br>
              <b-row>
                <h2>Team</h2>
              </b-row>
              <br>
              <b-list-group>
                <b-list-group-item class="flex-column align-items-start">
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Product Owner</h5>
                  </div>
                  <p align="left">
                    Name: Steve Jobs
                    <br>
                    Email: unavailable
                  </p>
                </b-list-group-item>
                <b-list-group-item class="flex-column align-items-start">
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Scrum Master</h5>
                  </div>
                  <p align="left">
                    Name: Bill Gates
                    <br>
                    Email: unavailable
                  </p>
                </b-list-group-item>
                <b-list-group-item class="flex-column align-items-start">
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Developer</h5>
                  </div>
                  <p align="left">
                    Name: Evaggelos Raptis
                    <br>
                    Email: unavailable
                  </p>
                </b-list-group-item>
              </b-list-group>
              <br>
              <template>
                <div>
                  <b-form inline>
                    Add User &nbsp;
                    <b-input class="mb-2 mr-sm-2 mb-sm-0" id="inlineFormInputName2" placeholder="email" />
                    as &nbsp;
                    <b-form-select class="mb-2 mr-sm-2 mb-sm-0"
                                   :value="null"
                                   :options="{ '1': 'Scrum Master', '2': 'Developer'}"
                                   id="inlineFormCustomSelectPref">
                      <option slot="first" :value="null">Choose role</option>
                    </b-form-select>
                    <b-button variant="primary">Invite</b-button>
                  </b-form>
                </div>
              </template>
              <br><br>

            </b-jumbotron>
          </b-container>
        </b-col>
      </b-row>



    </b-container>
  </div>
  <div v-else>
    <b-container class="Navigation" fluid>
      <navbar></navbar>
      <br>
      <b-row>
        <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
      </b-row>
      <b-row style="padding-top:10px;">
        <b-col class="text-left">
          <h2>Insurance App</h2>
        </b-col>
      </b-row>
      <b-row>
        <div class="line">.</div>
      </b-row>
      <br>
      <b-button variant="primary">Create new Sprint</b-button>
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
    },
    data() {
      return {
        value: 75,
        selected: [], // Must be an array reference!
        options: [{
          text: 'User Story 1',
          value: 'orange'
        }, {
          text: 'User Story 2',
          value: 'apple'
        }, {
          text: 'User Story 3',
          value: 'pineapple'
        }],
        items: [{
          text: 'Home',
          href: '#'
        }, {
          text: 'Insurance App',
          href: '#'
        }, {
          text: 'Overview',
          active: true
        }]
      }
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

