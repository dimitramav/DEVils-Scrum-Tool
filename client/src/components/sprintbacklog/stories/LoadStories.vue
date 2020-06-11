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
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/stories?isCurrent=true',
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
                    if (response.data.stories) {
                        //self.currentStories = response.data.stories
                        self.$emit('getSprintStories', response.data.stories)
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
