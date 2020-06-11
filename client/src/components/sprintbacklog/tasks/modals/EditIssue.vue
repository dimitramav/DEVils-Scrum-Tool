<template>
    <b-modal
        class="text-left"
        :id="'edit_issue' + issue.idIssue"
        title="Edit Issue"
        @ok="editIssue()"
    >
        <b-form-group
            label="Description:"
            :label-for="'editIssue' + issue.idIssue"
        >
            <b-form-input
                :id="'editIssue' + issue.idIssue"
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
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/tasks/' +
                        this.idTask +
                        '/issues',
                    this.issue,
                    {
                        headers: {
                            auth: localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.results) {
                        console.log('Edit Issue')
                        self.$emit('editIssue', response.data.results)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        deleteIssue() {
            const self = this
            axios
                .patch(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/tasks/' +
                        this.idTask +
                        '/issues',
                    this.issue,
                    {
                        headers: {
                            auth: localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.results) {
                        self.$emit('deleteIssue', self.issue.idIssue)
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
