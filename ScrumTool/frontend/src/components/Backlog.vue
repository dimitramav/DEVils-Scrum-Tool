<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
    <b-container>
      <b-row>
        <b-col class="text-right">
          <div v-if="gotPBIS && currentPbis.length>0">
            <b-btn  v-b-modal="'new_storyundefined'" size="lg" variant ="success" class="m-md-2 pcsprint" >Create New Epic</b-btn>
            <new_pbi v-on:new_pbi="newEpic" :idProject="currentProject_id"></new_pbi>
          </div>
        </b-col>
      </b-row>

      <b-jumbotron v-if="gotPBIS && currentPbis.length===0">
        <h1 class="pcsprint">No Epics in Product Backlog</h1>
        <b class="pcsprint">Start creating!</b><br><br>
        <b-btn  v-b-modal="'new_storyundefined'" size="lg" variant ="success" class="m-md-2 pcsprint" >Create New Epic</b-btn>
        <new_pbi v-on:new_pbi="newEpic" :idProject="currentProject_id"></new_pbi>
      </b-jumbotron>

      <b-row>

        <b-card-group v-for="cur_pbi in currentPbis" :key="cur_pbi.idPBI" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
          <b-card :title="cur_pbi.title" img-top tag="article" class="mb-2">
            <div slot="header">
              <b-row>
                <b-col class="text-left">
                  <!--<h4>Epic</h4>-->
                </b-col>

                <!--New User Story-->
                <div row>
                  <b-btn class="pcsprint" v-b-modal="'new_story'+cur_pbi.idPBI" style="height:98%">Add User Story</b-btn>
                  <new_pbi v-on:new_pbi="newUserStory" :idProject="currentProject_id" :Epic_id="cur_pbi.idPBI"></new_pbi>
                </div>
                <!--EDIT EPIC-->
                <edit_pbi class="pcsprint" v-on:edit_pbi="editEpic" :idPBI="cur_pbi.idPBI" :idProject="currentProject_id" :title="cur_pbi.title" :desc="cur_pbi.description" :priority="cur_pbi.priority"></edit_pbi>
                <issue class="pcsprint" v-on:edit_pbi="editEpic" :idPBI="cur_pbi.idPBI" :idProject="currentProject_id" :title="cur_pbi.title" :desc="cur_pbi.description" :priority="cur_pbi.priority"></issue>
              </b-row>
            </div>
            <p class="card-text pcsprint" style="color: grey;"> {{cur_pbi.description}} </p>

            <!--Get User Stories of each epic-->
            <div row style="padding-top: 2px">
              <b-btn class="pcsprint" v-b-toggle="'collapse'+cur_pbi.idPBI" v-on:click="getEpicUserStories(cur_pbi.idPBI)"
                     variant="primary" style="background-color: #333333;"> User Stories
              </b-btn>
            </div>
            <b-collapse :id="'collapse'+cur_pbi.idPBI" class="mt-2 pcsprint">
              <div>

                <b-jumbotron>

                  <draggable v-model="currentUserStories[cur_pbi.idPBI]" :move="onUserStoryMove" @change="onUserStoryChange" :options="{group:'UserStories'}">

                    <b-card-group v-for="cur_us in currentUserStories[cur_pbi.idPBI]" :key="cur_us.idPBI" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
                      <b-card v-if="cur_us.idPBI>0" class="mb-1">
                        <b-card-header header-tag="header" class="p-1" role="tab">
                          <b-btn block href="#" v-b-toggle="'collapse'+cur_us.idPBI" variant="info">{{cur_us.title}}
                          </b-btn>
                        </b-card-header>
                        <b-collapse :id="'collapse'+cur_us.idPBI" visible accordion="my-accordion" role="tabpanel">
                          <b-card-body>
                            <p class="card-text">
                              <b-row>
                                {{cur_us.description}}
                              </b-row>
                            </p>
                            <p class="card-text">
                              <b-row>
                                <i class="text-muted">{{priorityToString(cur_us.priority)}}</i>
                              </b-row>
                            </p>
                          </b-card-body>

                          <edit_pbi v-on:edit_pbi="editStory" :epicId="cur_pbi.idPBI" :idPBI="cur_us.idPBI" :idProject="currentProject_id" :title="cur_us.title" :desc="cur_us.description" :priority="cur_us.priority"></edit_pbi>

                        </b-collapse>
                      </b-card>
                      <div v-show="cur_us.idPBI===-1">
                        <b>No User Stories yet.</b><br>
                        <i> Try to create a new one </i><br>
                        <i> or drag here from another epic</i>
                      </div>
                    </b-card-group>
                  </draggable>
                </b-jumbotron>

              </div>
            </b-collapse>

            <div slot="footer">
              <h6 class="text-muted pcsprint">{{priorityToString(cur_pbi.priority)}}</h6>
            </div>
          </b-card>
        </b-card-group> 
      </b-row>
    </b-container>

  </b-container>

</template>


<script>import axios from 'axios'
import Navbar from "./Navbar.vue"
import EditPBI from "./EditPBI.vue"
import draggable from 'vuedraggable'
import NewPBI from './NewPBI.vue'
import Issue from './Issue.vue'

