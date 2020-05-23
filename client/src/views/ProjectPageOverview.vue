<template>
    <b-container fluid>
        <Navbar :logout_prop="false" />
        <b-row class="text-font" id="title">
            <b-col style="font-weight: bold;">
                <h2>{{ projectOverview.project.title }}</h2>
            </b-col>
            <b-col style="font-weight: bold;">
                <p>Project End: {{ projectOverview.project.deadlineDate }}</p>
            </b-col>
            <b-col>
                <EditProjectForm
                    v-if="
                        sprintDataLoaded === true &&
                        teamDataLoaded === true &&
                        projectOverview.project.isDone === false &&
                        isProductOwner() === true
                    "
                    :Project_id="parseInt(this.$route.params.id)"
                    :Project_title="projectOverview.project.title"
                    v-on:editProject="editProject"
                />
            </b-col>
        </b-row>
        <b-row class="line">.</b-row>
        <!--{{projectOverview}}-->
        <b-row>
            <b-col>
                <SprintInfo
                    v-if="sprintDataLoaded === true"
                    :projectOverview="projectOverview"
                />
            </b-col>
            <b-col>
                <Members
                    v-if="sprintDataLoaded === true && teamDataLoaded === true"
                    :projectOverview="projectOverview"
                    :Team="Team"
                    :isProductOwner="isProductOwner()"
                />
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import axios from 'axios'
import config from '@/router/auth.js'
import Navbar from '@/components/navbar/Navbar.vue'
import EditProjectForm from '@/components/projectPageOverview/EditProjectForm'
import SprintInfo from '@/components/projectPageOverview/SprintInfo'
import Members from '@/components/projectPageOverview/Members'

export default {
    name: 'ProjectPageOverview',
    components: {
        Navbar,
        EditProjectForm,
        SprintInfo,
        Members,
    },
    data() {
        return {
            projectOverview: {
                project: {
                    idProject: 0,
                    title: '',
                    isDone: false,
                    deadlineDate: '',
                },
                currentSprintId: 0,
                currentSprintNum: 0,
                currentSprintExpDate: '',
                todo: 0,
                doing: 0,
                done: 0,
                todoIssues: 0,
                doingIssues: 0,
                doneIssues: 0,
                showAlert: false,
            },
            sprintDataLoaded: false,
            teamDataLoaded: false,
            Team: [],
        }
    },

    methods: {
        getSprintInfo() {
            const self = this
            axios
                .get(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id,
                    config
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.results) {
                        self.projectOverview = response.data.results
                        self.sprintDataLoaded = true
                        console.log('Got the results')
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        getMembers() {
            const self = this
            axios
                .get(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/members',
                    config
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.results) {
                        self.Team = response.data.results
                        self.teamDataLoaded = true
                        console.log('Got the members')
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        isProductOwner() {
            let loggedUserID = localStorage.getItem('userId')
            //console.log(loggedUserID);
            for (let teamMember of this.Team) {
                if (teamMember.idUser == loggedUserID) {
                    if (teamMember.role === 'Product Owner') {
                        return true
                    } else {
                        return false
                    }
                }
            }
            return false
        },
        editProject(updatedProject) {
            this.projectOverview.project = updatedProject
        },
    },
    mounted() {
        this.getSprintInfo()
        this.getMembers()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.line {
    font-size: 1px;
    line-height: 2px;
    background-color: #e6edf2;
    margin-top: 9px;
    margin-bottom: 9px;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}
</style>
