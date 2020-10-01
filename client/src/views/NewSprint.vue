<template>
    <b-container fluid>
        <Navbar :dashboard="true" :logout_prop="false" />
        <b-row class="text-font total-margin">
            <b-col align-self="start">
                <h2>Create New Sprint</h2>
            </b-col>
        </b-row>
        <b-row class="text-font total-margin">
            <b-col class="text-center">
                <NewSprintForm
                    v-on:plan="editPlan"
                    v-on:goal="editGoal"
                    v-on:deadlineDate="editDeadlineDate"
                />
            </b-col>
            <b-col class="text-center">
                <LoadEpics v-on:stories_options="editStoriesOptions" />
            </b-col>
            <b-col class="text-right">
                <SelectStories
                    :stories_options="stories_options"
                    v-on:selected_stories="editSelectedStories"
                />
            </b-col>
        </b-row>
        <b-row style="margin-bottom: 45px;">
            <b-col class="text-center text-font">
                <SubmitButton
                    :sprint="sprint"
                    :selected_stories="selected_stories"
                    :validDate="validDate"
                />
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import Navbar from '@/components/navbar/Navbar.vue'
import NewSprintForm from '@/components/newsprint/NewSprintForm.vue'
import LoadEpics from '@/components/newsprint/LoadEpics.vue'
import SelectStories from '@/components/newsprint/SelectStories.vue'
import SubmitButton from '@/components/newsprint/SubmitButton.vue'

export default {
    name: 'NewSprint',
    components: {
        Navbar,
        NewSprintForm,
        LoadEpics,
        SelectStories,
        SubmitButton,
    },
    data() {
        return {
            sprint: {
                deadlineDate: '',
                goal: '',
                plan: '',
                isCurrent: true,
                numSprint: 0,
                project: {
                    id: 0,
                },
            },
            validDate: false,
            stories_options: [],
            selected_stories: [],
        }
    },
    methods: {
        editPlan(plan) {
            this.sprint.plan = plan
        },
        editGoal(goal) {
            this.sprint.goal = goal
        },
        editDeadlineDate(deadlineDate, dateState) {
            this.sprint.deadlineDate = deadlineDate
            if (deadlineDate == '') {
                this.validDate = false
            } else this.validDate = dateState
        },
        editStoriesOptions(loadedStories) {
            this.stories_options = loadedStories
        },
        editSelectedStories(selectedStories) {
            this.selected_stories = selectedStories
        },
    },
    mounted() {
        this.sprint.project.id = parseInt(this.$route.params.id)
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.total-margin {
    margin-top: 15px;
    margin-bottom: 35px;
    padding-right: 30px;
    padding-left: 30px;
}
</style>
