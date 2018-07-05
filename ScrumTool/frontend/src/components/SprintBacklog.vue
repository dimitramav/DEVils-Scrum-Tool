<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="true"></navbar>
    <br>
    <!--Sprint Infos-->
    <b-row>
      <b-col class="center">
        <h3 class="ptitle">Sprint #{{currentSprint.numSprint}}</h3>
      </b-col>
    </b-row>
    <hr>
    <b-row>
      <b-col>
        <h5 class="pcsprint"><i class="text-muted pcsprint">Sprint Goal: </i>{{currentSprint.goal}}</h5>
      </b-col>
      <b-col>
        <h5 class="pcsprint"><i class="text-muted pcsprint">Sprint Plan: </i>{{currentSprint.plan}}</h5>
      </b-col>
      <b-col>
        <h5 class="pcsprint"><i class="text-muted">Deadline Date: </i>{{currentSprint.deadlineDate}} </h5>
      </b-col>
    </b-row>
    <hr style="margin-bottom:0px">
    <!--board-->
    <!--<b-container class="bv-example-row bv-example-row-flex-cols">-->
    <b-row>
      <p></p>
    </b-row>
    <b-row>
      <b-col>
        <h4 class="pcsprint">User Stories</h4>
      </b-col>
      <b-col>
        <h4 class="pcsprint">TO DO</h4>
      </b-col>
      <b-col>
        <h4 class="pcsprint">DOING</h4>
      </b-col>
      <b-col>
        <h4 class="pcsprint">DONE</h4>
      </b-col>
    </b-row>
    <div v-for="cur_story in currentStories" :key="cur_story.idPBI" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
      <b-row>
        <b-col>
          <b-card :title="cur_story.title" img-top tag="article" class="mb-2 pcsprint" >

            <div slot="header"  >
              <b-row>

                <!--New Task-->
                <div>
                  <b-btn v-b-modal="'new_task'+cur_story.idPBI" class="sm">Add Task</b-btn>
                  <b-modal @ok="addTask(cur_story.idPBI)" class="text-left" :id="'new_task'+cur_story.idPBI" title="Add new task">
                    <b-form>
                      <b-form-group label="Description:" :label-for="'addTask'+cur_story.idPBI">
                        <b-form-input :id="'addTask'+cur_story.idPBI"
                                      type="text"
                                      v-model="newTask_desc"
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
          <draggable v-model="todoTasks" @change="addToTodo" :move=onMove :options="{group:'UserStories'}">
            <transition-group name="markos1" class="list-group">
              <b-card-group v-for="cur_task in todoTasks" :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
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
            </transition-group>
          </draggable>

          <!--<tasklane v-if="gotTasks" :state="1" :list="todoTasks" :story_id="cur_story.idPBI"> </tasklane>-->

        </b-col>

        <b-col>
          <draggable v-model="doingTasks" @change="addToDoing" :move=onMove :options="{group:'UserStories'}">
            <transition-group name="markos" class="list-group">
              <b-card-group v-for="cur_task in doingTasks"  :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">

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
            </transition-group>
          </draggable>

          <!--<tasklane v-if="gotTasks" :state="2" :list="doingTasks" :story_id="cur_story.idPBI"> </tasklane>-->


        </b-col>
        <b-col class="w-25 bg-light">


          <draggable v-model="doneTasks" @change="addToDone" :move=onMove :options="{group:'UserStories'}">
            <transition-group name="yolanda" class="list-group">

              <b-card-group v-for="cur_task in doneTasks"  :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
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
            </transition-group>
          </draggable>

          <!--<tasklane v-if="gotTasks" :state="3" :list="doneTasks" :story_id="cur_story.idPBI"> </tasklane>-->


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
  import TaskLane from "./TaskLane.vue"

  export default {
    name: "SprintBacklog",
    components: {
      navbar: Navbar,
      draggable,
      tasklane: TaskLane,
    },
    data() {
      return {
        currentSprint:[],
        currentStories:[],
        newTask_desc: '',
        currentProject_id:'',
        todoTasks: [],
        doingTasks:[],
        doneTasks: [],
        gotTasks: false,
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
                switch(element.state) {
                  case 1:
                    self.todoTasks.push(element);
                    break;
                  case 2:
                    // if (typeof(self.doingTasks[element.PBI_id])=== "undefined") self.doingTasks[element.PBI_id] = [element];
                    self.doingTasks.push(element);
                    break;
                  case 3:
                    //if (typeof(self.doneTasks[element.PBI_id])=== "undefined") self.doneTasks[element.PBI_id] = [element];
                    self.doneTasks.push(element);
                    break;
                  default:
                    console.log ("Unknown value " + element.state);
                }
              });
              console.log(self.todoTasks);
              console.log(self.doingTasks);
              console.log(self.doneTasks);
              self.gotTasks=true;
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
          description: this.newTask_desc, Project_id: this.currentProject_id, PBI_id: storyId ,
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
              self.todoTasks.push(response.data.task);
              self.newTask_desc='';
            }
          })
          .catch(function (error) {
            console.log(error);
          })
      },

      onMove ({relatedContext, draggedContext}) {
        draggedContext.element.state=null;
      },

      addToTodo(dragged) {
        console.log(dragged);
        if (dragged.added) {
          dragged.added.element.state = 1;
          this.updateDB(1, dragged.added.element);
        }

      },

      addToDoing(dragged) {
        console.log(dragged);
        if (dragged.added) {
          dragged.added.element.state = 2;
          this.updateDB(2, dragged.added.element);

        }
      },
      addToDone(dragged) {
        console.log(dragged);
        if (dragged.added) {
          dragged.added.element.state = 3;
          this.updateDB(3, dragged.added.element);

        }
      },

      updateDB(state, element) {
        let config = {
          headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
        };
        let data = {
          PBI_id: element.PBI_id,
          description: element.description,
          state: state,
          idTask: element.idTask,
        };
        //console.log(data);

        axios.put(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/tasks', data, config)
          .then(function (response) {
            //console.log(response);
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
              else if (response.data.error === "Unauthorized projects") console.log("Unauthorized projects");
              else if (response.data.error === "null") console.log("Null token");
              else console.log(error);
            }
            else if (response.data.task) {
              console.log(response.data.task);
              //self.$emit('edit_pbi', self.idPBI, response.data.results.title, response.data.results.description, response.data.results.priority, self.epicId);
            }
            else console.log ("Unresolved response: " + response);
          })
          .catch(function (error) {
            console.log(error);
          });
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
