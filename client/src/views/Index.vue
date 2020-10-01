<template>
    <div>
        <!--DEFAULT HOMEPAGE-->
        <template v-if="notLoggedIn() === true">
            <b-container class="Navigation" fluid>
                <Navbar :logout_prop="true" />
                <IndexDefault />
            </b-container>
        </template>
        <!--USER HOMEPAGE-->
        <div v-else>
            <b-container class="Navigation" fluid>
                <Navbar :logout_prop="false" :dashboard="false" />
                <b-container>
                    <br />
                    <!--Show jumbotron if no project at all-->
                    <div v-show="!totalNumOfCurProjects">
                        <b-jumbotron class="text-font">
                            <h1>Welcome to ScrumTool!</h1>
                            <b>No projects yet. Start creating!</b>
                            <br /><br />
                            <NewProjectForm
                                v-on:newProject="firstProjectCreated"
                            />
                        </b-jumbotron>
                    </div>
                    <!--Current Projects-->
                    <ProjectsPresentation
                        :isDone="false"
                        v-on:total="updateNumOfProjects"
                    />
                    <!--Done Projects-->
                    <ProjectsPresentation
                        :isDone="true"
                        v-on:total="updateNumOfProjects"
                    />
                </b-container>
            </b-container>
        </div>
    </div>
</template>

<script>
import Navbar from '@/components/navbar/Navbar.vue'
import IndexDefault from '@/components/index/IndexDefault.vue'
import NewProjectForm from '@/components/index/NewProjectForm.vue'
import ProjectsPresentation from '@/components/index/ProjectsPresentation.vue'

export default {
    components: {
        Navbar,
        IndexDefault,
        NewProjectForm,
        ProjectsPresentation,
    },
    data() {
        return {
            totalNumOfCurProjects: 0,
        }
    },
    methods: {
        notLoggedIn() {
            if (
                localStorage.getItem('auth_token') === null ||
                localStorage.getItem('auth_token') === 'null'
            ) {
                localStorage.setItem('userId', 'null')
                localStorage.setItem('username', 'null')
                localStorage.setItem('auth_token', 'null')
                return true
            }
            return false
        },
        firstProjectCreated() {
            location.reload()
        },
        updateNumOfProjects(numOfProjects, isDone) {
            if (!isDone) {
                this.totalNumOfCurProjects = numOfProjects
            }
        },
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.Navigation {
    position: absolute;
}

.container-fluid {
    padding-right: 0;
    padding-left: 0;
    top: 0;
    left: 0;
}
</style>
