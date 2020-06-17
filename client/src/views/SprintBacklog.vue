<template>
    <b-container fluid>
        <Navbar :dashboard="true" :logout_prop="false" />
        <!--Sprint Infos-->
        <SprintHeader />
        <!--board-->
        <LoadStories v-on:getSprintStories="getSprintStories" ref="stories" />
        <hr />
        <div v-for="cur_story in currentStories" :key="cur_story.idPBI" deck>
            <b-row>
                <b-col>
                    <UserStoryCard
                        v-on:addTask="addTask"
                        v-on:removeStoryAndTasks="removeStoryAndTasks"
                        :story="cur_story"
                    />
                </b-col>
                <b-col class="bg-light">
                    <TaskGroup
                        v-on:newCurrentTaskState="newCurrentTaskState"
                        v-on:editTask="editTask"
                        :currentStoryId="cur_story.idPBI"
                        :tasks="tasks"
                        :typeofTasks="1"
                        :currentTaskState="currentTaskState"
                    />
                </b-col>
                <b-col>
                    <TaskGroup
                        v-on:newCurrentTaskState="newCurrentTaskState"
                        v-on:editTask="editTask"
                        :currentStoryId="cur_story.idPBI"
                        :tasks="tasks"
                        :typeofTasks="2"
                        :currentTaskState="currentTaskState"
                    />
                </b-col>
                <b-col class="bg-light">
                    <TaskGroup
                        v-on:newCurrentTaskState="newCurrentTaskState"
                        v-on:editTask="editTask"
                        :currentStoryId="cur_story.idPBI"
                        :tasks="tasks"
                        :typeofTasks="3"
                        :currentTaskState="currentTaskState"
                    />
                </b-col>
            </b-row>
            <hr />
        </div>
        <AddStories :currentStories="currentStories" @addStories="addStories" />
    </b-container>
</template>

<script>
import axios from 'axios'
import Navbar from '@/components/navbar/Navbar.vue'
import SprintHeader from '@/components/sprintbacklog/SprintHeader.vue'
import LoadStories from '@/components/sprintbacklog/stories/LoadStories.vue'
import UserStoryCard from '@/components/sprintbacklog/stories/UserStoryCard.vue'
import TaskGroup from '@/components/sprintbacklog/tasks/TaskGroup.vue'
import AddStories from '@/components/sprintbacklog/stories/AddStories.vue'

export default {
    name: 'SprintBacklog',
    components: {
        Navbar,
        SprintHeader,
        LoadStories,
        UserStoryCard,
        TaskGroup,
        AddStories,
    },
    data() {
        return {
            currentStories: [],
            tasks: [[], []],
            currentTaskState: -1,
        }
    },
    methods: {
        getSprintStories(loadedStories) {
            this.currentStories = loadedStories
        },
        getTasks() {
            const self = this
            axios
                .get(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/tasks?sprintId=' +
                        this.$route.params.sprintId,
                    {
                        headers: {
                            auth: localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.tasks) {
                        //self.tasks = response.data.tasks
                        let tempTodo = []
                        let tempDoing = []
                        let tempDone = []
                        response.data.tasks.forEach(function (element) {
                            switch (element.state) {
                                case 1:
                                    tempTodo.push(element)
                                    break
                                case 2:
                                    tempDoing.push(element)
                                    break
                                case 3:
                                    tempDone.push(element)
                                    break
                                default:
                                    console.log('Unknown value', element.state)
                            }
                        })
                        if (tempTodo.length > 0) {
                            self.$set(self.tasks, 1, tempTodo)
                        } else {
                            let emptyData = [
                                {
                                    state: 1,
                                    idTask: -1,
                                },
                            ]
                            self.$set(self.tasks, 1, emptyData)
                        }
                        if (tempDoing.length > 0) {
                            self.$set(self.tasks, 2, tempDoing)
                        } else {
                            let emptyData = [
                                {
                                    state: 2,
                                    idTask: -1,
                                },
                            ]
                            self.$set(self.tasks, 2, emptyData)
                        }
                        if (tempDone.length > 0) {
                            self.$set(self.tasks, 3, tempDone)
                        } else {
                            let emptyData = [
                                {
                                    state: 3,
                                    idTask: -1,
                                },
                            ]
                            self.$set(self.tasks, 3, emptyData)
                        }
                        console.log(self.tasks)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        addTask(data) {
            //this.tasks.push(newTask)
            if (this.tasks[data.state]) {
                if (
                    this.tasks[data.state].length === 1 &&
                    this.tasks[data.state][0].idTask === -1
                ) {
                    this.tasks[data.state].splice(0, 1, data)
                } else this.tasks[data.state].push(data)
            }
        },
        removeStoryAndTasks(storyId) {
            let i = this.currentStories.findIndex((o) => o.idPBI === storyId)
            if (i != -1) this.currentStories.splice(i, 1)
            this.tasks = [[], []]
            this.getTasks()
        },
        addStories() {
            this.currentStories = []
            this.tasks = [[], []]
            //this.getSprintStories()
            this.$refs.stories.getSprintStories()
            this.getTasks()
        },
        newCurrentTaskState(updatedCurrentTaskState) {
            this.currentTaskState = updatedCurrentTaskState
        },
        editTask(taskList) {
            this.tasks = taskList
        },
    },
    mounted() {
        //this.getSprintStories()
        this.getTasks()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}
</style>
