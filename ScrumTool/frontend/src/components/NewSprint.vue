<template>
    <b-container class="Navigation" fluid>
        <navbar></navbar>
        <br>
        <b-row>
          <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
        </b-row>
        <b-row>
          <b-col>
            <h2> Create New Sprint</h2>
          </b-col>
          <b-col></b-col>
          <b-col></b-col>
          <b-col></b-col>
        </b-row>
            <b-row>
                <p></p>
            </b-row>
        <b-card border-variant="light">
            <b-row>
                <label style="position: relative;left:25px">Plan:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input v-model="sprint.plan" type="text" placeholder="Type the project's plan"></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <label style="position: relative;left:25px">Ends:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input  v-model="sprint.deadlineDate" type="date"></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
                <label style="position: relative;left:25px">Goal:</label>
            </b-row>
            <b-row>
                <b-col>
                    <b-form-input v-model="sprint.goal" type="text" placeholder="Type project's goal"></b-form-input>
                </b-col>
                <b-col></b-col>
            </b-row>
            <b-row>
              <b-col align-self="center">
                <h3> Add user stories </h3>
              </b-col>
            </b-row>
            <b-row>
                <div class="line">.</div>
            </b-row>

          <b-row>
            <b-col>
              <select v-model="selectedValue" @click="loadStories(selectedValue.idPBI, selectedValue.title)">
                <option disabled value="">Please select one</option>
                <option v-for="item in epics" :value="item">{{item.title}}</option>
              </select>
            </b-col>
            <b-col>
              <div>
                <b-form-select v-model="selected" :options="stories_options" class="mb-3" :select-size="4">
                </b-form-select>
                <div>Selected: <strong>{{ selected }}</strong></div>
              </div>
            </b-col>
            <b-col>
              <div>
                <b-form-select :options="selected_stories" class="mb-3" :select-size="4">
                </b-form-select>
              </div>
            </b-col>
          </b-row>
          <b-row>
            <p></p>
          </b-row>
          <b-row>
            <div class="line">.</div>
          </b-row>
          <b-row>
            <b-col></b-col>
            <b-col></b-col>
            <b-col align-self="end">
              <b-button type="submit" size="lg" variant="danger" @click="submit">
                Submit
              </b-button>
            </b-col>
          </b-row>
        </b-card>
      {{selected_stories}}
      {{sprint}}
    </b-container>
</template>

<script>
    import Navbar from "./Navbar.vue"
    import axios from 'axios'
    import json from '../assets/team.json'
    export default {
        components: {
            navbar: Navbar,
        },
      data() {
            return {
              sprint: {
                deadlineDate: '',
                goal: '',
                plan: '',
                isCurrent: true,
                numSprint: 2,
                Project_id: 3
              },

              selected: null,
              selectedValue: null,
              selectedvalue: null,
              options: [],
              stories_options: [],
              selected_stories: [],
              pbis_list: [],
              epics: [],
              items: [{
                text: 'Home',
                href: '/#/'
              }, {
                text: 'Incurance App',
                href: '/#/projectpageoverview'
              }, {
                text: 'Create New Sprint',
                active: true
              }]
            }
        },
      methods: {
        submit () {
          const self = this;
          console.log(self.sprint);
          let new_sprint = {deadlineDate: self.sprint.deadlineDate, Project_id: self.sprint.Project_id,
            goal: self.sprint.goal, plan: self.sprint.plan
            , isCurrent: self.sprint.isCurrent, numSprint: self.sprint.numSprint};

          let config = {
            headers: { "auth": localStorage.getItem('auth_token'), "Content-Type":'application/json' }
          }
          // Call to create the new sprint
          axios.post('http://localhost:8765/app/api/users/'+localStorage.getItem('userId') + '/projects/5/sprints', new_sprint, config)
            .then(function (response) {
              console.log(response.data.Sprint_id)
              // Call to update the pbis !
              // Sprint_id, idPBI
              for(let i = 0; i < self.selected_stories.length; i++) {
                self.pbis_list.push({idPBI: self.selected_stories[i].value, Sprint_id: response.data.Sprint_id, Project_id: 3})
              }

              axios.patch('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/5/pbis', self.pbis_list, config)
                .then(function (response) {
                    console.log("xoxo")
                })
                .catch(function(error){

                })
            })
            .catch(function (error) {
              console.log(error);
            })
        },
        loadStories(idPBI, title) {
          console.log(idPBI);
          console.log(title);
          // Call the other rest endpoint to get the stories of each epic
          const self = this;
          axios.get('http://localhost:8765/app/api/users/'+localStorage.getItem('userId') + '/projects/5/pbis?epicId=' + idPBI, {
            headers: { "auth": localStorage.getItem('auth_token') }
          })
            .then(function (response) {
              if (response.data.error) {
                if (response.data.error == "Unauthorized") {
                  console.log("Unauthorized");
                }
              }
              if (response.data.results) {
                self.stories_options = []
                self.stories = response.data.results
                let id;
                let title;
                // Create an array of object
                for(let i = 0; i < self.stories.length; i++) {
                  title = self.stories[i].title
                  id = self.stories[i].idPBI
                  self.stories_options.push({value: id, text: title})
                }
                console.log(self.stories)
              }
            })
            .catch(function (error) {
              console.log(error);
            })
        },
        getEpics () {
          const self = this;
          axios.get('http://localhost:8765/app/api/users/'+localStorage.getItem('userId') + '/projects/5/pbis?isEpic=true', {
            headers: { "auth": localStorage.getItem('auth_token') }
          })
            .then(function (response) {
              if (response.data.error) {
                if (response.data.error == "Unauthorized") {
                  console.log("Unauthorized");
                }
              }
              if (response.data.results) {
                self.epics = response.data.results;
                let id;
                let title;
                // Create an array of object
                for(let i = 0; i < self.epics.length; i++) {
                  title = self.epics[i].title;
                  id = self.epics[i].idPBI;
                  self.options.push({value: id, text: title});
                }

                console.log(self.epics);
              }
            })
            .catch(function (error) {
              console.log(error);
            })
        },
        selectedStories(value) {
          const self = this;
          console.log("selected");
          // filter using the pbi id
          var result = self.stories_options.filter(function( obj ) {
            return obj.value == value;
          });
          // Pass into the new list
          self.selected_stories.push({value: value, text: result[0].text});
          console.log(result[0].text);
        }
      },
      mounted() {
        this.getEpics();
      },
      watch: {
        selected: function (value) {
          this.selectedStories(value);
          console.log(value);
        }
      }
    }
</script>

<style scoped>
    .line {
        width: 99%;
        font-size: 1px;
        line-height: 2px;
        background-color: lavender;
        margin-top: -6px;
        margin-bottom: 10px;
    }

    .Navigation {
        position: absolute;
        top: 0;
        left: 0;
    }

    .container-fluid {
        padding-right: 0;
        padding-left: 0;
    }

</style>
