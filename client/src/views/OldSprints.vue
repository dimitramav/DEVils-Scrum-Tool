<template>
    <b-container>
        <Navbar :dashboard="true" :logout_prop="false" />
        <NewSprintButton :numOfSprints="sprints.length" />
        <b-row class="line">.</b-row>
        <b-list-group
            v-for="sprint in sprints"
            v-bind:data="sprint"
            v-bind:key="sprint.id"
        >
            <b-list-group-item button @click="gotoSprintBacklog(sprint.id)">
                <b-row class="text-font">
                    <b-col class="text-left">
                        <b>
                            #{{ sprint.numSprint }}
                            <b v-if="sprint.isCurrent == true">(Current)</b>
                        </b>
                    </b-col>
                    <b-col class="text-left">
                        <b>Goal: </b>{{ sprint.goal }}
                    </b-col>
                    <b-col class="text-left">
                        <b>Deadline: </b>{{ sprint.deadlineDate }}
                    </b-col>
                    <b-col class="text-right">
                        <BadgeInfo :sprintId="sprint.id" />
                    </b-col>
                </b-row>
            </b-list-group-item>
        </b-list-group>
    </b-container>
</template>

<script>
import axios from 'axios'
import Navbar from '@/components/navbar/Navbar.vue'
import NewSprintButton from '@/components/oldsprints/NewSprintButton.vue'
import BadgeInfo from '@/components/oldsprints/BadgeInfo.vue'

export default {
    name: 'OldSprints',
    components: {
        Navbar,
        NewSprintButton,
        BadgeInfo,
    },
    data() {
        return {
            sprints: [],
        }
    },
    methods: {
        getSprints() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/sprints',
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
                        self.sprints = response.data
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        gotoSprintBacklog(idSprint) {
            this.$router.push({
                path:
                    '/project/' +
                    this.$route.params.id +
                    '/sprintbacklog/' +
                    idSprint,
            })
        },
    },
    mounted() {
        this.getSprints()
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
    /*margin-top: 40px;*/
    margin-bottom: 15px;
}

.list-group-item {
    padding-top: 20px;
    padding-bottom: 20px;
    font-size: 20px;
}
</style>
