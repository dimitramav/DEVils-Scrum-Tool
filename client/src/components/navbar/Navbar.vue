<template>
    <b-container fluid>
        <!--Second Navbar when needed (declared before first navbar)-->
        <div v-if="dashboard === true">
            <SecondNavbar :logout_prop="logout_prop" />
        </div>
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
import axios from 'axios'
import SecondNavbar from '@/components/navbar/SecondNavbar.vue'
import Notifications from '@/components/navbar/Notifications.vue'

export default {
    name: 'Navbar',
    components: {
        SecondNavbar,
        Notifications,
    },
    props: {
        logout_prop: {
            type: Boolean,
            default: true,
        },
        dashboard: {
            type: Boolean,
            default: true,
        },
    },
    methods: {
        profile() {
            if (this.$router.currentRoute.name == 'Profile') {
                this.$router.push({
                    name: 'Profile',
                    params: {
                        username: localStorage.getItem('username'),
                    },
                })
                location.reload()
            } else {
                this.$router.push({
                    name: 'Profile',
                    params: {
                        username: localStorage.getItem('username'),
                    },
                })
            }
        },
        logout() {
            localStorage.setItem('userId', 'null')
            localStorage.setItem('username', 'null')
            localStorage.setItem('auth_token', 'null')
            if (this.$router.currentRoute.name == 'Index') {
                location.reload()
            } else {
                this.$router.push({
                    name: 'Index',
                })
            }
        },
        projectAuthorization() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/projectAuthorization',
                    {
                        headers: {
                            Authorization:
                                'Bearer ' + localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.serverErrorMessage) {
                        console.log(response.data.serverErrorMessage)
                    } else {
                        if (response.data == false) {
                            self.$router.push({
                                path: '/unauthorized',
                            })
                        }
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        if (this.$route.params.id != null) {
            this.projectAuthorization()
        }
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
