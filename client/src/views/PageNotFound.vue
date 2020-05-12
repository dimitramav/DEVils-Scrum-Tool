<template>
    <b-container fluid>
        <Navbar :logout_prop="true"></Navbar>
        <b-row>
            <b-col></b-col>
            <b-col>
                <h1 class="notfound-style">404</h1>
                <h1>Oops! Page not found</h1>
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
    name: 'PageNotFound',
}
</script>

<style scoped>
.notfound-style {
    font-size: 250px;
}
</style>
