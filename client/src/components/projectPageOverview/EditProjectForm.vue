<template>
    <b-dropdown class="text-font" right>
        <template slot="button-content">
            <img src="@/assets/edit.png" class="btn-img" />
            Edit Project
        </template>
        <template>
            <b-form inline style="margin: 10px;" @submit="updateProject">
                <p>New Project's Title</p>
                <b-form-input
                    id="newTitle"
                    type="text"
                    style="margin-top: -10px;"
                    v-model="form.newTitle"
                    required
                ></b-form-input>
                <p style="margin-top: 10px;">
                    New Project's Deadline
                </p>
                <b-form-input
                    id="dateInput"
                    style="margin-top: -10px;"
                    v-model.trim="form.deadlineDate"
                    type="date"
                    :state="dateState"
                    required
                ></b-form-input>
                <b-form-invalid-feedback id="inputLiveFeedback">
                    Project's Deadline cannot be a past or current date.
                </b-form-invalid-feedback>
                <b-button
                    type="submit"
                    :disabled="dateState === false"
                    variant="success"
                    style="margin-top: 10px; width: 100%;"
                    >Save changes</b-button
                >
                <ArchiveProject v-if="isDone === false" />
                <b-button
                    v-b-modal="'rm_project'"
                    variant="danger"
                    style="margin-top: 10px; width: 100%;"
                    >Delete Project</b-button
                >
                <DeleteProject :Project_id="Project_id" />
            </b-form>
        </template>
    </b-dropdown>
</template>

<script>
import axios from 'axios'
import ArchiveProject from '@/components/projectPageOverview/actions/ArchiveProject'
import DeleteProject from '@/components/projectPageOverview/actions/DeleteProject'

export default {
    name: 'EditProjectForm',
    components: {
        ArchiveProject,
        DeleteProject,
    },
    props: {
        Project_id: {
            type: Number,
            default: 0,
        },
        Project_title: {
            type: String,
            default: '',
        },
        isDone: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            form: {
                id: this.Project_id,
                newTitle: this.Project_title,
                deadlineDate: '',
            },
        }
    },
    computed: {
        dateState() {
            let mindate = new Date()
            let dd = ('0' + mindate.getDate()).slice(-2)
            let mm = ('0' + (mindate.getMonth() + 1)).slice(-2)
            let yyyy = mindate.getFullYear()
            mindate = yyyy + '-' + mm + '-' + dd
            if (this.form.deadlineDate === '') return null
            return this.form.deadlineDate > mindate
        },
    },
    methods: {
        updateProject(evt) {
            evt.preventDefault()
            const self = this
            let data = {
                id: this.Project_id,
                title: this.form.newTitle,
                isDone: this.isDone,
                deadlineDate: this.form.deadlineDate,
            }
            axios
                .put(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id,
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
                    } else {
                        self.$emit('editProject', response.data)
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

.btn-img {
    width: 20px;
    margin-right: 5px;
    margin-bottom: 5px;
}
</style>
