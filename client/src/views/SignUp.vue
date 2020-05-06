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
                    <EmailForm v-on:email="editEmail"></EmailForm>
                    <UsernameForm v-on:username="editUsername"></UsernameForm>
                    <NameForm
                        v-on:firstname="editFirstname"
                        v-on:lastname="editLastname"
                    ></NameForm>
                    <PasswordForm v-on:password="editPassword"></PasswordForm>
                    <br />
                    <b-button
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
                .post(this.$url + 'users', {
                    mail: this.form.email,
                    username: this.form.username,
                    firstname: this.form.firstname,
                    lastname: this.form.lastname,
                    password: this.form.password,
                })
                .then(function (response) {
                    if (response.data.results) {
                        localStorage.setItem(
                            'auth_token',
                            response.data.results.auth_token
                        )
                        localStorage.setItem(
                            'username',
                            response.data.results.username
                        )
                        localStorage.setItem(
                            'userId',
                            response.data.results.userId
                        )
                        self.$router.push({
                            path: '/',
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
            this.$router.push({
                path: '/',
            })
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.submit-form {
    font-family: Quicksand;
}
</style>
