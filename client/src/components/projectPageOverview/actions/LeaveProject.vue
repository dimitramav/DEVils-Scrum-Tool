<template>
    <div>
        <b-button squared variant="outline-danger" v-b-modal="'lv_project'"
            >Leave Project</b-button
        >
        <b-modal @ok="leaveProject()" :id="'lv_project'" title="Leave Project">
            <p class="alert alert-danger">
                Are you sure you want to leave this project?
            </p>
            <template v-slot:modal-footer="{ cancel, ok }">
                <b-btn variant="secondary" @click="cancel()">Cancel</b-btn>
                <b-button variant="danger" @click="ok()">Leave</b-button>
            </template>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'LeaveProject',
    props: {
        Project_id: Number,
    },
    methods: {
        leaveProject() {
            const self = this
            axios
                .delete(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.Project_id +
                        '/members',
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
