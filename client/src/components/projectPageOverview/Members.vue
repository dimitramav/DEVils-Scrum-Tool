<template>
    <b-jumbotron style="background-color: #f7f9fc;">
        <b-row style="margin-top: -5%;">
            <h2 class="text-font">Team</h2>
        </b-row>

        <b-row class="text-font" style="margin-top: 20px;">
            <b-col cols="11">
                <div id="scrollspy-nested" class="team">
                    <b-list-group
                        v-for="teamMember in Team"
                        v-bind:data="teamMember"
                        v-bind:key="teamMember.email"
                    >
                        <b-list-group-item>
                            <div class="d-flex">
                                <h5 class="mb-1">
                                    {{ teamMember.role }}
                                </h5>
                            </div>
                            <p align="left" class="text-font">
                                Name:
                                {{ teamMember.lastname }}
                                {{ teamMember.firstname }}
                                <br />
                                Email: {{ teamMember.email }}
                            </p>
                        </b-list-group-item>
                    </b-list-group>
                </div>
            </b-col>
        </b-row>
        <br />
        <b-form
            class="text-font"
            v-if="isProductOwner == true"
            inline
            @submit="addMembers"
        >
            Add User
            <b-form-group
                id="emailForm"
                label-for="email"
                style="margin-left: 7px;"
                :invalid-feedback="validEmail === false ? 'Invalid User' : ''"
            >
                <b-form-input
                    class="mb-2 mr-sm-2 mb-sm-0"
                    id="emailInput"
                    type="email"
                    v-model="newMember.email"
                    @change="checkEmail"
                    :state="validEmail"
                    placeholder="email"
                    required
                >
                </b-form-input>
            </b-form-group>
            as
            <b-form-select
                v-model="newMember.role"
                :options="opts"
                style="margin-left: 7px;"
                class="mb-2 mr-sm-2 mb-sm-0"
                id="inlineFormCustomSelectPref"
            >
                <template slot="first">
                    <option :value="null" disabled>- Select role -</option>
                </template>
            </b-form-select>
            <b-button
                type="submit"
                variant="primary"
                :disabled="validEmail === false"
                >Invite</b-button
            >
        </b-form>
        <b-row style="margin-top: 20px;">
            <b-col cols="11">
                <b-alert variant="success" :show="showAlert" dismissible>
                    Invitation send!</b-alert
                >
            </b-col>
        </b-row>
    </b-jumbotron>
</template>

<script>
import axios from 'axios'

export default {
    name: 'Members',
    props: {
        isProductOwner: {
            type: Boolean,
            default: false,
        },
        projectOverview: Object,
        Team: Array,
    },
    data() {
        return {
            opts: [
                {
                    value: '1',
                    text: 'Scrum Master',
                },
                {
                    value: '2',
                    text: 'Developer',
                },
            ],
            newMember: {
                email: '',
                role: null,
            },
            invitation: {
                id: null,
                projectId: 0,
                projectTitle: null,
                role: null,
                fromUsername: null,
                toUserEmail: '',
                type: '',
            },
            showAlert: false,
            validEmail: null,
        }
    },
    methods: {
        addMembers(evt) {
            evt.preventDefault()
            const self = this
            if (self.newMember.role === '1') {
                self.invitation.role = 'Scrum Master'
            } else if (self.newMember.role === '2') {
                self.invitation.role = 'Developer'
            }
            self.invitation.projectId = this.$route.params.id
            self.invitation.projectTitle = self.projectOverview.project.title
            self.invitation.fromUsername = localStorage.getItem('username')
            self.invitation.toUserEmail = self.newMember.email
            self.invitation.type = 'Accept/Decline'
            axios
                .post(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/notifications',
                    self.invitation,
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
                        console.log('Invitation send')
                        self.newMember.email = ''
                        self.validEmail = null
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
            self.showAlert = true
        },
        checkEmail() {
            const self = this
            if (this.newMember.email === '') {
                this.validEmail = null
                return
            } // Check if the user is among the members already in project
            let i
            for (i = 0; i < this.Team.length; i++) {
                if (this.Team[i].email === this.newMember.email) {
                    this.validEmail = false
                    return
                }
            }
            axios
                .post(this.$url + 'exists', {
                    mail: self.newMember.email,
                })
                .then(function (response) {
                    self.validEmail = response.data.exists === 1
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

.team {
    position: relative;
    height: 328px;
    overflow-y: auto;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}
</style>
