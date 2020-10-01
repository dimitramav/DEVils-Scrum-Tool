<template>
    <b-jumbotron style="background-color: #f7f9fc;">
        <b-row style="margin-top: -5%;">
            <b-col class="text-left">
                <h2 class="text-font">Team</h2>
            </b-col>
            <b-col>
                <LeaveProject
                    v-if="teamRole != 'Product Owner'"
                    :Project_id="projectOverview.project.id"
                />
            </b-col>
        </b-row>

        <b-row class="text-font" style="margin-top: 20px;">
            <b-col cols="11">
                <div id="scrollspy-nested" class="team">
                    <b-list-group
                        v-for="teamMember in Team"
                        v-bind:data="teamMember"
                        v-bind:key="teamMember.email"
                    >
                        <b-list-group-item
                            button
                            @click="gotoProfile(teamMember.username)"
                        >
                            <b-row class="text-font">
                                <b-col class="text-left">
                                    <p>
                                        Name:
                                        {{ teamMember.lastname }}
                                        {{ teamMember.firstname }}
                                        <br />
                                        Email: {{ teamMember.email }}
                                    </p>
                                </b-col>
                                <b-col>
                                    <h5 style="margin-top: 12px;">
                                        {{ teamMember.role }}
                                    </h5>
                                </b-col>
                            </b-row>
                        </b-list-group-item>
                    </b-list-group>
                </div>
            </b-col>
        </b-row>
        <br />
        <AddMembers
            v-if="teamRole != 'Developer'"
            :projectTitle="projectOverview.project.title"
            :Team="Team"
        />
    </b-jumbotron>
</template>

<script>
import LeaveProject from '@/components/projectPageOverview/actions/LeaveProject'
import AddMembers from '@/components/projectPageOverview/actions/AddMembers'

export default {
    name: 'Members',
    components: {
        LeaveProject,
        AddMembers,
    },
    props: {
        teamRole: {
            type: String,
            default: 'Product Owner',
        },
        projectOverview: Object,
        Team: Array,
    },
    methods: {
        gotoProfile(memberUsername) {
            this.$router.push({
                name: 'Profile',
                params: {
                    username: memberUsername,
                },
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

.team {
    position: relative;
    height: 328px;
    overflow-y: auto;
}

.list-group-item {
    padding-top: 20px;
    padding-bottom: 10px;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}
</style>
