<template>
    <draggable
        v-model="tasks[typeofTasks]"
        @change="onChange"
        :move="onMove"
        group="Tasks"
    >
        <transition-group name="taskCards" class="list-group tasks-field">
            <b-card-group
                v-for="cur_task in tasks[typeofTasks]"
                :key="cur_task.id"
                class="tasks-card-group text-font"
                deck
            >
                <div v-if="cur_task.storyId === currentStoryId">
                    <TaskCard
                        v-on:editTask="editTask"
                        :cur_task="cur_task"
                        :tasks="tasks"
                        :typeofTasks="typeofTasks"
                    />
                </div>
            </b-card-group>
        </transition-group>
    </draggable>
</template>

<script>
import axios from 'axios'
import draggable from 'vuedraggable'
import TaskCard from '@/components/sprintbacklog/tasks/TaskCard.vue'

export default {
    name: 'TaskGroup',
    components: {
        draggable,
        TaskCard,
    },
    props: {
        currentStoryId: Number,
        tasks: Array,
        typeofTasks: Number,
        currentTaskState: Number,
    },
    data() {
        return {
            taskList: [],
        }
    },
    methods: {
        editTask(editedTasks) {
            this.tasks = editedTasks
            this.$emit('editTask', this.tasks)
        },
        onMove({ relatedContext, draggedContext }) {
            //draggedContext.element.state = null
            console.log('On Move', relatedContext.element.state, draggedContext)
            let newCurrentTaskState = relatedContext.element.state
            this.$emit('newCurrentTaskState', newCurrentTaskState)
        },
        onChange(dragged) {
            console.log('Dragged', dragged)
            //const self = this
            if (dragged.added) {
                let taskItem = this.tasks[this.currentTaskState]
                if (taskItem.length === 2) {
                    if (taskItem[0].id === -1) {
                        taskItem.splice(0, 1)
                    } else if (taskItem[1].id === -1) {
                        taskItem.splice(1, 1)
                    }
                }
                dragged.added.element.state = this.currentTaskState
                axios
                    .put(
                        this.$url +
                            '/users/' +
                            localStorage.getItem('userId') +
                            '/projects/' +
                            this.$route.params.id +
                            '/pbis/' +
                            this.currentStoryId +
                            '/tasks',
                        dragged.added.element,
                        {
                            headers: {
                                Authorization:
                                    'Bearer ' +
                                    localStorage.getItem('auth_token'),
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                    .then(function (response) {
                        if (response.data.serverErrorMessage) {
                            console.log(response.data.serverErrorMessage)
                        } else {
                            /*self.tasks[self.currentTaskState][
                                dragged.added.newIndex
                            ].state = self.currentTaskState*/
                            //self.$emit('dragTask', response.data.task)
                        }
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            } else if (dragged.removed) {
                //let state = dragged.removed.element.state
                let state = this.typeofTasks
                console.log(this.tasks[state].length, state)
                if (this.tasks[this.typeofTasks].length === 0) {
                    let emptyData = [
                        {
                            state: this.typeofTasks, // If now task category is empty
                            id: -1, // Update its typeofDo task array
                        },
                    ]
                    this.$set(this.tasks, this.typeofTasks, emptyData)
                }
            }
            this.$emit('editTask', this.tasks)
        },
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.tasks-card-group {
    margin: auto;
}

.tasks-field {
    min-height: 150px;
    padding-top: 10px;
    padding-bottom: 5px;
}
</style>
