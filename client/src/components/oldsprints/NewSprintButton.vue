<template>
    <div class="text-font">
        <b-jumbotron v-if="numOfSprints === 0">
            <h1>No Sprints in this Project</h1>
            <div
                v-if="
                    projectOverview.project.isDone === false &&
                    teamRole() != 'Developer'
                "
            >
                <b>Start a new sprint!</b><br /><br />
                <b-button
                    size="lg"
                    variant="success"
                    class="m-md-3"
                    :to="{
                        name: 'NewSprint',
                        params: { id: $route.params.id },
                    }"
                    >Create New Sprint</b-button
                >
            </div>
        </b-jumbotron>
        <b-row v-if="numOfSprints > 0">
            <b-col class="text-right">
                <b-button
                    v-if="
                        projectOverview.project.isDone === false &&
                        teamRole() != 'Developer'
                    "
                    size="lg"
                    variant="success"
                    class="m-md-3"
                    :to="{
                        name: 'NewSprint',
                        params: { id: $route.params.id },
                    }"
                >
                    Create New Sprint
                </b-button>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'NewSprintButton',
    props: {
        numOfSprints: Number,
    },
    data() {
        return {
            projectOverview: {
                project: {
                    id: 0,
                    isDone: false,
                },
            },
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
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        teamRole() {
            return localStorage.getItem('teamRole')
        },
    },
    mounted() {
        this.getSprintInfo()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}
</style>
