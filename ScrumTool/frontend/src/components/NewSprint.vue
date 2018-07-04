<template>
    <b-container class="Navigation" fluid>
        <navbar :dashboard="true"></navbar>
                <b-row>
            <p></p>
        </b-row>        <b-row>
            <p></p>
        </b-row>
        <b-row>
            <b-col align-self="start">
                <h3 class="text-enhancement">Create New Sprint</h3>
            </b-col>
        </b-row>
        <b-row>
            <p></p>
        </b-row>
        <b-row>
            <p></p>
        </b-row>
        <b-row>
            <b-col cols="1">
            </b-col>
            <b-col cols="4" class="text-center">
                <b-form>
                    <label class="text-enhancement">Plan:</label>
                    <b-form-input class="input-style" v-model="sprint.plan" type="text" placeholder="Type the project's plan"></b-form-input>
                    <b-row>
                        <p></p>
                    </b-row>
                    <label class="text-enhancement">Goal:</label>
                    <b-form-input class="input-style" v-model="sprint.goal" type="text" placeholder="Type the project's goal"></b-form-input>
                    <b-row>
                        <p></p>
                    </b-row>
                    <label class="text-enhancement">Deadline Date:</label>
                    <b-form-input class="input-style" v-model="sprint.deadlineDate" type="date"></b-form-input>
                </b-form>
            </b-col>
            <b-col>
                <b-row>
                    <b-col cols="4">
                        <h5 class="text-enhancement">Select an Epic: </h5>
                        <select v-model="selectedValue" @click="loadStories(selectedValue.idPBI, selectedValue.title)">
                            <option disabled value="">Please select one</option>
                            <option v-for="item in epics" :value="item">{{item.title}}</option>
                        </select>
                    </b-col>
                    <b-col cols="6">
                        <div>
                            <h5 class="text-enhancement">Click to add a user story: </h5>
                            <b-form-select v-model="selected" :options="stories_options" class="mb-3" :select-size="4">
                            </b-form-select>
                        </div>
                    </b-col>
                </b-row>
                <b-row>
                    <p></p>
                </b-row>
                <b-row>
                    <p></p>
                </b-row>
                <b-row>
                    <b-col cols="4">
                    </b-col>
                    <b-col cols="6">
                        <h5 class="text-enhancement">User Stories for the new sprint: </h5>
                        <b-form-select :options="selected_stories" class="mb-3" :select-size="4">
                        </b-form-select>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>
        <b-row>
            <b-col class="text-center">
                <b-button style="margin-top: 40px; padding-left:100px; padding-right: 100px;" type="submit" size="md" variant="danger" @click="submit">
                    Submit
                </b-button>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
  import Navbar from "./Navbar.vue"
  import axios from 'axios'
  import json from '../assets/team.json'
  export default {
    name: "NewSprint",
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
          numSprint: 0,
          Project_id: 0
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
          , isCurrent: self.sprint.isCurrent, numSprint: self.sprint.numSprint+1};
        console.log("paparia");
        console.log(self.sprint.numSprint);
       
        let config = {
          headers: { "auth": localStorage.getItem('auth_token'), "Content-Type":'application/json' }
        }
        // Call to create the new sprint

        axios.post(self.$url + 'users/' +localStorage.getItem('userId')+'/projects/'+ self.sprint.Project_id + '/sprints', new_sprint, config)
          .then(function (response) {
              for(var i = 0; i < self.selected_stories.length; i++) {
                self.pbis_list.push({idPBI: self.selected_stories[i].value, Sprint_id: response.data.Sprint_id, Project_id: self.sprint.Project_id})
              }
              console.log(self.pbis_list)
              console.log(self.sprint.Project_id)
            axios.patch(self.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + self.sprint.Project_id + '/pbis', self.pbis_list, config)
              .then(function (response) {
                // Debugging
                console.log("xoxo")
                self.$router.push({path: '/overview/' + self.sprint.Project_id})
              })
              .catch(function(error){
                console.log(error)
              })
            // Call to update the pbis !
            // Sprint_id, idPBI
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
        axios.get(self.$url + 'users/' +localStorage.getItem('userId')+'/projects/'+ self.sprint.Project_id + '/pbis?epicId=' + idPBI, {
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
        axios.get(self.$url + 'users/' +localStorage.getItem('userId')+'/projects/'+ self.sprint.Project_id + '/pbis?isEpic=true', {
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
        var find_pbi = self.selected_stories.find(function (find_pbi) { return find_pbi.value === value; });
        if (find_pbi == undefined) {
          self.selected_stories.push({value: value, text: result[0].text});
        }
      }
    },
    mounted() {
      this.sprint.Project_id = this.$route.params.id;      // Receive projectId from url parameters
      //this.sprint.Project_id = 5;
      this.sprint.numSprint  = this.$route.query.newSprintNum;   // Receive new numSprint (+1 from the previous one)
      if( isNaN(this.sprint.numSprint)){
        console.log("dada");
        this.sprint.numSprint=0;
      }
      //this.sprint.numSprint = 8;
      if (this.sprint.Project_id == null || this.sprint.numSprint == null) {
        console.log("Error: Add correct parameters (projectId and newSprintNum) in url");
      }
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
  @import url('https://fonts.googleapis.com/css?family=Merienda');

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

  .form-frame {
    margin-top: 50px;
    border-radius: 0px;
    background: #4568DC;  /* fallback for old browsers */
    background: linear-gradient(-90deg, #d3d3d3, #d5d5d5); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
    box-shadow: 20px 20px 50px #aaaaaa;
  }
  .container-fluid {

  }

  .input-style {
    border-radius: 5px;
    padding: 15px;
  }

  select {
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 20px;
    padding-right: 20px;
    margin-top: 10px;
    border-radius: 5px;
    background-color: #f3f3f3;
  }

  select.option {
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 20px;
    padding-right: 20px;
    margin-top: 10px;
    border-radius: 5px;
    background-color: red;
  }

  .text-enhancement {
    font-family: 'Merienda', cursive;
  }

</style>
