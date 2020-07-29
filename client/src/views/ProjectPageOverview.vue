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
                        isProductOwner() === true
                    "
                    :Project_id="parseInt(this.$route.params.id)"
                    :Project_title="projectOverview.project.title"
                    :isDone="projectOverview.project.isDone"
                    v-on:editProject="editProject"
                />
            </b-col>
        </b-row>
        <b-row class="line">.</b-row>
        <ActivateProject
            v-if="projectOverview.project.isDone === true"
            :isProductOwner="isProductOwner()"
        />
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
import Navbar from '@/components/navbar/Navbar.vue'
import EditProjectForm from '@/components/projectPageOverview/EditProjectForm'
import ActivateProject from '@/components/projectPageOverview/actions/ActivateProject'
import SprintInfo from '@/components/projectPageOverview/SprintInfo'
import Members from '@/components/projectPageOverview/Members'

export default {
    name: 'ProjectPageOverview',
    components: {
        Navbar,
        EditProjectForm,
        ActivateProject,
        SprintInfo,
        Members,
    },
    data() {
        return {
            projectOverview: {
                project: {
                    id: 0,
                    title: '',
                    isDone: false,
                    deadlineDate: '',
                },
                currentSprintId: 0,
                currentSprintNum: 0,
                currentSprintExpDate: '',
                currentSprintGoal: '',
                todo: 0,
                doing: 0,
                done: 0,
                issues: 0,
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
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id,
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
                        self.projectOverview = response.data
                        self.sprintDataLoaded = true
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
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/members',
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
                        /*self.$router.push({
                            path: '/unauthorized',
                        })*/
                    } else {
                        self.Team = response.data
                        self.teamDataLoaded = true
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
                    localStorage.setItem('projectRole', teamMember.role)
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
