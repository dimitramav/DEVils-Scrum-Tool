<template>
    <b-jumbotron style="background-color: #f7f7f7;" class="text-font">
        <div v-if="projectOverview.project.isDone === true">
            <b-row>
                <p class="text-font done">
                    This Project has finished... Go back home and create a new
                    one!
                </p>
            </b-row>
            <b-button class="text-font" href="/">
                Home Page
            </b-button>
        </div>
        <div v-else id="currentProject">
            <div v-if="(projectOverview.currentSprintNum === 0)">
                <b-button
                    variant="primary"
                    :to="{
                        name: 'NewSprint',
                        params: {
                            id: $route.params.id,
                            newSprintNum: 1,
                        },
                    }"
                >
                    Create New Sprint
                </b-button>
            </div>
            <div v-else id="currentSprint">
                <b-row style="margin-top: -5%;">
                    <h2 v-if="(diffDays > -1)">
                        Current sprint #{{
                            projectOverview.currentSprintNum
                        }}
                        ({{ projectOverview.currentSprintGoal }})
                    </h2>
                    <h2 v-else>
                        Last sprint #{{ projectOverview.currentSprintNum }} ({{
                            projectOverview.currentSprintGoal
                        }})
                    </h2>
                </b-row>
                <b-row style="margin-top: 20px;">
                    <b-col>
                        <b-card title="TODO">
                            <p class="card-text">{{ projectOverview.todo }}</p>
                        </b-card>
                    </b-col>
                    <b-col>
                        <b-card title="DOING">
                            <p class="card-text">{{ projectOverview.doing }}</p>
                        </b-card>
                    </b-col>
                    <b-col>
                        <b-card title="DONE">
                            <p class="card-text">{{ projectOverview.done }}</p>
                        </b-card>
                    </b-col>
                </b-row>
                <b-row style="margin-top: 30px;">
                    <b-col class="text-left">
                        <h5>Progress</h5>
                        <b-progress
                            :value="donePercentage"
                            :max="max"
                            class="mb-3"
                        ></b-progress>
                    </b-col>
                    <b-col md="4" class="py-4">
                        <b-btn id="exBtn1" variant="outline-danger">
                            Issues</b-btn
                        >
                        <b-tooltip target="exBtn1"> {{ issues }} </b-tooltip>
                    </b-col>
                </b-row>
                <b-row style="margin-bottom: 30px;">
                    <b-col>
                        <p style="font-weight: bold;">
                            Sprint End: {{ sdeadline }}
                        </p>
                    </b-col>
                    <b-col>
                        <p v-if="(diffDays > -1)" style="font-weight: bold;">
                            Days Remaining: {{ diffDays }}
                        </p>
                        <p v-else style="font-weight: bold;">
                            Sprint has Ended!
                        </p>
                    </b-col>
                </b-row>
                <div v-if="(diffDays < 0)">
                    <b-row align-h="center" style="margin-bottom: 20px;">
                        <b-button
                            variant="outline-secondary"
                            :to="{
                                name: 'SprintBacklog',
                                params: { id: $route.params.id },
                            }"
                        >
                            Go to Sprint Page
                        </b-button>
                    </b-row>
                    <b-row align-h="center">
                        <b-button
                            variant="primary"
                            :to="{
                                name: 'NewSprint',
                                params: {
                                    id: $route.params.id,
                                    newSprintNum:
                                        projectOverview.currentSprintNum + 1,
                                },
                            }"
                        >
                            Create New Sprint!
                        </b-button>
                    </b-row>
                </div>
                <div v-else>
                    <b-button
                        variant="success"
                        :to="{
                            name: 'SprintBacklog',
                            params: { id: $route.params.id },
                        }"
                    >
                        Go to Sprint Page
                    </b-button>
                </div>
            </div>
        </div>
    </b-jumbotron>
</template>

<script>
export default {
    name: 'SprintInfo',
    props: {
        projectOverview: Object,
    },
    data() {
        return {
            max: 100,
            donePercentage: 0,
            sdeadline: null,
            diffDays: 0,
            issues: 0,
        }
    },
    methods: {
        calcSprintParameters() {
            //console.log(this.projectOverview.project.title)
            let totalTasks =
                this.projectOverview.todo +
                this.projectOverview.doing +
                this.projectOverview.done
            this.donePercentage = (this.projectOverview.done / totalTasks) * 100
            this.issues =
                this.projectOverview.todoIssues +
                this.projectOverview.doingIssues +
                this.projectOverview.doneIssues
            this.calcDeadline()
        },
        calcDeadline() {
            let today = new Date()
            let d = new Date(this.projectOverview.currentSprintExpDate)
            //console.log(this.projectOverview.currentSprintExpDate);
            let oneDay = 24 * 60 * 60 * 1000
            this.diffDays = Math.floor((d.getTime() - today.getTime()) / oneDay)
            this.diffDays = this.diffDays + 1 // The last day counts as one
            //console.log(this.diffDays)
            this.sdeadline = this.projectOverview.currentSprintExpDate
        },
    },
    mounted() {
        this.calcSprintParameters()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.done {
    text-align: center;
    font-size: 180%;
    color: #264d73;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}
</style>
