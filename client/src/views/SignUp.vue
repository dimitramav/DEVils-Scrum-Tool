<template>
    <b-container>
        <b-row>
            <b-col></b-col>
            <b-col class="w-100">
                <router-link :to="{ name: 'Index' }">
                    <img src="@/assets/kanban.png" style="width: 50%;" />
                </router-link>
                <br /><br />
                <b-form @submit="onSubmit" class="submit-form">
                    <EmailForm v-on:email="editEmail" />
                    <UsernameForm v-on:username="editUsername" />
                    <NameForm
                        v-on:firstname="editFirstname"
                        v-on:lastname="editLastname"
                    />
                    <PasswordForm v-on:password="editPassword" />
                    <br />
                    <b-button
                        id="signup"
                        size="lg"
                        type="submit"
                        variant="primary"
                        :disabled="
                            validEmail === false ||
                            validUsername === false ||
                            validPassword === false
                        "
                    >
                        Sign up
                    </b-button>
                    <br /><br /><br />
                    <b-button variant="link" :to="{ name: 'SignIn' }"
                        >Already a member? Sign in</b-button
                    >
                    <br /><br /><br />
                </b-form>
            </b-col>
            <b-col></b-col>
        </b-row>
    </b-container>
</template>

<script>
import axios from 'axios'
import EmailForm from '@/components/signup/EmailForm.vue'
import UsernameForm from '@/components/signup/UsernameForm.vue'
import NameForm from '@/components/signup/NameForm.vue'
import PasswordForm from '@/components/signup/PasswordForm.vue'

export default {
    name: 'SignUp',
    components: {
        EmailForm,
        UsernameForm,
        NameForm,
        PasswordForm,
    },
    data() {
        return {
            form: {
                email: '',
                username: '',
                firstname: '',
                lastname: '',
                password: '',
            },
            validEmail: null,
            validUsername: null,
            validPassword: false,
        }
    },
    methods: {
        editEmail(email, validEmail) {
            this.form.email = email
            this.validEmail = validEmail
            //console.log(this.form.email, this.validEmail)
        },
        editUsername(username, validUsername) {
            this.form.username = username
            this.validUsername = validUsername
            //console.log(this.form.username, this.validUsername)
        },
        editFirstname(firstname) {
            this.form.firstname = firstname
            //console.log(this.form.firstname)
        },
        editLastname(lastname) {
            this.form.lastname = lastname
            //console.log(this.form.lastname)
        },
        editPassword(password, validPassword) {
            this.form.password = password
            this.validPassword = validPassword
            //console.log('o', this.form.password, this.validPassword)
        },
        onSubmit(evt) {
            evt.preventDefault()
            const self = this
            axios
                .post(this.$url + '/signup', this.form)
                .then(function (response) {
                    if (response.data.serverErrorMessage) {
                        console.log(response.data.serverErrorMessage)
                    } else {
                        //console.log(response.data)
                        axios
                            .post(self.$url + '/authenticate', {
                                username: self.form.email,
                                password: self.form.password,
                            })
                            .then(function (response) {
                                if (response.data.serverErrorMessage) {
                                    console.log(
                                        response.data.serverErrorMessage
                                    )
                                } else {
                                    //console.log(response.data)
                                    localStorage.setItem(
                                        'auth_token',
                                        response.data.jwt
                                    )
                                    localStorage.setItem(
                                        'username',
                                        response.data.username
                                    )
                                    localStorage.setItem(
                                        'userId',
                                        response.data.id
                                    )
                                    self.$router.push({
                                        path: '/',
                                    })
                                }
                            })
                            .catch(function (error) {
                                console.log(error)
                            })
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        //redirect to user homepage if user is already logged in
        if (
            localStorage.getItem('auth_token') !== 'null' &&
            localStorage.getItem('userId') !== 'null'
        )
            this.$router
                .push({
                    path: '/',
                })
                .catch((err) => err)
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.submit-form {
    font-family: Quicksand;
}
</style>
