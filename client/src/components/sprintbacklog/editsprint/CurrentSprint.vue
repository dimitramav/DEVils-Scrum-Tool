<template>
    <b-button
        v-if="currentSprint.isCurrent === false"
        variant="info"
        style="margin-top: 10px; width: 100%;"
        @click="makeSprintCurrent()"
    >
        Change Sprint to Current
    </b-button>
</template>

<script>
import axios from 'axios'

export default {
    name: 'CurrentSprint',
    props: {
        currentSprint: Object,
    },
    methods: {
        makeSprintCurrent() {
            this.currentSprint.isCurrent = true
            axios
                .put(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/sprints/' +
                        this.$route.params.sprintId +
                        '/makeSprintCurrent',
                    this.currentSprint,
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
                        location.reload()
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
