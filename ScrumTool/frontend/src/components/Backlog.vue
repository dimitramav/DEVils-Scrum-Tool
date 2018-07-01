<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
    <b-container>
      <b-row>
        <b-col class="text-right">
          <div>
            <b-dropdown id="ddown1" size="lg" variant ="info" text="Create New Epic" class="m-md-2">
              <b-form inline style="margin: 10px;" @submit="newEpic">
                <h4>Title:</h4>
                <label class="sr-only" for="newEpicName"></label>
                <b-form-input id="newEpicName" placeholder="New Epic's Name" v-model="newEpic_form.title" required></b-form-input>
                <h5>Description:</h5>
                <label class="sr-only" for="newEpicDescription"></label>
                <b-form-input id="newEpicDescription" placeholder="New Epic's Description" v-model="newEpic_form.desc" required></b-form-input>

                <b-form-group label="Priority">
                  <b-form-radio-group id="new_epic_radio" v-model="newEpic_form.selected" :options="options" name="radioOpenions">
                  </b-form-radio-group>
                </b-form-group>

                <b-button variant="success" type="submit" style="margin-top: 10px; width: 100%;">Add to Epics</b-button>
              </b-form>

            </b-dropdown>
          </div>
        </b-col>
      </b-row>
      <b-row>
        <b-card-group v-for="cur_pbi in currentPbis" :key="cur_pbi.idPBI" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
          <b-card :title="cur_pbi.title" img-top tag="article" class="mb-2">
            <div slot="header">
              <b-row>
                <b-col class="text-left">
                  <h4>Epic</h4>
                </b-col>

                <!--New User Story-->
                <div row>
                  <b-btn  v-b-modal="'new_story'+cur_pbi.idPBI" >Add User Story</b-btn>
                  <b-modal :id="'new_story'+cur_pbi.idPBI" title="Add User Story" @ok="newStory(cur_pbi.idPBI,$event)">
                    <div class="text-left" :id="'new_story'+cur_pbi.idPBI">
                      <b-form>
                        <b-form-group label="Title:" :label-for="'addStoryTitle'+cur_pbi.idPBI">
                          <b-form-input :id="'addStoryTitle'+cur_pbi.idPBI"
                                        type="text"
                                        v-model="newStory_form.title"
                                        required>
                          </b-form-input>
                        </b-form-group>
                        <b-form-group
                          label="Description:"
                          :label-for="'addStoryDesc'+cur_pbi.idPBI">
                          <b-form-input :id="'updateEpicDesc'+cur_pbi.idPBI"
                                        type="text"
                                        v-model="newStory_form.desc"
                                        required>
                          </b-form-input>
                        </b-form-group>
                        <b-form-group label="Priority">
                          <b-form-radio-group id="new_story_radio" v-model="newStory_form.selected" :options="options">
                          </b-form-radio-group>
                        </b-form-group>
                      </b-form>
                    </div>
                  </b-modal>
                </div>
                <!--EDIT EPIC-->
                <edit_pbi v-on:edit_epic="editEpic" :idPBI="cur_pbi.idPBI" :epicId="cur_pbi.idPBI" :idProject="currentProject_id" :title="cur_pbi.title" :desc="cur_pbi.description" :priority="cur_pbi.priority"></edit_pbi>
              </b-row>
            </div>
            <p class=" card-text"> {{cur_pbi.description}} </p>




            <!--Get User Stories of each epic-->
            <div row style="padding-top: 2px">
              <b-btn v-b-toggle="'collapse'+cur_pbi.idPBI" v-on:click="getEpicUserStories(cur_pbi.idPBI)"
                     variant="primary"> User Stories
              </b-btn>
            </div>
            <b-collapse :id="'collapse'+cur_pbi.idPBI" class="mt-2">
              <div>
                <draggable :list="currentUserStories" @change="onMove" :options="{group:'people'}">

                  <b-card-group v-for="cur_us in currentUserStories" :key="cur_us.idPBI" deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
                    <b-card v-if="cur_us.Epic_id===cur_pbi.idPBI" class="mb-1">
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
                              <i class="text-muted">{{cur_us.priority}}</i>
                            </b-row>
                          </p>
                        </b-card-body>

                        <edit_pbi v-on:edit_epic="editStory" :epicId="cur_pbi.idPBI" :idPBI="cur_us.idPBI" :idProject="currentProject_id" :title="cur_us.title" :desc="cur_us.description" :priority="cur_us.priority"></edit_pbi>


                      </b-collapse>
                    </b-card>
                  </b-card-group>
                </draggable>
              </div>
            </b-collapse>

            <div slot="footer">
              <h6 class="text-muted">{{cur_pbi.priority}}</h6>
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
export default {
  components: {
    navbar: Navbar,
    edit_pbi: EditPBI,
    draggable,
  },
  data() {
    return {
      newEpic_form: {
        title: '',
        desc: '',
        selected: '',
      },
      newStory_form:{
        title:'',
        desc:'',
        selected: '',
      },

      options: [
        { text: 'High', value: 'high' },
        { text: 'Medium', value: 'medium' },
        { text: 'Low', value: 'low' },
      ],

      currentProject_id: -1,
      modalShow: false,
      something: 'high',

      currentPbis: [],
      currentUserStories: [],
      loadedStory: [],
    }
  },
  methods: {

    onMove(something){
      if (something.added) {
        console.log(something);
        console.log(this.currentUserStories);
        console.log(this.currentUserStories[something.added.newIndex].Epic_id);

        let temp = something;
        temp.added.element.Epic_id = this.currentUserStories[something.added.newIndex].Epic_id;
        //console.log(temp);
        //this.currentUserStories.push(temp.added.element);
      }
      //something.added.element.Epic_id = this.currentUserStories[something.added.newIndex].Epic_id;
    },

    // onMove ({relatedContext, draggedContext}) {
    //   console.log(relatedContext);
    //   //console.log(draggedContext);
    // },

    getPBIS() {
      //evt.preventDefault();
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
          if (response.data.results) {
            //prepei na to spasw se synartisi
            response.data.results.forEach(function(arrayItem)
            {
              arrayItem.priority=self.priorityToString(arrayItem.priority);
              //self.modalShow[arrayItem.idPBI]=false;
            });
            self.currentPbis = response.data.results;
            //console.log(self.currentPbis);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },

    newEpic (evt) {
      evt.preventDefault();
      const self = this;
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      };
      let data = {
        title: this.newEpic_form.title, description: this.newEpic_form.desc, priority: self.priorityToNumber(this.newEpic_form.selected), Project_id: this.currentProject_id,
      };
      axios.post(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis?isEpic=true', data, config)
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
            else if (response.data.error === "Unauthorized projects") {
              console.log("Unauthorized projects");
            }
            else if (response.data.error === "null") {
              console.log("Null token");
            }
          }
          if (response.data.results) {
            response.data.results.priority=self.priorityToString(response.data.results.priority);
            console.log(response.data.results);
            self.currentPbis.push(response.data.results);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },


    editEpic (idPBI, title, desc, priority) {
      //console.log('mphka sthn synartisi, tittle = ' + idPBI + title + desc, priority);
      let i = this.currentPbis.findIndex(o => o.idPBI === idPBI);
      //console.log(i);
      this.currentPbis[i].title=title;
      this.currentPbis[i].description=desc;
      this.currentPbis[i].priority=priority;
    },

    editStory(idPBI, title, desc, priority, epicId) {
      //console.log (v1, v2, v3, v4, v5);
      let i = this.currentUserStories.findIndex(o => o.idPBI === idPBI);

      this.currentUserStories[i].title=title;
      this.currentUserStories[i].description=desc;
      this.currentUserStories[i].priority=priority;
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
            if (!self.loadedStory[epicId]===true) {

              response.data.results.forEach(function(arrayItem) {
                arrayItem.priority=self.priorityToString(arrayItem.priority);
                self.currentUserStories.push(arrayItem);
              });
              self.loadedStory[epicId]=true;
              console.log(response.data.results);
              //Array.prototype.push.apply(self.currentUserStories, response.data.results);
              //self.$set()
              console.log(self.currentUserStories);
              //self.currentUserStories = Object.assign({}, self.currentUserStories, response.data.results);



            }
            //self.$set(self.currentUserStories,epicId,response.data.results);
            //console.log(response.data.results);
            //self.currentUserStories = response.data.results;
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },

    newStory (current_epicId) {
      //evt.preventDefault();
      const self = this;
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      };
      let data = {
        title: this.newStory_form.title, description: this.newStory_form.desc, priority: self.priorityToNumber(this.newStory_form.selected), Project_id: this.currentProject_id, Epic_id: current_epicId ,
      };
      axios.post(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis', data, config)
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
            else if (response.data.error === "Unauthorized projects") console.log("Unauthorized projects");
            else if (response.data.error === "null") console.log("Null token");
            else console.log(error);
          }
          if (response.data.results) {
            console.log(response.data.results);
            response.data.results.priority=self.priorityToString(response.data.results.priority);
            //self.currentUserStories.push(response.data.results);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },

    priorityToNumber(priority) {
      let num_priority;
      switch(priority) {
        case "high":
          num_priority=1;
          break;
        case "medium":
          num_priority=2;
          break;
        case "low":
          num_priority=3;
          break;
        default:
          console.log ("Unknown value" + priority);
      }
      return num_priority;
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


  },
  mounted() {
    if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
    this.currentProject_id=parseInt(this.$route.params.id);
    this.getPBIS();
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
