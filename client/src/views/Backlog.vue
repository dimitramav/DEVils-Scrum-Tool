<template>
    <b-container>
        <Navbar :dashboard="true" :logout_prop="false" />
        <NewEpicForm v-on:newEpic="newEpic" :numOfPbis="currentPbis.length" />
        <b-row class="line">.</b-row>
        <b-row>
            <b-card-group
                v-for="cur_pbi in currentPbis"
                :key="cur_pbi.id"
                class="card-style text-font"
            >
                <EpicCard
                    v-on:editEpic="editEpic"
                    v-on:newCurrentEpicId="newCurrentEpicId"
                    v-on:editCurrentUserStories="editCurrentUserStories"
                    :cur_pbi="cur_pbi"
                    :currentPbis="currentPbis"
                    :currentUserStories="currentUserStories"
                    :currentEpicId="currentEpicId"
                />
            </b-card-group>
        </b-row>
    </b-container>
</template>

<script>
import axios from 'axios'
import Navbar from '@/components/navbar/Navbar.vue'
import NewEpicForm from '@/components/backlog/NewEpicForm.vue'
import EpicCard from '@/components/backlog/EpicCard.vue'

export default {
    name: 'Backlog',
    components: {
        Navbar,
        NewEpicForm,
        EpicCard,
    },
    data() {
        return {
            currentEpicId: -1,
            currentProject_id: -1,
            currentPbis: [],
            currentUserStories: [[], []],
        }
    },
    methods: {
        getPBIS() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis?isEpic=true',
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
                        self.currentPbis = response.data
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        newEpic(data) {
            this.currentPbis.push(data)
        },
        editEpic(updatedCurrentPbis) {
            this.currentPbis = updatedCurrentPbis
        },
        newCurrentEpicId(updatedCurrentEpicId) {
            this.currentEpicId = updatedCurrentEpicId
        },
        editCurrentUserStories(updatedCurrentUserStories) {
            this.currentUserStories = updatedCurrentUserStories
        },
    },
    mounted() {
        if (
            localStorage.getItem('auth_token') === 'null' ||
            localStorage.getItem('userId') === 'null'
        )
            return
        this.currentProject_id = parseInt(this.$route.params.id)
        this.getPBIS()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.line {
    font-size: 1px;
    line-height: 2px;
    background-color: #e6edf2;
    margin-bottom: 15px;
}

.card-style {
    margin-bottom: 10px;
    padding-left: 10px;
}
</style>
