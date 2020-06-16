<template>
    <b-jumbotron style="background-color: #f7f7f7;">
        <b-row style="margin-bottom: 20px;">
            <h1 class="text-font done">
                This Project has finished... Go back home and create a new one!
            </h1>
        </b-row>
        <b-row class="text-font">
            <b-col style="margin-left: 35%;">
                <b-button class="text-font" size="lg" href="/">
                    Home Page
                </b-button>
            </b-col>
            <b-col style="margin-right: 35%;">
                <b-button
                    :disabled="isProductOwner === false"
                    size="lg"
                    variant="info"
                    @click="activateProject()"
                >
                    Activate Project
                </b-button>
            </b-col>
        </b-row>
    </b-jumbotron>
</template>

<script>
import axios from 'axios'

export default {
    name: 'ActivateProject',
    props: {
        isProductOwner: Boolean,
    },
    data() {
        return {
            isDoneToBecome: false,
        }
    },
    methods: {
        activateProject() {
            axios
                .put(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '?isDone=true',
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

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.done {
    color: #264d73;
    text-align: center;
    display: block;
    margin-left: auto;
    margin-right: auto;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}
</style>
