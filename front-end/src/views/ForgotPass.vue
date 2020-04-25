<template>
<b-container class="Navigation" fluid>
    <b-row>
        <b-col></b-col>
        <b-col class="w-100">
            <b-img style="width: 35%" src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png"
                    v-on:click="gotoIndex"></b-img>
            <br><br>
            <b-form @submit="onSubmit">
                <b-alert class="pcsprint" show variant="warning" v-if="wrongLogin===true">Invalid email</b-alert>
                <br><br>
                <b-row>
                    <b-col cols="15">
                        <p class="pcsprint" style="font-size:15px;"> Well it happens, type your email and we will send you a retrieval link!</p>
                    </b-col>
                </b-row>
                <b-form-group id="mail_group" class="pcsprint">
                    <b-form-input id="mail" type="email" v-model="form.mail" autocomplete="off" placeholder="Enter email" required>
                    </b-form-input>
                </b-form-group>
                <br><br>
                <b-btn v-b-modal.modal1 variant="primary" class="pcsprint">Send Retrieval Link!</b-btn>
                <b-modal id="modal1" class="pcsprint">
                    <p class="my-4"> 🐦 We are out of post pigeons, better luck next time 🐦 </p>
                </b-modal>
                <br><br><br>
            </b-form>
        </b-col>
        <b-col></b-col>
    </b-row>
</b-container>
</template>



<script>
import axios from 'axios'

export default {
    data() {
        return {
            form: {
                mail: '',
                password: '',
            },
            wrongLogin: false,
        }
    },
    methods: {
        onSubmit(evt) {

        },
        gotoIndex() {
            this.$router.push({
                path: '/'
            });
        },
        gotoSignIn() {
            this.$router.push({
                path: '/signin'
            });
        },
    },
    mounted() {
        //redirect to user homepage if user is already logged in
        if (localStorage.getItem('auth_token') !== 'null' && localStorage.getItem('userId') !== 'null') this.$router.push({
            path: '/'
        });
    },
}
</script>



<style scoped>
@import url('https://fonts.googleapis.com/css?family=Merienda');
@import url('https://fonts.googleapis.com/css?family=VT323');
@import url('https://fonts.googleapis.com/css?family=Quicksand');

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

.Title {
    position: absolute;
    top: 0;
    left: 0;
}
</style>