export default {
  components: {
    navbar: Navbar,
    edit_pbi: EditPBI,
    draggable,
    new_pbi: NewPBI,
    issue: Issue,
  },
  data() {
    return {
      options: [
        { text: 'High', value: 0 },
        { text: 'Medium', value: 1 },
        { text: 'Low', value: 2 },
      ],
      currentEpicId: -1,
      currentProject_id: -1,
      gotPBIS: false,

      currentPbis: [],
      currentUserStories: [[],[]],

    }
  },
  methods: {
    onUserStoryChange(input){
      console.log(input);
      if (input.added) {
        const self=this;
        if (this.currentUserStories[this.currentEpicId].length===2) {
          if (this.currentUserStories[this.currentEpicId][0].idPBI===-1) this.currentUserStories[this.currentEpicId].splice(0,1);
          else if (this.currentUserStories[this.currentEpicId][1].idPBI===-1) this.currentUserStories[this.currentEpicId].splice(1,1);
        }

        //console.log(this.currentUserStories[this.currentEpicId]);
        let element = input.added.element;
        let config = {
          headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
        };
        let data = {
          title: element.title, description: element.description, priority: element.priority, Project_id: element.Project_id, idPBI: element.idPBI, Epic_id: this.currentEpicId,
        };
        axios.put(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis?isEpic=false', data, config)
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
              else if (response.data.error === "Unauthorized projects") console.log("Unauthorized projects");
              else if (response.data.error === "null") console.log("Null token");
              else console.log(error);
            }
            else if (response.data.results) {
              console.log(response.data.results);
              self.currentUserStories[self.currentEpicId][input.added.newIndex].Epic_id=self.currentEpicId;
            }
            else console.log("Unresolved response: " + response);
          })
          .catch(function (error) {
            console.log(error);
          });
      }
      else if (input.removed) {
        let Epic_id = input.removed.element.Epic_id;
        if (this.currentUserStories[Epic_id].length===0) {
          let data=[{
            Epic_id: Epic_id, idPBI: -1
          }];
          this.$set (this.currentUserStories, Epic_id, data);
        }
      }
    },

    onUserStoryMove ({relatedContext, draggedContext}) {
      console.log (relatedContext);
      console.log(draggedContext);
      this.currentEpicId = relatedContext.element.Epic_id;
    },

    getPBIS() {
      const self = this;
      axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.$route.params.id + '/pbis?isEpic=true', {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          else if (response.data.results) {
            self.gotPBIS=true;
            self.currentPbis = response.data.results;
          }
          else (console.log("Unresolved response: " + response));
        })
        .catch(function (error) {
          console.log(error);
        })
    },

    priorityToString(priority) {
      let str_priority;
      switch(priority) {
        case 1:
          str_priority="high";
          break;
        case 2:
          str_priority="medium";
          break;
        case 3:
          str_priority="low";
          break;
        default:
          console.log ("Unknown value" + priority);
      }
      return str_priority;
    },

    editEpic (idPBI, title, desc, priority) {
      let i = this.currentPbis.findIndex(o => o.idPBI === idPBI);
      //console.log(i);
      this.currentPbis[i].title=title;
      this.currentPbis[i].description=desc;
      this.currentPbis[i].priority=priority;
    },

    editStory(idPBI, title, desc, priority, epicId) {
      let i = this.currentUserStories[epicId].findIndex(o => o.idPBI === idPBI);

      this.currentUserStories[epicId][i].title=title;
      this.currentUserStories[epicId][i].description=desc;
      this.currentUserStories[epicId][i].priority=priority;
    },

    getEpicUserStories(epicId) {
      const self = this;
      axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.$route.params.id + '/pbis?isEpic=false&epicId='+epicId, {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          else if (response.data.results) {
            //console.log(response.data.results.length);
            if (response.data.results.length>0) self.$set(self.currentUserStories,epicId,response.data.results);
            else {
              let data=[{
                Epic_id: epicId, idPBI: -1
              }];
              self.$set (self.currentUserStories, epicId, data);
            }
            console.log(self.currentUserStories[epicId]);
            //self.currentUserStories = response.data.results;
          }
          else console.log("Unresolved response: " + response);
        })
        .catch(function (error) {
          console.log(error);
        })
    },

    newUserStory(data) {
      console.log(data);
      console.log(this.currentUserStories);
      if (this.currentUserStories[data.Epic_id]) this.currentUserStories[data.Epic_id].push(data);
    },

    newEpic(data) {
      //console.log(data);
      this.currentPbis.unshift(data);
    },
  },
  mounted() {
    console.log(this.currentPbis);
    if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
    this.currentProject_id=parseInt(this.$route.params.id);
    this.getPBIS();
  },
}
</script>


<style scoped>
  @import url('https://fonts.googleapis.com/css?family=Merienda');
  @import url('https://fonts.googleapis.com/css?family=VT323');
  @import url('https://fonts.googleapis.com/css?family=Quicksand');

  .ptitle{
    font-family: Merienda;
  }

  .pdeadline{
    font-family: VT323;font-size:24px; margin-top: 1%;
  }

  .pcsprint{
    font-family: Quicksand;
  }

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
