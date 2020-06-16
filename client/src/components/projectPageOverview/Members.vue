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
                        v-bind:key="teamMember.mail"
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
                                Email: {{ teamMember.mail }}
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
                    v-model="newMember.mail"
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
                mail: '',
                role: null,
            },
            invitation: {
                idNotification: null,
                Project_id: 0,
                projectTitle: null,
                role: null,
                FromUsername: null,
                ToUserEmail: '',
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
            self.invitation.Project_id = this.$route.params.id
            self.invitation.projectTitle = self.projectOverview.project.title
            self.invitation.FromUsername = localStorage.getItem('username')
            self.invitation.ToUserEmail = self.newMember.mail
            self.invitation.type = 'Accept/Decline'
            axios
                .post(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/notifications',
                    self.invitation,
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
                        console.log('Invitation send')
                        self.newMember.mail = ''
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
            if (this.newMember.mail === '') {
                this.validEmail = null
                return
            } // Check if the user is among the members already in project
            let i
            for (i = 0; i < this.Team.length; i++) {
                if (this.Team[i].mail === this.newMember.mail) {
                    this.validEmail = false
                    return
                }
            }
            axios
                .post(this.$url + 'exists', {
                    mail: self.newMember.mail,
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
