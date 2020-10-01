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
                    v-on:click="goToProfile(notification.sender.username)"
                    href="#"
                    >{{ notification.sender.username }}</a
                >
                invited you as {{ notification.role }} in
                {{ notification.projectTitle }}.
                <br />
                <b-button
                    variant="success"
                    class="btn"
                    v-on:click="watchedNotification('accept', notification)"
                    >Accept</b-button
                >
                <b-button
                    variant="danger"
                    v-on:click="watchedNotification('decline', notification)"
                    >Decline</b-button
                >
            </div>
            <div v-else>
                {{ notification.message }}
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
                id: null,
                project: {
                    id: 0,
                    title: '',
                    isDone: false,
                    deadlineDate: '',
                },
                projectTitle: null,
                role: null,
                sender: {
                    username: null,
                },
                receiver: {
                    email: '',
                },
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
                    username: name,
                },
            })
        },
        getNotifications() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/notifications',
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
                        self.Notifications = response.data
                        // console.log('Got the notifications')
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        watchedNotification: async function (functionality, notificationItem) {
            const self = this
            // Delete the notification
            await axios
                .delete(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/notifications/' +
                        notificationItem.id,
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
                        let index = -1
                        for (let x in self.Notifications) {
                            if (
                                self.Notifications[x].id === notificationItem.id
                            ) {
                                index = x
                                break
                            }
                        }
                        if (index >= 0) {
                            self.Notifications.splice(index, 1)
                            //console.log('Notification removed')
                        } else {
                            console.log('Notif. deleted in db, not client')
                        }
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
            // Add user in project
            if (functionality === 'accept') {
                await axios
                    .post(
                        this.$url +
                            '/users/' +
                            localStorage.getItem('userId') +
                            '/projects/' +
                            notificationItem.project.id +
                            '/members',
                        notificationItem.role,
                        {
                            headers: {
                                Authorization:
                                    'Bearer ' +
                                    localStorage.getItem('auth_token'),
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                    .then(function (response) {
                        if (response.data.serverErrorMessage) {
                            console.log(response.data.serverErrorMessage)
                        } else {
                            //console.log('User inserts as member in project')
                        }
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
                //console.log(functionality)
            }
            // Send a notification back to product owner
            if (notificationItem.type === 'Accept/Decline') {
                let answer
                if (functionality === 'accept') {
                    answer = notificationItem.receiver.email + ' accepted'
                } else if (functionality === 'decline') {
                    answer = notificationItem.receiver.email + ' declined (!!!)'
                }
                notificationItem.receiver.username =
                    notificationItem.sender.username
                notificationItem.type = 'Answer-Accept/Decline'
                notificationItem.message =
                    'User ' +
                    answer +
                    ' invitation to ' +
                    notificationItem.projectTitle
                await axios
                    .post(
                        this.$url +
                            '/users/' +
                            localStorage.getItem('userId') +
                            '/notifications',
                        notificationItem,
                        {
                            headers: {
                                Authorization:
                                    'Bearer ' +
                                    localStorage.getItem('auth_token'),
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                    .then(function (response) {
                        if (response.data.serverErrorMessage) {
                            console.log(response.data.serverErrorMessage)
                        } else {
                            //console.log('Response Invitation send')
                        }
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
                if (functionality === 'accept') {
                    //location.reload()
                    this.$router.push({
                        path:
                            '/project/' +
                            notificationItem.project.id +
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
