<template>
    <b-container fluid>
        <!--Second Navbar (declared before first navbar)-->
        <SecondNavbar :logout_prop="logout_prop" />
        <!--First Navbar (here, in order to show the dropdown items)-->
        <b-navbar id="first-nav" fixed="top" class="bg-dark">
            <b-navbar>
                <b-navbar-brand>
                    <router-link :to="{ name: 'Index' }">
                        <img id="logo" class="logo" src="@/assets/kanban.png" />
                    </router-link>
                </b-navbar-brand>
                <router-link :to="{ name: 'Index' }" class="logo-enhancement">
                    ScrumTool
                </router-link>
            </b-navbar>
            <b-navbar-nav class="ml-auto" v-if="logout_prop === false">
                <!-- Project notifications -->
                <Notifications />
                <!-- Profile, Log out -->
                <b-nav-item-dropdown class="text-font" right>
                    <template slot="button-content">
                        <img src="@/assets/menu.png" class="menu" />
                    </template>
                    <b-dropdown-item-button v-on:click="profile"
                        >Profile</b-dropdown-item-button
                    >
                    <b-dropdown-item-button v-on:click="logout"
                        >Sign out</b-dropdown-item-button
                    >
                </b-nav-item-dropdown>
            </b-navbar-nav>
            <b-navbar-nav class="ml-auto" v-if="logout_prop === true">
                <b-button class="btn" :to="{ name: 'SignIn' }">
                    Log In
                </b-button>
                <b-button class="btn" variant="info" :to="{ name: 'SignUp' }">
                    Sign Up
                </b-button>
            </b-navbar-nav>
        </b-navbar>

        <br /><br /><br />
    </b-container>
</template>

<script>
import SecondNavbar from '@/components/navbar/SecondNavbar.vue'
import Notifications from '@/components/navbar/Notifications.vue'

export default {
    name: 'Navbar',
    components: {
        SecondNavbar,
        Notifications,
    },
    methods: {
        profile() {
            this.$router.push({
                name: 'Profile',
                params: {
                    id: localStorage.getItem('username'),
                },
            })
        },
        logout() {
            localStorage.setItem('userId', 'null')
            localStorage.setItem('username', 'null')
            localStorage.setItem('auth_token', 'null')
            location.reload()
        },
    },
    props: {
        logout_prop: Boolean,
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Merienda');
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.btn {
    margin-right: 5px;
}

.logo {
    width: 30px;
    height: 30px;
}

.menu {
    width: 30px;
    position: relative;
    bottom: 0.5px;
}

.text-font {
    font-family: Quicksand;
}

.logo-enhancement {
    font-family: 'Merienda', cursive;
    text-decoration: none;
    padding-left: 15px;
    font-size: 24px;
    color: white;
}
</style>
