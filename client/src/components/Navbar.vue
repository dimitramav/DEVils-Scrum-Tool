<template>
    <b-container fluid>
        <b-navbar id="first-nav" fixed="top" class="bg-dark">
            <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
            <b-navbar>
                <b-navbar-brand>
                    <router-link :to="{ name: 'Index' }">
                        <img src="@/assets/kanban.png" width="30" height="30" />
                    </router-link>
                </b-navbar-brand>
                <router-link :to="{ name: 'Index' }" class="logo-enhancement">
                    ScrumTool
                </router-link>
            </b-navbar>
            <b-collapse is-nav id="nav_collapse" v-if="logout_prop === false">
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <!-- Project notifications -->
                    <b-nav-item-dropdown class="pcsprint" right>
                        <template slot="button-content">
                            <em v-if="Notifications.length === 0">
                                <img
                                    src="../assets/notification-Off.png"
                                    style="width: 25px;"
                                />
                            </em>
                            <em v-else>
                                <img
                                    src="../assets/notification-On.png"
                                    style="width: 30px;"
                                />
                            </em>
                        </template>
                        <div v-if="Notifications.length === 0">
                            <b-dropdown-item disabled>
                                You don't have any notifications...
                            </b-dropdown-item>
                        </div>
                        <div
                            v-else
                            v-for="notification in Notifications"
                            v-bind:key="notification"
                        >
                            <b-dropdown-item disabled>
                                <div
                                    v-if="
                                        notification.type === 'Accept/Decline'
                                    "
                                >
                                    User
                                    <a
                                        v-on:click="
                                            goToProfile(
                                                notification.FromUsername
                                            )
                                        "
                                        href="#"
                                        >{{ notification.FromUsername }}</a
                                    >
                                    invited you as {{ notification.role }} in
                                    {{ notification.projectTitle }}.
                                    <br />
                                    <b-button
                                        variant="primary"
                                        v-on:click="
                                            watchedNotification(
                                                'accept',
                                                notification
                                            )
                                        "
                                        >Accept</b-button
                                    >
                                    <b-button
                                        variant="secondary"
                                        v-on:click="
                                            watchedNotification(
                                                'decline',
                                                notification
                                            )
                                        "
                                        >Decline</b-button
                                    >
                                </div>
                                <div v-else>
                                    {{ notification.text }}
                                    <br />
                                    <b-button
                                        variant="primary"
                                        v-on:click="
                                            watchedNotification(
                                                'decline',
                                                notification
                                            )
                                        "
                                        >OK</b-button
                                    >
                                </div>
                            </b-dropdown-item>
                        </div>
                    </b-nav-item-dropdown>
                    <!-- Setting notifications -->
                    <b-nav-item-dropdown class="pcsprint" right>
                        <template slot="button-content">
                            <em>
                                <img
                                    src="../assets/menu.png"
                                    style="
                                        width: 30px;
                                        position: relative;
                                        bottom: 0.5px;
                                    "
                                />
                            </em>
                        </template>
                        <b-dropdown-item-button v-on:click="profile"
                            >Profile</b-dropdown-item-button
                        >
                        <!--<b-dropdown-item-button v-on:click="editprofile">Edit Profile</b-dropdown-item-button>-->
                        <b-dropdown-item-button v-on:click="logout"
                            >Sign out</b-dropdown-item-button
                        >
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </b-collapse>
            <b-navbar-nav class="ml-auto" v-if="logout_prop === true">
                <b-button class="btn" :to="{ name: 'SignIn' }">
                    Log In
                </b-button>
                <b-button class="btn" variant="info" :to="{ name: 'SignUp' }">
                    Sign Up
                </b-button>
            </b-navbar-nav>
        </b-navbar>
        <b-navbar id="second-nav" fixed="top" class="subnav">
            <b-navbar-nav v-if="logout_prop === true" class="mx-auto">
                <b-nav-item href="#home">
                    <p class="anchors">Home</p>
                </b-nav-item>
                <b-col></b-col>
                <b-nav-item href="#features">
                    <p class="anchors">Features</p>
                </b-nav-item>
                <b-col></b-col>
                <b-nav-item href="#about">
                    <p class="anchors">About</p>
                </b-nav-item>
            </b-navbar-nav>
        </b-navbar>
        <br /><br /><br />
        <b-navbar
            v-if="dashboard"
            toggleable="md"
            class="navbar navbar-expand-lg navbar-static-top subnav second-nav"
            variant="primary"
            type="dark"
            fluid
        >
            <b-navbar-toggle target="subnav_collapse"></b-navbar-toggle>
            <b-collapse is-nav id="subnav_collapse">
                <b-navbar-nav style="padding-left: 15px;">
                    <b-nav-item
                        :to="{
                            name: 'ProjectPageOverview',
                            params: { id: $route.params.id },
                        }"
                    >
                        <p class="text-enhancement2">Overview</p>
                    </b-nav-item>
                    <b-col></b-col>
                    <b-nav-item
                        :to="{
                            name: 'Backlog',
                            params: { id: $route.params.id },
                        }"
                    >
                        <p class="text-enhancement2">Backlog</p>
                    </b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </b-container>
