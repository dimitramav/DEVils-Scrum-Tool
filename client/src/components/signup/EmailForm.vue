<template>
    <b-form-group
        id="emailForm"
        label="Email address"
        label-for="email"
        :invalid-feedback="validEmail === false ? 'Invalid email' : ''"
        description="As this is a github project, add a fake email if you like"
    >
        <b-form-input
            id="emailInput"
            type="email"
            v-model="email"
            @change="checkEmail"
            :state="validEmail"
            autocomplete="off"
            required
        >
        </b-form-input>
    </b-form-group>
</template>

<script>
import axios from 'axios'

export default {
    name: 'EmailForm',
    data() {
        return {
            email: '',
            validEmail: null,
        }
    },
    methods: {
        checkEmail() {
            if (this.email === '') {
                this.validEmail = null
                return
            }
            const self = this
            if (this.email.indexOf('@') === -1) {
                self.validEmail = false
                self.$emit('email', self.email, self.validEmail)
                return false
            }
            axios
                .get(this.$url + '/exists/email/' + this.email)
                .then(function (response) {
                    // If email not found in db, then it can be used
                    self.validEmail = !response.data
                    if (self.validEmail == false) {
                        console.log('Email: ' + self.email + ' is used')
                    }
                    self.$emit('email', self.email, self.validEmail)
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
}
</script>

<style scoped></style>
