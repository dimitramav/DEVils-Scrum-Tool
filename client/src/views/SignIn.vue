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
                    <b-form-group id="mail_group">
                        <b-form-input
                            id="mail"
                            type="email"
                            v-model="form.mail"
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
                mail: '',
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
                .post(this.$url + 'signin', {
                    mail: this.form.mail,
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
                    } else if (response.data.Message) {
                        if (
                            response.data.Message === 'Wrong Email or Password'
                        ) {
                            self.wrongLogin = true
                            self.form.password = ''
                        }
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
