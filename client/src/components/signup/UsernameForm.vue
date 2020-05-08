<template>
    <b-form-group
        id="user"
        label="Username"
        label-for="username"
        :invalid-feedback="validUsername === false ? 'Invalid username' : ''"
        description="Username should not containt '@' character"
    >
        <b-form-input
            id="username"
            type="text"
            v-model="username"
            @change="checkUsername"
            :state="validUsername"
            autocomplete="off"
            required
        >
        </b-form-input>
    </b-form-group>
</template>

<script>
import axios from 'axios'

export default {
    name: 'UsernameForm',
    data() {
        return {
            username: '',
            validUsername: null,
        }
    },
    methods: {
        checkUsername() {
            if (this.username === '') {
                this.validUsername = null
                return
            }
            const self = this
            if (this.username.indexOf('@') !== -1) {
                self.validUsername = false
                self.$emit('username', self.username, self.validUsername)
                return false
            }
            axios
                .post(this.$url + 'exists', {
                    mail: this.username,
                })
                .then(function (response) {
                    self.validUsername = response.data.exists === 0
                    self.$emit('username', self.username, self.validUsername)
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
}
</script>

<style scoped></style>
