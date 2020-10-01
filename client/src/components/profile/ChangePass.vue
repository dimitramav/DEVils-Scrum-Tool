<template>
    <div>
        <b-row align-h="start">
            <b-btn v-b-toggle.changepass class="text-font"
                >Change Password</b-btn
            >
        </b-row>
        <b-collapse id="changepass" class="mt-2">
            <b-card class="card-text text-font">
                <b-alert show variant="success" v-if="ok === true">
                    You have successfully changed your password!</b-alert
                >
                <b-form style="text-align: left;">
                    <b-form-group
                        label="Current password"
                        :invalid-feedback="
                            currentPassword === false
                                ? 'Please insert your correct password'
                                : ''
                        "
                        label-for="currentpass"
                    >
                        <b-form-input
                            id="currentpass"
                            type="password"
                            @change="passwordMatch"
                            :state="currentPassword"
                            v-model="password"
                            autocomplete="off"
                            required
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group label="New password" label-for="newpass">
                        <b-form-input
                            id="newpass"
                            type="password"
                            :state="checkPassword"
                            v-model="newpass"
                            required
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group
                        label="Verify new password"
                        label-for="verifypass"
                    >
                        <b-form-input
                            id="verifypass"
                            type="password"
                            :state="verifyPassword"
                            v-model="verifypass"
                            required
                        >
                        </b-form-input>
                    </b-form-group>
                </b-form>
                <div class="text-center">
                    <b-btn
                        @click="updatePass"
                        size="md"
                        :disabled="
                            validPassword === false ||
                            validPassword === null ||
                            validVerifyPass === false ||
                            validVerifyPass === null
                        "
                    >
                        Update Password</b-btn
                    >
                </div>
            </b-card>
        </b-collapse>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'ChangePass',
    data() {
        return {
            validEmail: null,
            ok: false,
            password: '',
            newpass: '',
            verifypass: '',
            validPassword: null,
            validVerifyPass: null,
            currentPassword: null,
        }
    },
    computed: {
        verifyPassword() {
            let verifyCheck = null
            if (this.verifypass === '') {
                //console.log('p', this.newpass, this.validPassword)
                return null
            }
            if (
                this.newpass === this.verifypass &&
                this.newpass !== '' &&
                this.validPassword === true
            ) {
                //console.log('p1', this.newpass, this.validPassword)
                verifyCheck = true
                return verifyCheck
            } else {
                //console.log('p2', this.newpass, this.validPassword)
                verifyCheck = false
                return verifyCheck
            }
        },
        checkPassword() {
            let passCheck = null
            if (this.newpass === '') {
                //console.log('c', this.newpass, this.validPassword)
                return null
            }
            const regex = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
            if (this.newpass.match(regex)) {
                passCheck = true
                //console.log('c1', this.newpass, this.validPassword)
                return passCheck
            } else {
                passCheck = false
                //console.log('c2', this.newpass, this.validPassword)
                return passCheck
            }
        },
    },
    watch: {
        checkPassword: {
            deep: true,
            handler: function (passCheck) {
                this.validPassword = passCheck
            },
        },
        verifyPassword: {
            deep: true,
            handler: function (verifyCheck) {
                this.validVerifyPass = verifyCheck
            },
        },
    },
    methods: {
        updatePass() {
            const self = this
            axios
                .put(
                    this.$url +
                        '/password/' +
                        localStorage.getItem('userId') +
                        '/update',
                    {
                        password: this.newpass,
                    },
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
                        self.ok = true
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
            this.password = ''
            this.newpass = ''
            this.verifypass = ''
            this.validPassword = null
            this.validVerifyPass = null
        },
        passwordMatch() {
            const self = this
            this.ok = false
            axios
                .post(
                    this.$url +
                        '/password/' +
                        localStorage.getItem('userId') +
                        '/matches',
                    {
                        password: this.password,
                    },
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
                        self.currentPassword = response.data
                    }
                })
                .catch(function (error) {
                    console.log(error)
                    //self.validPassword = false;
                })
        },
    },
}
</script>

<style type="text/css">
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}
</style>
