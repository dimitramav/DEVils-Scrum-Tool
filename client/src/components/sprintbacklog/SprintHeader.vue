<template>
    <div>
        <b-row class="text-font">
            <b-col class="center">
                <h2>Sprint #{{ currentSprint.numSprint }}</h2>
            </b-col>
            <b-col>
                <EditSprintForm
                    v-if="isDeveloper() == false"
                    v-on:editSprint="editSprint"
                    :currentSprint="currentSprint"
                />
            </b-col>
        </b-row>
        <hr />
        <b-row class="text-font">
            <b-col>
                <h5>
                    <i class="text-muted">Sprint Goal: </i
                    >{{ currentSprint.goal }}
                </h5>
            </b-col>
            <b-col>
                <h5>
                    <i class="text-muted">Sprint Plan: </i
                    >{{ currentSprint.plan }}
                </h5>
            </b-col>
            <b-col>
                <h5 class="text-font">
                    <i class="text-muted">Deadline Date: </i
                    >{{ currentSprint.deadlineDate }}
                </h5>
            </b-col>
        </b-row>
        <!--<hr style="margin-bottom: 20px;" />-->
        <b-row class="line">.</b-row>
    </div>
</template>

<script>
import axios from 'axios'
import EditSprintForm from '@/components/sprintbacklog/EditSprintForm.vue'

export default {
    name: 'SprintHeader',
    components: {
        EditSprintForm,
    },
    data() {
        return {
            routeSprintId: 0,
            currentSprint: {},
        }
    },
    methods: {
        getSprintInfos() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/sprints/' +
                        this.routeSprintId,
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
                        self.currentSprint = response.data
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        editSprint(updatedSprintInfo) {
            this.currentSprint = updatedSprintInfo
        },
        isDeveloper() {
            if (localStorage.getItem('teamRole') == 'Developer') {
                return true
            } else {
                return false
            }
        },
    },
    mounted() {
        this.routeSprintId = parseInt(this.$route.params.sprintId)
        this.getSprintInfos()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.line {
    font-size: 3px;
    line-height: 2px;
    background-color: #e6edf2;
    margin-top: 10px;
    margin-bottom: 20px;
}
</style>
