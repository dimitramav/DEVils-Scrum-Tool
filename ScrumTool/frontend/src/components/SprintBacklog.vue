<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
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
    <!--<b-container class="bv-example-row bv-example-row-flex-cols">-->
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
          <draggable v-model="todoTasks[cur_story.idPBI]" @change="addToTodo" :move=onMove :options="{group:'UserStories'}">
            <transition-group name="markos1" class="list-group">
              <b-card-group v-for="cur_task in todoTasks[cur_story.idPBI]"  :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
                <div v-if="cur_task.state===1">
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
            </transition-group>
          </draggable>
        </b-col>
        <b-col>

          <draggable v-model="doingTasks[cur_story.idPBI]" @change="addToDoing" :move=onMove :options="{group:'UserStories'}">
            <transition-group name="markos" class="list-group">
              <b-card-group v-for="cur_task in doingTasks[cur_story.idPBI]"  :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">

                <div v-if="cur_task.state===2">
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
            </transition-group>
          </draggable>
        </b-col>
        <b-col class="w-25 bg-light">


          <draggable v-model="doneTasks[cur_story.idPBI]" @change="addToDone" :move=onMove :options="{group:'UserStories'}">
            <transition-group name="yolanda" class="list-group">

              <b-card-group v-for="cur_task in doneTasks[cur_story.idPBI]"  :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
                <div v-if="cur_task.state===3">
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
            </transition-group>
          </draggable>
        </b-col>
      </b-row>
      <hr>
    </div>
    <!--</b-container>-->
  </b-container>
</template>

<script>
  import axios from 'axios'
  import Navbar from "./Navbar.vue"
  import draggable from 'vuedraggable'

  export default {
    name: "SprintBacklog",
    components: {
      navbar: Navbar,
      draggable,
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
        todoTasks: [],
        doingTasks:[],
        doneTasks: [],
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
              console.log(response.data.tasks);

              response.data.tasks.forEach(function(element) {
                self.todoTasks[element.PBI_id]=[];
                self.doingTasks[element.PBI_id]=[];
                self.doneTasks[element.PBI_id]=[];
              });

              response.data.tasks.forEach(function(element) {
                switch(element.state) {
                  case 1:
                    self.todoTasks[element.PBI_id].push(element);
                    break;
                  case 2:
                    // if (typeof(self.doingTasks[element.PBI_id])=== "undefined") self.doingTasks[element.PBI_id] = [element];
                    self.doingTasks[element.PBI_id].push(element);
                    break;
                  case 3:
                    //if (typeof(self.doneTasks[element.PBI_id])=== "undefined") self.doneTasks[element.PBI_id] = [element];
                    self.doneTasks[element.PBI_id].push(element);
                    break;
                  default:
                    console.log ("Unknown value " + element.state);
                }
              });
              console.log(self.todoTasks);
              console.log(self.doingTasks);
              console.log(self.doneTasks);

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
              //self.currentTasks=response.data.tasks;
              //console.log(self.currentTasks);
            }

          })
          .catch(function (error) {
            console.log(error);
          })
      },
      addTask(storyId) {
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
              console.log(self.todoTasks);
              console.log(storyId);
              console.log(response.data.task);
              self.todoTasks[storyId].push(response.data.task);
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      },


      onMove ({relatedContext, draggedContext}) {
        //console.log (relatedContext);
        //console.log(draggedContext);

        draggedContext.element.state=null;

        //this.currentEpicId = relatedContext.element.Epic_id;
      },


      addToTodo(dragged) {
        console.log(dragged);
        if (dragged.added) {
          dragged.added.element.state = 1;
        }
      },
      addToDoing(dragged) {
        console.log(dragged);
        if (dragged.added) {
          dragged.added.element.state = 2;
        }
      },
      addToDone(dragged) {
        console.log(dragged);
        if (dragged.added) {
          dragged.added.element.state = 3;
        }
      },

    },

    mounted(){
      console.log("mphkaaaaa");
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

  .flip-list-move {
    transition: transform 0.5s;
  }

  .no-move {
    transition: transform 0s;
  }

  .ghost {
    opacity: .5;
    background: #C8EBFB;
  }

  .list-group {
    min-height: 100px;
  }

  .list-group-item {
    cursor: move;
  }

  .list-group-item i{
    cursor: pointer;
  }
</style>
