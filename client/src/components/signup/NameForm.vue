<template>
    <div>
        <b-form-group id="first" label="First Name" label-for="firstname">
            <b-form-input
                id="firstname"
                type="text"
                v-model="firstname"
                @change="firstName"
                required
            >
            </b-form-input>
        </b-form-group>
        <b-form-group id="last" label="Last Name" label-for="lastname">
            <b-form-input
                id="lastname"
                type="text"
                v-model="lastname"
                @change="lastName"
                required
            >
            </b-form-input>
        </b-form-group>
    </div>
</template>

<script>
export default {
    name: 'NameForm',
    data() {
        return {
            firstname: '',
            lastname: '',
        }
    },
    methods: {
        firstName() {
            this.$emit('firstname', this.firstname)
        },
        lastName() {
            this.$emit('lastname', this.lastname)
        },
    },
}
</script>

<style scoped></style>
