<template>
    <b-row>
        <b-col>
            <h4 class="text-font">User Stories</h4>
        </b-col>
        <b-col>
            <h4 class="text-font">TO DO</h4>
        </b-col>
        <b-col>
            <h4 class="text-font">DOING</h4>
        </b-col>
        <b-col>
            <h4 class="text-font">DONE</h4>
        </b-col>
    </b-row>
</template>

<script>
import axios from 'axios'

export default {
    name: 'LoadStories',
    methods: {
        getSprintStories() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/sprintStories?sprintId=' +
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
                        self.$emit('getSprintStories', response.data)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        this.getSprintStories()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}
</style>
