<template>
    <b-modal @ok="deleteSprint()" :id="'rm_sprint'" title="Delete Sprint">
        <p class="alert alert-danger">
            Warning!! By deleting this sprint from the project its information
            are deleted, however not the stories, tasks and issues that were
            attached to it
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
    name: 'DeleteSprint',
    props: {
        currentSprint: Object,
    },
    methods: {
        deleteSprint() {
            // Convert deadline's format for java backend
            let d = this.currentSprint.deadlineDate
            let prodate = new Date(d)
            let dd = ('0' + prodate.getDate()).slice(-2)
            let mm = ('0' + (prodate.getMonth() + 1)).slice(-2)
            let yyyy = prodate.getFullYear()
            prodate = yyyy + '-' + mm + '-' + dd
            this.currentSprint.deadlineDate = prodate
            // Delete sprint
            const self = this
            axios
                .delete(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/sprints/' +
                        this.$route.params.sprintId,
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
                            path:
                                '/project/' +
                                self.$route.params.id +
                                '/overview/',
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
