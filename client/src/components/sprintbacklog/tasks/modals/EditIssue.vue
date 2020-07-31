<template>
    <b-modal
        class="text-left"
        :id="'edit_issue' + issue.id"
        title="Edit Issue"
        @ok="editIssue()"
    >
        <b-form-group label="Description:" :label-for="'editIssue' + issue.id">
            <b-form-input
                :id="'editIssue' + issue.id"
                type="text"
                v-model="issue_desc"
                required
            >
            </b-form-input>
        </b-form-group>
        <template v-slot:modal-footer="{ remove, cancel, ok }">
            <b-button class="mr-auto" variant="danger" @click="deleteIssue()">
                Delete Issue</b-button
            >
            <b-button variant="secondary" @click="cancel()">Cancel</b-button>
            <b-button variant="primary" @click="ok()">OK</b-button>
        </template>
    </b-modal>
</template>

<script>
import axios from 'axios'

export default {
    name: 'EditIssue',
    props: {
        PBI_id: Number,
        idTask: Number,
        issue: Object,
    },
    data() {
        return {
            issue_desc: '',
        }
    },
    methods: {
        editIssue() {
            const self = this
            this.issue.description = this.issue_desc
            axios
                .put(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis/' +
                        this.PBI_id +
                        '/tasks/' +
                        this.idTask +
                        '/issues',
                    this.issue,
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
                        self.$emit('editIssue', response.data)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        deleteIssue() {
            const self = this
            axios
                .delete(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis/' +
                        this.PBI_id +
                        '/tasks/' +
                        this.idTask +
                        '/issues/' +
                        this.issue.id,
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
                        self.$emit('deleteIssue', self.issue.id)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        this.issue_desc = this.issue.description
    },
}
</script>

<style scoped></style>
