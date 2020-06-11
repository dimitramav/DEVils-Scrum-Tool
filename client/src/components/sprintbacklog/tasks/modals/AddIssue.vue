<template>
    <b-form>
        <b-form-group label="Add an issue:">
            <b-form-input
                v-model="newIssue"
                type="text"
                placeholder="Description"
            >
            </b-form-input>
        </b-form-group>
    </b-form>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AddIssue',
    props: {
        idTask: Number,
    },
    data: function () {
        return {
            newIssue: '',
            issueToAdd: {
                description: '',
                Task_id: 0,
            },
        }
    },
    methods: {
        addIssue() {
            const self = this
            if (this.newIssue === '') return
            this.issueToAdd.description = this.newIssue
            this.issueToAdd.Task_id = this.idTask
            axios
                .post(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/tasks/' +
                        this.idTask +
                        '/issues',
                    this.issueToAdd,
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
                        self.newIssue = ''
                        self.$emit('addIssue', response.data.results)
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
