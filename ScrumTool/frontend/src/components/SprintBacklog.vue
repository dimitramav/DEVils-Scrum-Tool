<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
    <b-container fluid>
      <!--Sprint Infos-->
      <b-row>
        <b-col class="center">
        <h1>Sprint #{{currentSprint.numSprint}}</h1>
        </b-col>
      </b-row>
      <hr>
      <br>
      <b-row>
        <b-col>
          <h4><i class="text-muted">Sprint Goal: </i>{{currentSprint.goal}}</h4>
        </b-col>
        <b-col>
          <h4><i class="text-muted">Sprint Plan: </i>{{currentSprint.plan}}</h4>
        </b-col>
        <b-col>
          <h4><i class="text-muted">Deadline Date: </i>{{currentSprint.deadlineDate}} </h4>
        </b-col>
      </b-row>
      <hr style="margin-bottom:0px">
      <!--board-->
      <b-row >
        <b-col class="w-25 bg-light" >
          <br>
          <h2>User Stories</h2>
          <!--user stories card-->
          <b-card-group v-for="cur_story in currentStories" :key="cur_story.idPBI" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
          <b-card :title="cur_story.title" img-top tag="article" class="mb-2">

            <div slot="header"  >
              <b-row>

                <!--New Task-->
                <div>
                  <b-btn  v-b-modal="'new_task'+cur_story.idPBI" >Add Task</b-btn>
                  <!--
                  <new_pbi v-on:new_epic="newUserStory" :idProject="currentProject_id" :Epic_id="cur_pbi.idPBI"></new_pbi>!-->
                </div>
                <!--EDIT STORY-->
                <!--
                <edit_pbi v-on:edit_epic="editEpic" :idPBI="cur_pbi.idPBI" :idProject="currentProject_id" :title="cur_pbi.title" :desc="cur_pbi.description" :priority="cur_pbi.priority"></edit_pbi>
                -->
              </b-row>
            </div>
            <p class=" card-text"> {{cur_story.description}} </p>
          </b-card>
          </b-card-group>
        </b-col>
        <b-col >
          <br>
          <h2>TO DO</h2>
        </b-col>
        <b-col class="w-25 bg-light" >
          <br>
          <h2>DOING</h2>
        </b-col>
        <b-col>
          <br>
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
      name: "SprintBacklog"
    },
    data() {
      return {
        currentSprint:[],
        currentStories:[],
        currentProject_id:'',
        today:'',
      }
    },
    methods: {
      getSprintInfos() {
        const self = this;
        axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/sprints?isCurrent=true', {
          headers: {"auth": localStorage.getItem('auth_token')}
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") {
                console.log("Unauthorized user");
              }
            }
            if (response.data.sprint) {
              self.currentSprint = response.data.sprint;
            }

          })
          .catch(function (error) {
            console.log(error);
          })
      },
      getSprintStories() {
        const self = this;
        axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/stories?isCurrent=true', {
          headers: {"auth": localStorage.getItem('auth_token')}
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") {
                console.log("Unauthorized user");
              }
            }
            if (response.data.stories) {;
              self.currentStories = response.data.stories;
              console.log(self.currentStories);
            }

          })
          .catch(function (error) {
            console.log(error);
          })
      },

    },
    mounted(){
      this.currentProject_id=parseInt(this.$route.params.id);
      this.getSprintInfos();
      this.getSprintStories();
    },
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
