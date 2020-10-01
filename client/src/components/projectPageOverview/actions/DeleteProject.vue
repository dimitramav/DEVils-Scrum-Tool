<template>
    <b-modal @ok="deleteProject()" :id="'rm_project'" title="Delete Project">
        <p class="alert alert-danger">
            Warning!! By deleting this project, all information that are
            connected with it (sprints, epics, stories, tasks, issues) are
            deleted. Are you sure you want to delete this project??
        </p>
        <template v-slot:modal-footer="{ cancel, ok }">
            <b-button variant="secondary" @click="cancel()">Cancel</b-button>
            <b-button variant="danger" @click="ok()">Delete</b-button>
        </template>
    </b-modal>
</template>

<script>
import axios from 'axios'

export default {
    name: 'DeleteProject',
    props: {
        Project_id: Number,
    },
    methods: {
        deleteProject() {
            const self = this
            axios
                .delete(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.Project_id,
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
                        self.$router.push({
                            path: '/',
                        })
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
}
</script>

<style scoped></style>
