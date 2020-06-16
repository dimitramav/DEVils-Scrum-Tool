<template>
    <b-button
        variant="info"
        style="margin-top: 10px; width: 100%;"
        @click="archiveProject()"
    >
        Archive Project (Done)
    </b-button>
</template>

<script>
import axios from 'axios'

export default {
    name: 'ArchiveProject',
    data() {
        return {
            isDoneToBecome: true,
        }
    },
    methods: {
        archiveProject() {
            axios
                .put(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '?isDone=false',
                    this.isDoneToBecome,
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
