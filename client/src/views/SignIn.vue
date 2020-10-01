<template>
    <b-container>
        <b-row>
            <b-col></b-col>
            <b-col class="w-100">
                <router-link :to="{ name: 'Index' }">
                    <img src="@/assets/kanban.png" style="width: 50%;" />
                </router-link>
                <br /><br />
                <b-form class="submit-form" @submit="onSubmit">
                    <b-alert show variant="warning" v-if="wrongLogin === true">
                        Invalid email or password
                    </b-alert>
                    <b-form-group id="email_group">
                        <b-form-input
                            id="email"
                            type="email"
                            v-model="form.email"
                            placeholder="Enter email"
                            required
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-form-group id="password_group">
                        <b-form-input
                            id="password"
                            type="password"
                            v-model="form.password"
                            required
                            placeholder="Enter password"
                        >
                        </b-form-input>
                    </b-form-group>
                    <b-button
                        size="sm"
                        variant="link"
                        :to="{ name: 'ForgotPass' }"
                        >Forgot your password?</b-button
                    >
                    <br /><br />
                    <b-button size="lg" type="submit" variant="primary">
                        Sign in</b-button
                    >
                    <br /><br /><br />
                    <b-button variant="link" :to="{ name: 'SignUp' }"
                        >Not a member? Sign up</b-button
                    >
                    <br /><br />
                </b-form>
            </b-col>
            <b-col></b-col>
        </b-row>
    </b-container>
</template>

<script>
import axios from 'axios'

export default {
    name: 'SignIn',
    data() {
        return {
            form: {
                email: '',
                password: '',
            },
            wrongLogin: false,
        }
    },
    methods: {
        onSubmit(evt) {
            evt.preventDefault()
            const self = this
            axios
                .post(this.$url + '/authenticate', {
                    username: this.form.email,
                    password: this.form.password,
                })
                .then(function (response) {
                    if (response.data.serverErrorMessage) {
                        console.log(response.data.serverErrorMessage)
                        self.wrongLogin = true
                        self.form.password = ''
                    } else {
                        //console.log(response.data)
                        localStorage.setItem('auth_token', response.data.jwt)
                        localStorage.setItem('username', response.data.username)
                        localStorage.setItem('userId', response.data.id)
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
