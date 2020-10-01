<template>
    <div>
        <b-form-group
            id="pass"
            label="Password"
            label-for="password"
            description="Password must contain at least 8 characters, at least 1 number and 1 special character"
        >
            <b-form-input
                id="password"
                type="password"
                v-model="password"
                :state="checkPassword"
                required
            >
            </b-form-input>
        </b-form-group>
        <b-form-group
            id="verify"
            label="Verify password"
            label-for="verify_password"
        >
            <b-form-input
                id="verify_password"
                type="password"
                v-model="verify_password"
                :state="verifyPassword"
                required
            >
            </b-form-input>
        </b-form-group>
    </div>
</template>

<script>
export default {
    name: 'PasswordForm',
    data() {
        return {
            password: '',
            verify_password: '',
            validPassword: null,
        }
    },
    computed: {
        verifyPassword() {
            if (this.verify_password === '') {
                //console.log('p', this.password, this.validPassword)
                return null
            }
            if (
                this.password === this.verify_password &&
                this.password !== '' &&
                this.validPassword === true
            ) {
                //console.log('p1', this.password, this.validPassword)
                this.$emit('password', this.password, true)
                return true
            } else {
                //console.log('p2', this.password, this.validPassword)
                this.$emit('password', this.password, false)
                return false
            }
        },
        checkPassword() {
            let passCheck = null
            if (this.password === '') {
                //console.log('c', this.password, this.validPassword)
                return null
            }
            const regex = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
            if (this.password.match(regex)) {
                passCheck = true
                //console.log('c1', this.password, this.validPassword)
                return passCheck
            } else {
                passCheck = false
                //console.log('c2', this.password, this.validPassword)
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
    },
}
</script>

<style scoped></style>
