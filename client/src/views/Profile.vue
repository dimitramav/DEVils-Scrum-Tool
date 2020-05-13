<template>
    <b-container fluid>
        <Navbar :logout_prop="false" />
        <br />
        <b-card
            v-if="userInfos.mail != null"
            class="mb-2 image"
            img-src="https://picsum.photos/600/300?image=445"
            img-alt="Image"
            img-top
        >
            <div style="text-align: left;">
                <h2 class="card-title text-font">
                    {{ userInfos.firstname }} {{ userInfos.lastname }}
                </h2>
                <p class="text-font">
                    Username: <b>{{ userInfos.username }}</b>
                </p>
                <p class="text-font">
                    Email : <b>{{ userInfos.mail }}</b
                    ><br />
                </p>
                <p class="text-font" v-if="userInfos.country != null">
                    Country : <b>{{ userInfos.country }}</b>
                </p>
                <p class="text-font" v-if="userInfos.job != null">
                    Job : <b>{{ userInfos.job }}</b>
                </p>
                <p class="text-font" v-if="userInfos.company != null">
                    Works at: <b>{{ userInfos.company }}</b>
                </p>
                <p class="text-font">
                    Number of projects: <b>{{ userInfos.numProjects }}</b>
                </p>
            </div>
            <b-row><p></p></b-row>
            <b-card-footer class="text-muted">
                {{ userInfos.description }}
            </b-card-footer>
            <b-row><p></p></b-row>
            <b-button
                class="text-font"
                variant="primary"
                v-show="loggedin_userId === userInfos.idUser"
                @click="gotoEdit"
                >Edit Profile</b-button
            >
        </b-card>
        <b-card
            v-else
            class="mb-2 image"
            img-src="https://picsum.photos/600/300?image=1062"
            img-alt="Image"
            img-top
        >
            <div style="text-align: left;">
                <h2 class="card-title text-font">User Not Found</h2>
            </div>
        </b-card>
    </b-container>
</template>

<script>
import axios from 'axios'
import Navbar from '@/components/navbar/Navbar.vue'

export default {
    name: 'Profile',
    components: {
        Navbar,
    },
    data() {
        return {
            userInfos: [],
            loggedin_userId: 0,
            current_username: '',
        }
    },
    methods: {
        gotoEdit() {
            this.$router.push({
                path: '/editprofile',
            })
        },
        getProfile() {
            const self = this
            axios
                .get(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/profile/' +
                        self.current_username,
                    {
                        headers: {
                            auth: localStorage.getItem('auth_token'),
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.results) {
                        self.userInfos = response.data.results
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        if (
            localStorage.getItem('auth_token') === 'null' ||
            localStorage.getItem('userId') === 'null'
        )
            return
        this.current_username = this.$route.params.id
        this.loggedin_userId = parseInt(localStorage.getItem('userId'))
        this.getProfile()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.image {
    max-width: 19rem;
    margin: 0 auto;
    float: none;
    margin-bottom: 10px;
}
</style>
