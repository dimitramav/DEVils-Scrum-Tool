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
      <br>
      <!--board-->
      <b-container class="bv-example-row bv-example-row-flex-cols">
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
      <div v-for="cur_story in currentStories" :key="cur_story.idPBI" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
        <b-row>
          <b-col>
            <b-card :title="cur_story.title" img-top tag="article" class="mb-2" >

              <div slot="header"  >
                <b-row>

                  <!--New Task-->
                  <div>
                    <b-btn  v-b-modal="'new_task'+cur_story.idPBI">Add Task</b-btn>
                    <b-modal @ok="addTask(cur_story.idPBI)" class="text-left" :id="'new_task'+cur_story.idPBI" title="Add new task">
                      <b-form>
                        <b-form-group label="Description:" :label-for="'addTask'+cur_story.idPBI">
                          <b-form-input :id="'addTask'+cur_story.idPBI"
                                        type="text"
                                        v-model="newTask_form.desc"
                                        required>
                          </b-form-input>
                        </b-form-group>
                      </b-form>
                    </b-modal>
                  </div>
                </b-row>
              </div>
            </b-card>
          </b-col>
          <b-col class="w-25 bg-light">
            <b-card-group v-for="cur_task in currentTasks"  :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
              <div v-if="cur_task.state===1 && cur_task.PBI_id===cur_story.idPBI">
              <b-card img-top tag="article" class="mb-2">
                <div slot="header"  >
                  <b-row>
                    <div>
                      <b-btn  v-b-modal="'edit_task'+cur_task.idTask">Edit Task</b-btn>
                    </div>
                  </b-row>
                </div>
                <p class="card-text">{{cur_task.description}}</p>
              </b-card>
               </div>
            </b-card-group>
          </b-col>
          <b-col>
            <b-card-group v-for="cur_task in currentTasks"  :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
              <div v-if="cur_task.state===2 && cur_task.PBI_id===cur_story.idPBI">
                <b-card img-top tag="article" class="mb-2">
                  <div slot="header"  >
                    <b-row>
                      <div>
                        <b-btn  v-b-modal="'edit_task'+cur_task.idTask">Edit Task</b-btn>
                      </div>
                    </b-row>
                  </div>
                  <p class="card-text">{{cur_task.description}}</p>
                </b-card>
              </div>
            </b-card-group>
          </b-col>
          <b-col class="w-25 bg-light">
            <b-card-group v-for="cur_task in currentTasks"  :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
              <div v-if="cur_task.state===3 && cur_task.PBI_id===cur_story.idPBI">
                <b-card img-top tag="article" class="mb-2">
                  <div slot="header"  >
                    <b-row>
                      <div>
                        <b-btn  v-b-modal="'edit_task'+cur_task.idTask">Edit Task</b-btn>
                      </div>
                    </b-row>
                  </div>
                  <p class="card-text">{{cur_task.description}}</p>
                </b-card>
              </div>
            </b-card-group>
          </b-col>
        </b-row>
        <hr>
      </div>
      </b-container>
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
        newTask_form:{
          desc:'',
        },
        currentProject_id:'',
        today:'',
        currentTasks:[],
      }
    },
    computed:{
      todoTasks(id){
        console.log(id);
        const self=this;
        return self.currentTasks[id].filter(function(u) {
          return u.state===1;
        })
      },
      doingTasks(){
        return this.currentTasks.filter(function(u) {
          return u.state===2;
        })
      },
      doneTasks(){
        return this.currentTasks.filter(function(u) {
          return u.state===3;
        })
      },
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
            if (response.data.stories) {
              self.currentStories = response.data.stories;

            }

          })
          .catch(function (error) {
            console.log(error);
          })
      },
      getTasks() {
        const self = this;
        axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/tasks?isCurrent=true', {
          headers: {"auth": localStorage.getItem('auth_token')}
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") {
                console.log("Unauthorized user");
              }
            }
            else if (response.data.tasks) {
              //console.log(response.data.tasks);
             /* let result = response.data.tasks.reduce(function (r, a) {
                r[a.PBI_id] = r[a.PBI_id] || [];
                r[a.PBI_id].push(a);
                return r;
              }, Object.create(null));
              //console.log(result);

              self.currentTasks = Object.values(result);
              console.log(self.currentTasks);

              //self.currentTasks = result;
              //console.log(self.currentTasks);
              // self.currentTasks = JSON.parse(JSON.stringify(result));
              // console.log(self.currentTasks);*/
             self.currentTasks=response.data.tasks;
            }

          })
          .catch(function (error) {
            console.log(error);
          })
      },
      addTask(storyId)
      {
        const self = this;
        let config = {
          headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
        };
        let data = {
          description: this.newTask_form.desc, Project_id: this.currentProject_id, PBI_id: storyId ,
        };
        //console.log(data);
        axios.post(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.idProject + '/tasks?isCurrent=true', data, config)
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
              else if (response.data.error === "Unauthorized projects") console.log("Unauthorized projects");
              else if (response.data.error === "null") console.log("Null token");
              else console.log(error);
            }
            if (response.data.task) {
              self.currentTasks.push(response.data.task);
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      }

    },

    mounted(){
      this.currentProject_id=parseInt(this.$route.params.id);
      this.getSprintInfos();
      this.getSprintStories();
      this.getTasks();
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