</template>

<script>
import axios from 'axios'

export default {
    name: 'Navbar',
    data() {
        return {
            notificationStruct: {
                idNotification: null,
                Project_id: 0,
                projectTitle: null,
                role: null,
                FromUsername: null,
                ToUserEmail: '',
                type: '',
            },
            Notifications: [],
        }
    },
    methods: {
        goToProfile(name) {
            this.$router.push({
                name: 'Profile',
                params: {
                    id: name,
                },
            })
        },
        logout() {
            localStorage.setItem('userId', 'null')
            localStorage.setItem('username', 'null')
            localStorage.setItem('auth_token', 'null')
            location.reload()
        },
        profile() {
            this.$router.push({
                name: 'Profile',
                params: {
                    id: localStorage.getItem('username'),
                },
            })
        },
        /*editprofile() {
          this.$router.push({path: '/editprofile'});
        },*/
        getNotifications() {
            const self = this
            axios
                .get(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/notifications',
                    {
                        headers: {
                            auth: localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.error) {
                        if (response.data.error === 'Unauthorized user') {
                            console.log('Unauthorized user')
                        } else if (
                            response.data.error === 'Unauthorized notifications'
                        ) {
                            console.log('Unauthorized notifications')
                        } else if (response.data.error === 'null') {
                            console.log('Null token')
                        }
                    }
                    if (response.data.results) {
                        self.Notifications = response.data.results
                        console.log('Got the notifications')
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        watchedNotification(functionality, notificationItem) {
            const self = this
            if (functionality === 'accept') {
                axios
                    .put(
                        this.$url +
                            'users/' +
                            localStorage.getItem('userId') +
                            '/notifications',
                        notificationItem.idNotification,
                        {
                            headers: {
                                auth: localStorage.getItem('auth_token'),
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                    .then(function (response) {
                        if (response.data.error) {
                            console.log(response.data.error)
                        }
                        if (response.data.results) {
                            if (response.data.results === 1) {
                                let index = -1
                                for (let x in self.Notifications) {
                                    if (
                                        self.Notifications[x].idNotification ===
                                        notificationItem.idNotification
                                    ) {
                                        index = x
                                        break
                                    }
                                }
                                if (index >= 0) {
                                    self.Notifications.splice(index, 1)
                                    console.log('Notification removed')
                                }
                            } else {
                                console.log('Notification did not delete')
                            }
                        }
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
                // Add user in project
                let data = {
                    mail: notificationItem.ToUserEmail,
                    role: notificationItem.role,
                }
                axios
                    .post(
                        this.$url +
                            'users/' +
                            localStorage.getItem('userId') +
                            '/projects/' +
                            notificationItem.Project_id +
                            '/members',
                        data,
                        {
                            headers: {
                                auth: localStorage.getItem('auth_token'),
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                    .then(function (response) {
                        if (response.data.error) {
                            console.log(response.data.error)
                        }
                        if (response.data.results) {
                            console.log(
                                'User inserted as member in the project'
                            )
                        }
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
                console.log(functionality)
            } else if (functionality === 'decline') {
                axios
                    .put(
                        this.$url +
                            'users/' +
                            localStorage.getItem('userId') +
                            '/notifications',
                        notificationItem.idNotification,
                        {
                            headers: {
                                auth: localStorage.getItem('auth_token'),
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                    .then(function (response) {
                        if (response.data.error) {
                            console.log(response.data.error)
                        }
                        if (response.data.results) {
                            if (response.data.results === 1) {
                                var index = -1
                                for (var x in self.Notifications) {
                                    if (
                                        self.Notifications[x].idNotification ===
                                        notificationItem.idNotification
                                    ) {
                                        index = x
                                        break
                                    }
                                }
                                if (index >= 0) {
                                    self.Notifications.splice(index, 1)
                                    console.log('Notification removed')
                                }
                            } else {
                                console.log('Notification did not delete')
                            }
                        }
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
                console.log(functionality)
            }
            // Send a notification back to product owner
            if (notificationItem.type === 'Accept/Decline') {
                let answer
                if (functionality === 'accept') {
                    answer = notificationItem.ToUserEmail + ' accepted'
                } else if (functionality === 'decline') {
                    answer = notificationItem.ToUserEmail + ' declined (!!!)'
                }
                self.notificationStruct.ToUserEmail =
                    notificationItem.FromUsername
                self.notificationStruct.type = 'Answer-Accept/Decline'
                self.notificationStruct.text =
                    'User ' +
                    answer +
                    ' invitation to ' +
                    notificationItem.projectTitle
                axios
                    .post(
                        this.$url +
                            'users/' +
                            localStorage.getItem('userId') +
                            '/notifications',
                        self.notificationStruct,
                        {
                            headers: {
                                auth: localStorage.getItem('auth_token'),
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                    .then(function (response) {
                        if (response.data.error) {
                            console.log(response.data.error)
                        }
                        if (response.data.results) {
                            console.log('Response Invitation send')
                        }
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
                if (functionality === 'accept') {
                    //location.reload(true);
                    this.$router.push({
                        path:
                            '/project/' +
                            notificationItem.Project_id +
                            '/overview',
                    })
                }
            }
        },
    },
    mounted() {
        this.getNotifications()
    },
    props: {
        dashboard: Boolean,
        logout_prop: Boolean,
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Merienda');
@import url('https://fonts.googleapis.com/css?family=VT323');
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.btn {
    margin-right: 5px;
}

.ptitle {
    font-family: Merienda;
}

.pdeadline {
    font-family: VT323;
    font-size: 24px;
    margin-top: 1%;
}

.pcsprint {
    font-family: Quicksand;
}

.Navigation {
    position: absolute;
    top: 0;
    left: 0;
}

.line {
    width: 99%;
    font-size: 1px;
    line-height: 2px;
    background-color: lavender;
    margin-top: -6px;
    margin-bottom: 10px;
}

.container-fluid {
    padding-right: 0;
    padding-left: 0;
}

.logo-enhancement {
    font-family: 'Merienda', cursive;
    text-decoration: none;
    padding-left: 15px;
    font-size: 24px;
    color: white;
}

.text-enhancement2 {
    font-family: 'Quicksand', cursive;
    font-size: 17px;
    font-weight: normal;
    color: white;
    margin-top: 5px;
    margin-bottom: 5px;
}

.anchors {
    font-family: 'Merienda', cursive;
    color: white;
    font-style: italic;
    font-weight: 450;
    margin-top: 5px;
    margin-bottom: 5px;
}

.subnav {
    background-color: #494965 !important;
    top: 70px;
    padding-top: 4px;
    padding-bottom: 2px;
    line-height: 2px;
}

.navbar {
    min-height: 20px;
    max-height: 500px;
}

.navbar-toggle {
    /* (80px - button height 34px) / 2 = 23px */
    margin-top: 23px;
    padding: 9px 10px !important;
}
</style>
