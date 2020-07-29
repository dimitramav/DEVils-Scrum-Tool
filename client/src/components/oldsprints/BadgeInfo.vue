<template>
    <div>
        <b-badge variant="success" style="margin-right: 5px;">
            Tasks: {{ tasks }}
        </b-badge>
        <b-badge variant="info">Issues: {{ issues }}</b-badge>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'BagdeInfo',
    props: {
        sprintId: Number,
    },
    data() {
        return {
            projectOverview: {},
            tasks: 0,
            issues: 0,
        }
    },
    methods: {
        getSprintInfo() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '?sprintId=' +
                        this.sprintId,
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
                        self.projectOverview = response.data
                        self.calculateTotalTasksIssues()
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        calculateTotalTasksIssues() {
            this.tasks =
                this.projectOverview.todo +
                this.projectOverview.doing +
                this.projectOverview.done
            this.issues = this.projectOverview.issues
        },
    },
    mounted() {
        this.getSprintInfo()
    },
}
</script>

<style scoped></style>
