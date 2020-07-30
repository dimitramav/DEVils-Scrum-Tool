<template>
    <div>
        <b-row align-h="start" class="text-font">
            <b-button variant="danger" v-b-modal="'delete_user'"
                >Delete Account</b-button
            >
        </b-row>
        <b-modal
            @ok="deleteUserAccount()"
            :id="'delete_user'"
            title="Delete User Account"
        >
            <h5 class="alert alert-success text-center">
                Thank you for trying ScrumTool!!
            </h5>
            <h6 class="alert alert-warning">
                By deleting your account, you will automatically be removed from
                all projects that you were Scrum Master or Developer, while all
                the projects in which you are Product Owner will also be
                completely deleted (along with their sprints, epics, stories,
                tasks and issues). All your data will be completely removed from
                the database.
            </h6>
            <h5 class="alert alert-danger text-center">
                This action is irreversible! Are you sure you want to delete
                this account?
            </h5>
            <template v-slot:modal-footer="{ cancel, ok }">
                <b-btn variant="secondary" @click="cancel()">Cancel</b-btn>
                <b-btn variant="danger" @click="ok()">Delete Account</b-btn>
            </template>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'DeleteAccount',
    methods: {
        deleteUserAccount() {
            const self = this
            axios
                .delete(
                    this.$url + '/users/' + localStorage.getItem('userId'),
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
                        localStorage.setItem('userId', 'null')
                        localStorage.setItem('username', 'null')
                        localStorage.setItem('auth_token', 'null')
                        self.$router.push({
                            name: 'Index',
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

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}
</style>
