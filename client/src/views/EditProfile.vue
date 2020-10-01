<template>
    <b-container fluid>
        <Navbar :logout_prop="false" :dashboard="false" />
        <b-row>
            <b-col></b-col>
            <b-col>
                <b-form class="text-font" @submit="updateProfile">
                    <b-form-group label="First Name" label-for="firstname">
                        <b-form-input
                            id="firstname"
                            type="text"
                            v-model="userInfos.firstname"
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="Last Name" label-for="lastname">
                        <b-form-input
                            id="lastname"
                            type="text"
                            v-model="userInfos.lastname"
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="Email address" label-for="email">
                        <b-form-input
                            id="email"
                            type="email"
                            v-model="userInfos.email"
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="Job" label-for="job">
                        <b-form-input
                            id="job"
                            type="text"
                            v-model="userInfos.job"
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="Company" label-for="company">
                        <b-form-input
                            id="company"
                            type="text"
                            v-model="userInfos.company"
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="Country" label-for="Country">
                        <b-form-input
                            id="country"
                            type="text"
                            v-model="userInfos.country"
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="About me" label-for="description">
                        <b-form-textarea
                            id="description"
                            type="text"
                            :rows="3"
                            :max-rows="6"
                            v-model="userInfos.description"
                        >
                        </b-form-textarea>
                    </b-form-group>
                    <b-row align-h="center" style="margin-top: 30px;">
                        <b-button variant="success" size="lg" type="info"
                            >Submit changes</b-button
                        >
                    </b-row>
                </b-form>
                <ChangePass class="changePass-button" />
                <DeleteAccount />
            </b-col>
            <b-col></b-col>
        </b-row>
        <br /><br />
    </b-container>
</template>

<script>
import axios from 'axios'
import Navbar from '@/components/navbar/Navbar.vue'
import ChangePass from '@/components/profile/ChangePass.vue'
import DeleteAccount from '@/components/profile/DeleteAccount.vue'

export default {
    name: 'EditProfile',
    components: {
        Navbar,
        ChangePass,
        DeleteAccount,
    },
    data() {
        return {
            userInfos: [],
        }
    },
    methods: {
        getProfile() {
            const self = this
            axios
                .get(
                    this.$url + '/profile/' + localStorage.getItem('username'),
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
                        self.userInfos = response.data
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        updateProfile(evt) {
            evt.preventDefault()
            const self = this
            axios
                .put(
                    this.$url + '/profile/' + localStorage.getItem('username'),
                    self.userInfos,
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
                        self.userInfos = response.data
                        self.$router.push({
                            path: '/users/' + localStorage.getItem('username'),
                        })
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        if (
            localStorage.getItem('auth_token') === 'null' ||
            localStorage.getItem('userId') === 'null'
        )
            return
        this.getProfile()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
    text-align: left;
}

.changePass-button {
    margin-top: 15px;
    margin-bottom: 15px;
}
</style>
