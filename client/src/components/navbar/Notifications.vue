<template>
    <b-nav-item-dropdown class="text-font" right>
        <template slot="button-content">
            <em v-if="Notifications.length === 0">
                <img src="@/assets/notification-Off.png" style="width: 25px;" />
            </em>
            <em v-else>
                <img src="@/assets/notification-On.png" style="width: 30px;" />
            </em>
        </template>
        <b-dropdown-item v-if="Notifications.length === 0" disabled>
            You don't have any notifications...
        </b-dropdown-item>
        <b-dropdown-item
            v-else
            v-for="(notification, idx) in Notifications"
            v-bind:key="idx"
        >
            <div v-if="notification.type === 'Accept/Decline'">
                User
                <a
                    v-on:click="goToProfile(notification.FromUsername)"
                    href="#"
                    >{{ notification.FromUsername }}</a
                >
                invited you as {{ notification.role }} in
                {{ notification.projectTitle }}.
                <br />
                <b-button
                    variant="primary"
                    class="btn"
                    v-on:click="watchedNotification('accept', notification)"
                    >Accept</b-button
                >
                <b-button
                    variant="secondary"
                    v-on:click="watchedNotification('decline', notification)"
                    >Decline</b-button
                >
            </div>
            <div v-else>
                {{ notification.text }}
                <br />
                <b-button
                    variant="primary"
                    v-on:click="watchedNotification('decline', notification)"
                    >OK</b-button
                >
            </div>
        </b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
import axios from 'axios'

export default {
    name: 'Notifications',
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
                        console.log(response.data.error)
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
            if (functionality === 'accept' || functionality === 'decline') {
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
                if (functionality === 'accept') {
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
                                console.log('User inserts as member in project')
                            }
                        })
                        .catch(function (error) {
                            console.log(error)
                        })
                    console.log(functionality)
                }
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
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.btn {
    margin-right: 5px;
}

.text-font {
    font-family: Quicksand;
}
</style>
