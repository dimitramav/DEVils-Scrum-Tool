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
        PBI_id: Number,
        idTask: Number,
    },
    data: function () {
        return {
            newIssue: '',
            issueToAdd: {
                description: '',
                task: {
                    id: 0,
                },
            },
        }
    },
    methods: {
        addIssue() {
            const self = this
            if (this.newIssue === '') return
            this.issueToAdd.description = this.newIssue
            this.issueToAdd.task.id = this.idTask
            axios
                .post(
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
                    this.issueToAdd,
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
                        self.newIssue = ''
                        self.$emit('addIssue', response.data)
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
