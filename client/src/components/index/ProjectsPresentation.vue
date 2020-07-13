<template>
    <div v-show="totalNumOfProjects">
        <b-row v-if="isDone === false" class="text-font">
            <b-col class="text-left" style="padding-top: 10px;">
                <h2>Current Projects</h2>
            </b-col>
            <b-col class="text-right">
                <NewProjectForm v-on:newProject="newProjectCreated" />
            </b-col>
        </b-row>
        <b-row v-else class="text-font">
            <b-col class="text-left">
                <h2>Done Projects</h2>
            </b-col>
        </b-row>
        <b-row class="line">.</b-row>

        <b-row>
            <b-card-group
                v-for="project in Projects"
                :key="project.id"
                class="mb-2 presentation text-font"
            >
                <b-card
                    style="max-width: 15rem;"
                    img-src="https://picsum.photos/600/300/?image=527"
                    img-top
                >
                    <h3>{{ project.title }}</h3>
                    <p class="card-text">
                        Deadline: {{ project.deadlineDate }}
                    </p>
                    <router-link
                        tag="b-button"
                        :to="{
                            name: 'ProjectPageOverview',
                            params: {
                                id: project.id,
                            },
                        }"
                        >Proceed
                    </router-link>
                </b-card>
            </b-card-group>
        </b-row>
        <br />
        <b-row>
            <b-col></b-col>
            <b-col></b-col>
            <b-col></b-col>
            <b-col>
                <b-pagination
                    v-if="totalNumOfProjects"
                    size="md"
                    :total-rows="totalNumOfProjects"
                    v-model="currentPage"
                    :per-page="recordsPerPage"
                >
                </b-pagination>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from 'axios'
import NewProjectForm from '@/components/index/NewProjectForm.vue'

export default {
    name: 'ProjectsPresentation',
    components: {
        NewProjectForm,
    },
    props: {
        isDone: Boolean,
    },
    data() {
        return {
            Projects: [],
            totalNumOfProjects: 0,
            currentPage: 1,
            recordsPerPage: 4,
        }
    },
    methods: {
        newProjectCreated(responseDataResults) {
            this.Projects.push(responseDataResults)
            this.getNumOfProjects()
        },
        getProjects() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects?isDone=' +
                        self.isDone.toString() +
                        '&recordsPerPage=' +
                        self.recordsPerPage +
                        '&currentPage=' +
                        self.currentPage,
                    {
                        headers: {
                            Authorization:
                                'Bearer ' + localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.serverErrorMessage) {
                        console.log(response.data.serverErrorMessage)
                    } else {
                        self.Projects = response.data
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        getNumOfProjects() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/numOfProjects?isDone=' +
                        self.isDone.toString(),
                    {
                        headers: {
                            Authorization:
                                'Bearer ' + localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.serverErrorMessage) {
                        console.log(response.data.serverErrorMessage)
                    } else {
                        self.totalNumOfProjects = response.data
                        //console.log(self.totalNumOfProjects)
                        self.$emit('total', response.data, self.isDone)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        if (
            localStorage.getItem('auth_token') === 'null' ||
            localStorage.getItem('userId') === 'null'
        )
            return
        this.getNumOfProjects()
        this.getProjects()
    },
    watch: {
        currentPage: function () {
            this.getProjects()
        },
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.line {
    width: 102%;
    font-size: 1px;
    line-height: 2px;
    background-color: #e6edf2;
    margin-top: 10px;
    margin-bottom: 10px;
}

.presentation {
    padding-top: 10px;
    margin-bottom: 10px;
    padding-left: 10px;
}
</style>
