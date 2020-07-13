<template>
    <b-dropdown
        id="ddown1"
        variant="info"
        size="lg"
        text="Create a New Project"
        class="m-md-2 text-font"
    >
        <b-form style="margin: 10px;" @submit="newProject">
            <h4 style="margin-top: -13px;">Title:</h4>
            <label class="sr-only" for="newProjectName"></label>
            <b-form-input
                id="newProjectName"
                placeholder="New Project's Name"
                v-model="newTitle"
                required
                style="margin-top: -7px;"
            />
            <h5 style="margin-top: 5px;">Deadline:</h5>
            <label class="sr-only" for="newProjectDate"></label>
            <div>
                <b-form-input
                    id="inputLive"
                    v-model.trim="deadlineDate"
                    type="date"
                    :state="dateState"
                    required
                ></b-form-input>
                <b-form-invalid-feedback id="inputLiveFeedback">
                    Project's Deadline cannot be a past or current date.
                </b-form-invalid-feedback>
            </div>
            <b-button
                variant="success"
                size="lg"
                type="submit"
                :disabled="dateState === false"
                style="margin-top: 10px; width: 100%;"
                >Add to Projects</b-button
            >
        </b-form>
    </b-dropdown>
</template>

<script>
import axios from 'axios'

export default {
    name: 'NewProjectForm',
    data() {
        return {
            newTitle: '',
            deadlineDate: '',
        }
    },
    computed: {
        dateState() {
            let mindate = new Date()
            let dd = ('0' + mindate.getDate()).slice(-2)
            let mm = ('0' + (mindate.getMonth() + 1)).slice(-2)
            let yyyy = mindate.getFullYear()
            mindate = yyyy + '-' + mm + '-' + dd
            if (this.deadlineDate === '') return null
            else return this.deadlineDate > mindate
        },
    },
    methods: {
        newProject(evt) {
            evt.preventDefault()
            const self = this
            let data = {
                title: this.newTitle,
                isDone: false,
                deadlineDate: this.deadlineDate,
            }
            axios
                .post(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects',
                    data,
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
                    }
                    if (response.data) {
                        self.$emit('newProject', response.data)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}
</style>
