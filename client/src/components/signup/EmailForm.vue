<template>
    <b-form-group
        id="emailForm"
        label="Email address"
        label-for="email"
        :invalid-feedback="validEmail === false ? 'Invalid email' : ''"
        description="We'll never share your email with anyone else."
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
                .post(this.$url + 'exists', {
                    mail: this.email,
                })
                .then(function (response) {
                    self.validEmail = response.data.exists === 0
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
