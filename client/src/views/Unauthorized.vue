<template>
    <b-container fluid>
        <Navbar :logout_prop="true"></Navbar>
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
    components: {
        Navbar,
    },
    name: 'Unauthorized',
}
</script>

<style scoped>
.unauthorized-style {
    font-size: 250px;
}
</style>
