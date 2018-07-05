<template>


  <draggable :value="listJson" @change="addToTaskLane" :move=onMove :options="{group:'UserStories'}">

    <transition-group name="markos" class="list-group">
      <b-card-group v-for="cur_task in list" :key="cur_task.idTask" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">

        <div v-if="cur_task.state===state && cur_task.PBI_id===story_id">
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
</template>

<script>
  import draggable from 'vuedraggable'

  export default {

    name: "TaskLane",
    components: {
      draggable,
    },
    props: {
      list: Array,
      story_id: Number,
      state: Number,
    },

    // data: function () {
    //   return {
    //     mutableList: JSON.parse(this.list),
    //   }
    // },

    // created() {
    //   this.mutableList = this.list;
    // },

    computed: {
      listJson: function(){
        console.log("mphka sto computed");
        return this.list;
      }
    },

    methods: {
      onMove ({relatedContext, draggedContext}) {
        //console.log (relatedContext);
        //console.log(draggedContext);

        draggedContext.element.state=null;

        //this.currentEpicId = relatedContext.element.Epic_id;
      },


      addToTaskLane(dragged) {
        console.log(dragged);
        if (dragged.added) {
          dragged.added.element.state = this.state;
        }
      },
    },
  }
</script>

<style scoped>

</style>
