<template>
    <b-dropdown class="text-font" right>
        <template slot="button-content">
            <img src="@/assets/edit.png" class="btn-img" />
            Edit Sprint
        </template>
        <template>
            <b-form inline style="margin: 10px;" @submit="updateSprintInfo">
                <p>Sprint's Goal</p>
                <b-form-input
                    id="newGoal"
                    type="text"
                    style="margin-top: -10px;"
                    v-model="currentSprint.goal"
                    required
                ></b-form-input>
                <p style="margin-top: 10px;">Sprint's Plan</p>
                <b-form-input
                    id="newPlan"
                    type="text"
                    style="margin-top: -10px;"
                    v-model="currentSprint.plan"
                    required
                ></b-form-input>
                <p style="margin-top: 10px;">New Sprint's Deadline</p>
                <b-form-input
                    id="dateInput"
                    style="margin-top: -10px;"
                    v-model.trim="deadlineDate"
                    type="date"
                    :state="dateState"
                    required
                ></b-form-input>
                <b-form-invalid-feedback id="inputLiveFeedback">
                    Sprint's Deadline cannot be neither a past or current date,
                    nor a date after the project's deadline
                </b-form-invalid-feedback>
                <b-button
                    type="submit"
                    :disabled="dateState === false"
                    variant="success"
                    style="margin-top: 10px; width: 100%;"
                    >Save changes</b-button
                >
                <CurrentSprint :currentSprint="currentSprint" />
                <b-button
                    v-b-modal="'rm_sprint'"
                    variant="danger"
                    style="margin-top: 10px; width: 100%;"
                    >Delete Sprint</b-button
                >
                <DeleteSprint :currentSprint="currentSprint" />
            </b-form>
        </template>
    </b-dropdown>
</template>

<script>
import axios from 'axios'
import CurrentSprint from '@/components/sprintbacklog/editsprint/CurrentSprint.vue'
import DeleteSprint from '@/components/sprintbacklog/editsprint/DeleteSprint.vue'

export default {
    name: 'EditSprintForm',
    components: {
        CurrentSprint,
        DeleteSprint,
    },
    props: {
        currentSprint: Object,
    },
    data() {
        return {
            projectOverview: {
                project: {
                    id: 0,
                    deadlineDate: '',
                },
            },
            deadlineDate: '',
        }
    },
    computed: {
        dateState() {
            if (this.deadlineDate > this.projectOverview.project.deadlineDate) {
                return false
            }
            let mindate = new Date()
            let dd = ('0' + mindate.getDate()).slice(-2)
            let mm = ('0' + (mindate.getMonth() + 1)).slice(-2)
            let yyyy = mindate.getFullYear()
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
        updateSprintInfo(evt) {
            evt.preventDefault()
            const self = this
            this.currentSprint.deadlineDate = this.deadlineDate
            axios
                .put(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/sprints/' +
                        this.$route.params.sprintId,
                    this.currentSprint,
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
                        self.$emit('editSprint', response.data)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
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

.btn-img {
    width: 20px;
    margin-right: 5px;
    margin-bottom: 5px;
}
</style>
