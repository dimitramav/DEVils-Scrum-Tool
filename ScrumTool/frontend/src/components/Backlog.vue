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
              <b-form-input id="newEpicName" placeholder="New Epic's Name" v-model="form.newEpicTitle" required/>
              <h5>Description:</h5>
              <label class="sr-only" for="newEpicDescription"></label>
              <b-form-input id="newEpicDescription" placeholder="New Epic's Description" v-model="form.newEpicDesc" required/>
              <h5>Priority:</h5>
              <b-row class="text-center">
                <b-col>
                  <label for="high">High</label>
                  <input type="radio" name="myChoice" id="high" value="High" v-model="pick_EpicPriority" required>
                </b-col>
                <b-col>
                  <label for="medium">Medium</label>
                  <input type="radio" name="myChoice" id="medium" value="Medium" v-model="pick_EpicPriority" required>
                </b-col>
                <b-col>
                  <label for="low">Low</label>
                  <input type="radio" name="myChoice" id="low" value="Low" v-model="pick_EpicPriority" required>
                </b-col>
              </b-row>
              <b-button variant="success" type="submit" style="margin-top: 10px; width: 100%;">Add to Epics</b-button>
            </b-form>
          </b-dropdown>
        </div>
      </b-col>
    </b-row>
    <b-row>
      <b-card-group v-for="cur_pbi in currentPbis" :key="cur_pbi.idPBI" deck style="margin-left: 10px;">
      <b-card :title="cur_pbi.title" img-top tag="article" class="mb-2">
        <div slot="header">
          <b-row>
            <b-col class="text-left">
              <h5 >Epic</h5>
            </b-col>
            <b-col class="text-right">
              <!-- EDIT EPIC -->
              <b-btn v-b-modal="'modal'+cur_pbi.idPBI">Edit</b-btn>
              <b-modal :id="'modal'+cur_pbi.idPBI" title="Update Epic"   @ok="updateEpic(cur_pbi.idPBI,$event)">
                <div class="text-left" :id="'updateEpic1'+cur_pbi.idPBI">
                  <b-form>
                    <b-form-group
                                  :label="'Title:' + cur_pbi.idPBI"
                                  :label-for="'updateEpicTitle'+cur_pbi.idPBI">
                      <b-form-input :id="'updateEpicTitle'+cur_pbi.idPBI"
                                    type="text"
                                    v-model="form.updateEpicTitle"
                                    required
                                    :value="cur_pbi.title">
                      </b-form-input>
                    </b-form-group>
                    <b-form-group :id="'updateEpic2'+cur_pbi.idPBI"
                                  label="Description:"
                                  :label-for="'updateEpicDesc'+cur_pbi.idPBI">
                      <b-form-textarea :id="'updateEpicDesc'+cur_pbi.idPBI"
                                    type="text"
                                    v-model="form.updateEpicDesc"
                                    required :rows="3"
                                       :max-rows="6"
                                    :value="cur_pbi.description">
                      </b-form-textarea>
                    </b-form-group>
                    <h6>Priority:</h6>
                    <b-row class="text-center">
                      <b-col>
                        <label for="high_up">High</label>
                        <input type="radio" name="myChoice" id="high_up" value="High" v-model="update_EpicPriority" required>
                      </b-col>
                      <b-col>
                        <label for="medium_up">Medium</label>
                        <input type="radio" name="myChoice" id="medium_up" value="Medium" v-model="update_EpicPriority" required>
                      </b-col>
                      <b-col>
                        <label for="low_up">Low</label>
                        <input type="radio" name="myChoice" id="low_up" value="Low" v-model="update_EpicPriority" required>
                      </b-col>
                    </b-row>
                  </b-form>
                </div>
              </b-modal>
            </b-col>
          </b-row>
        </div>
        <p class=" card-text
              ">
              {{cur_pbi.description}}
              </p>
              <!--New User Story-->
              <div row>
                <b-btn  v-b-modal="'new_story'+cur_pbi.idPBI" variant="primary">Add User Story</b-btn>
                  <b-modal :id="'new_story'+cur_pbi.idPBI" title="Add User Story" @ok="newStory(cur_pbi.idPBI,$event)">
                    <div class="text-left" :id="'new_story'+cur_pbi.idPBI">
                      <b-form>
                        <b-form-group
                          label="Title:"
                          :label-for="'addStoryTitle'+cur_pbi.idPBI">
                          <b-form-input :id="'addStoryTitle'+cur_pbi.idPBI"
                                        type="text"
                                        v-model="form.newStoryTitle"
                                        required>
                          </b-form-input>
                        </b-form-group>
                        <b-form-group
                                      label="Description:"
                                      :label-for="'addStoryDesc'+cur_pbi.idPBI">
                          <b-form-textarea :id="'updateEpicDesc'+cur_pbi.idPBI"
                                        type="text"
                                           :rows="3"
                                           :max-rows="6"
                                        v-model="form.newStoryDesc"
                                        required>
                          </b-form-textarea>
                        </b-form-group>
                        <h6>Priority:</h6>
                        <b-row class="text-center">
                          <b-col>
                            <label for="high_story">High</label>
                            <input type="radio" name="myChoice" id="high_story" value="High" v-model="pick_StoryPriority" required>
                          </b-col>
                          <b-col>
                            <label for="medium_story">Medium</label>
                            <input type="radio" name="myChoice" id="medium_story" value="Medium" v-model="pick_StoryPriority" required>
                          </b-col>
                          <b-col>
                            <label for="low_story">Low</label>
                            <input type="radio" name="myChoice" id="low_story" value="Low" v-model="pick_StoryPriority" required>
                          </b-col>
                        </b-row>
                      </b-form>
                    </div>
                  </b-modal>
              </div>
              <!--Get User Stories of each epic-->
              <div row style="padding-top: 2px">
              <b-btn v-b-toggle="'collapse'+cur_pbi.idPBI" v-on:click="getEpicUserStories(cur_pbi.idPBI)"
                     variant="primary"> User Stories
              </b-btn>
              </div>
              <b-collapse :id="'collapse'+cur_pbi.idPBI" class="mt-2">
                <div>
                  <b-card-group v-for="cur_us in currentUserStories[cur_pbi.idPBI]" :key="cur_us.idPBI" deck
                                style="margin: 0 auto;float: none;margin-bottom: 10px;">
                    <b-card class="mb-1">
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
                        <div class="text-right">
                          <!--EDIT USER STORY-->
                          <b-btn v-b-modal="'modal_story'+cur_us.idPBI">Edit</b-btn>
                        </div>
                          <b-modal :id="'modal_story'+cur_us.idPBI" title="Update User Story"   @ok="updateStory(cur_us.idPBI,cur_us.Epic_id,$event)">
                            <div class="text-left" :id="'updateStory1'+cur_us.idPBI">
                              <b-form>
                                <b-form-group
                                  :label="'Title:' + cur_us.idPBI"
                                  :label-for="'updateStoryTitle'+cur_us.idPBI">
                                  <b-form-input :id="'updateStoryTitle'+cur_us.idPBI"
                                                type="text"
                                                v-model="form.updateStoryTitle"
                                                required
                                                :value="cur_us.title">
                                  </b-form-input>
                                </b-form-group>
                                <b-form-group :id="'updateStory2'+cur_us.idPBI"
                                              label="Description:"
                                              :label-for="'updateStoryDesc'+cur_us.idPBI">
                                  <b-form-textarea :id="'updateStoryDesc'+cur_us.idPBI"
                                                type="text"
                                                   :rows="3"
                                                   :max-rows="6"
                                                v-model="form.updateStoryDesc"
                                                required
                                                :value="cur_us.description">
                                  </b-form-textarea>
                                </b-form-group>
                                <h6>Priority:</h6>
                                <b-row class="text-center">
                                  <b-col>
                                    <label for="high_upstory">High</label>
                                    <input type="radio" name="myChoice" id="high_upstory" value="High" v-model="update_StoryPriority" required>
                                  </b-col>
                                  <b-col>
                                    <label for="medium_upstory">Medium</label>
                                    <input type="radio" name="myChoice" id="medium_upstory" value="Medium" v-model="update_StoryPriority" required>
                                  </b-col>
                                  <b-col>
                                    <label for="low_upstory">Low</label>
                                    <input type="radio" name="myChoice" id="low_upstory" value="Low" v-model="update_StoryPriority" required>
                                  </b-col>
                                </b-row>
                              </b-form>
                            </div>
                          </b-modal>

                      </b-collapse>
                    </b-card>
                  </b-card-group>
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
import json from '../assets/team.json'
export default {
  components: {
    navbar: Navbar,
  },
  data() {
    return {
      form: {
        newEpicTitle: '',
        newEpicDesc: '',
      },
      form:{
        updateEpicTitle: '',
        updateEpicDesc: '',
      },
      form:{
        newStoryTitle:'',
        newStoryDesc:'',
      },
      form:{
        updateStoryTitle:'',
        updateStoryDesc:'',
      },
      update_StoryPriority:'',
      pick_EpicPriority: '',
      update_EpicPriority: '',
      pick_StoryPriority:'',
      logOut: null,
      currentPbis: [],
      currentUserStories: [[],[]],
      teamData: json.team,
    }
  },
  methods: {
    updateStory(current_id,current_epicId,evt) {
      const self = this;
      //console.log("hi " +       this.form.updateEpicTitle);
      this.update_StoryPriority = self.priorityToNumber(this.update_StoryPriority);
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      }
      let data = {
        title: this.form.updateStoryTitle,
        description: this.form.updateStoryDesc,
        priority: this.update_StoryPriority,
        Project_id: this.currentProject_id,
        idPBI: current_id,
        Epic_id:current_epicId,
      }
      axios.put(this.$url +'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis?isEpic=false' , data, config)
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
            response.data.results.priority = self.priorityToString(response.data.results.priority);
            var epic=response.data.results.Epic_id;
            var i = self.currentUserStories[epic].findIndex(o => o.idPBI === response.data.results.idPBI);
            console.log(response.data.results);
            self.currentUserStories[epic] = self.currentUserStories[current_epicId].filter(el => el.idPBI !== self.current_id);
            self.$set(self.currentUserStories[epic], i, response.data.results);
            //TODO: isws na kleinw to card gia na ananewnetai
          }
        })
    },
    newStory (current_epicId,evt) {
      evt.preventDefault();
      const self = this;
      this.pick_StoryPriority=self.priorityToNumber(this.pick_StoryPriority);
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      }
      let data = {
        title: this.form.newStoryTitle, description: this.form.newStoryDesc, priority:this.pick_StoryPriority, Project_id: this.currentProject_id, Epic_id: current_epicId ,
      }
      axios.post(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis', data, config)
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
            self.currentUserStories[response.data.results.Epic_id].push(response.data.results);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    priorityToNumber(priority)
    {
      if(priority==="High")
      {
        priority=1;
      }
      else if(priority==="Medium")
      {
        priority=2;
      }
      else if(priority==="Low")
      {
        priority=3;
      }
      console.log(priority);
      return priority;
    },
    priorityToString(priority)
    {
      if(priority===1)
      {
        priority="High";
      }
      else if(priority===2)
      {
        priority="Medium";

      }
      else if(priority===3)
      {
        priority="Low";
      }
      return priority;
    },
    updateEpic(current_id,evt){
      const self = this;
      //console.log("hi " +       this.form.updateEpicTitle);
      this.update_EpicPriority=self.priorityToNumber(this.update_EpicPriority);
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      }
      let data = {
        title: this.form.updateEpicTitle, description: this.form.updateEpicDesc, priority:this.update_EpicPriority, Project_id: this.currentProject_id, idPBI: current_id,
      }
      axios.put(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis?isEpic=true', data, config)
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
            var i = self.currentPbis.findIndex(o => o.idPBI === response.data.results.idPBI);
            //remove previous epic
            console.log("current id")
            console.log(current_id);
            self.currentPbis = self.currentPbis.filter(el => el.idPBI !== self.current_id);
            console.log(self.currentPbis);
            //add updated epic
            self.$set(self.currentPbis,i,response.data.results);
            console.log("after push");
            console.log(self.currentPbis);

          }
        })
        .catch(function (error) {
          console.log(error);
        })

    },
    newEpic (evt) {
      evt.preventDefault();
      const self = this;
      this.pick_EpicPriority=self.priorityToNumber(this.pick_EpicPriority);
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      }
      let data = {
        title: this.form.newEpicTitle, description: this.form.newEpicDesc, priority:this.pick_EpicPriority, Project_id: this.currentProject_id ,
      }
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
            });
            self.currentPbis = response.data.results;
          }
        })
        .catch(function (error) {
          console.log(error);
        })
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
        response.data.results.forEach(function(arrayItem)
        {
          arrayItem.priority=self.priorityToString(arrayItem.priority);
        });
        if (response.data.results) {

          self.$set(self.currentUserStories,epicId,response.data.results);
        }
      })
      .catch(function (error) {
        console.log(error);
      })
    },
  },
  mounted() {
    if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
    this.currentProject_id=this.$route.params.id;
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
</style>
