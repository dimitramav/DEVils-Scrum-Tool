<template>
    <div>
        <b-form class="text-font" inline @submit="addMembers">
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
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AddMembers',
    props: {
        projectTitle: String,
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
                project: {
                    id: 0,
                    title: '',
                    isDone: false,
                    deadlineDate: '',
                },
                projectTitle: null,
                role: null,
                sender: {
                    username: null,
                },
                receiver: {
                    email: '',
                },
                type: '',
            },
            showAlert: false,
            validEmail: null,
        }
    },
    methods: {
        addMembers(evt) {
            evt.preventDefault()
            // Create the message
            if (this.newMember.role === '1') {
                this.invitation.role = 'Scrum Master'
            } else if (this.newMember.role === '2') {
                this.invitation.role = 'Developer'
            }
            this.invitation.project.id = this.$route.params.id
            this.invitation.projectTitle = this.projectTitle
            this.invitation.sender.username = localStorage.getItem('username')
            this.invitation.receiver.email = this.newMember.email
            this.invitation.type = 'Accept/Decline'
            const self = this
            axios
                .post(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/notifications',
                    this.invitation,
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
                        //console.log('Invitation send')
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
                .get(this.$url + '/exists/email/' + this.newMember.email)
                .then(function (response) {
                    self.validEmail = response.data
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
</style>
