<template>
    <b-form style="margin-bottom: 15px;" class="text-font">
        <label>Plan:</label>
        <b-form-input
            id="planInput"
            v-model="plan"
            type="text"
            @change="editPlan"
            placeholder="Type the project's plan"
            required
        ></b-form-input>
        <label style="margin-top: 15px;">Goal:</label>
        <b-form-input
            id="goalInput"
            v-model="goal"
            type="text"
            @change="editGoal"
            placeholder="Type the project's goal"
        ></b-form-input>
        <label style="margin-top: 15px;">Deadline Date:</label>
        <b-form-input
            id="inputLive"
            v-model.trim="deadlineDate"
            type="date"
            @change="editDeadlineDate"
            :state="dateState"
        ></b-form-input>
        <b-form-invalid-feedback id="inputLiveFeedback">
            Sprint's Deadline cannot be neither a past or current date, nor a
            date after the project's deadline
        </b-form-invalid-feedback>
    </b-form>
</template>

<script>
import axios from 'axios'
import config from '@/router/auth.js'

export default {
    name: 'NewSprintForm',
    data() {
        return {
            projectOverview: {
                project: {
                    idProject: 0,
                    deadlineDate: '',
                },
            },
            plan: '',
            goal: '',
            deadlineDate: '',
        }
    },
    computed: {
        dateState() {
            if (this.deadlineDate > this.projectOverview.project.deadlineDate) {
                return false
            }
            var mindate = new Date()
            var dd = ('0' + mindate.getDate()).slice(-2)
            var mm = ('0' + (mindate.getMonth() + 1)).slice(-2)
            var yyyy = mindate.getFullYear()
            mindate = yyyy + '-' + mm + '-' + dd
            if (this.deadlineDate === '') return null
            return this.deadlineDate > mindate
        },
    },
    methods: {
        getProjectInfo() {
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
                        let d = self.projectOverview.project.deadlineDate
                        let prodate = new Date(d)
                        let dd = ('0' + prodate.getDate()).slice(-2)
                        let mm = ('0' + (prodate.getMonth() + 1)).slice(-2)
                        let yyyy = prodate.getFullYear()
                        prodate = yyyy + '-' + mm + '-' + dd
                        self.projectOverview.project.deadlineDate = prodate
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        editPlan() {
            this.$emit('plan', this.plan)
        },
        editGoal() {
            this.$emit('goal', this.goal)
        },
        editDeadlineDate() {
            this.$emit('deadlineDate', this.deadlineDate, this.dateState)
        },
    },
    mounted() {
        this.getProjectInfo()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}
</style>
