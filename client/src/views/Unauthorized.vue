<template>
    <b-container fluid>
        <Navbar :logout_prop="logged_out" :dashboard="false"></Navbar>
        <b-row>
            <b-col></b-col>
            <b-col>
                <h1 class="unauthorized-style">401</h1>
                <h1>Oops! Unauthorized Access</h1>
                <p>You don't have permission to access this page</p>
                <h1>
                    <router-link :to="{ name: 'Index' }"
                        >Back to Homepage</router-link
                    >
                </h1>
            </b-col>
            <b-col></b-col>
        </b-row>
    </b-container>
</template>

<script>
import Navbar from '@/components/navbar/Navbar.vue'

export default {
    name: 'Unauthorized',
    components: {
        Navbar,
    },
    data() {
        return {
            logged_out: true,
        }
    },
    mounted() {
        if (
            localStorage.getItem('auth_token') === 'null' ||
            localStorage.getItem('userId') === 'null'
        ) {
            this.logged_out = true
        } else {
            this.logged_out = false
        }
    },
}
</script>

<style scoped>
.unauthorized-style {
    font-size: 250px;
}
</style>
