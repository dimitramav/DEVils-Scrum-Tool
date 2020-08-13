<template>
    <b-container>
        <b-row>
            <b-col></b-col>
            <b-col class="w-100">
                <router-link :to="{ name: 'Index' }">
                    <img src="@/assets/kanban.png" style="width: 50%;" />
                </router-link>
                <br /><br /><br />
                <b-form @submit.stop.prevent>
                    <b-row>
                        <b-col cols="15">
                            <p class="text">
                                Well it happens, type your email and we will
                                send you a retrieval link! (NOT WORKING)
                            </p>
                        </b-col>
                    </b-row>
                    <b-form-group id="email_group" class="text">
                        <b-form-input
                            id="email"
                            type="email"
                            v-model="form.email"
                            autocomplete="off"
                            placeholder="Enter email (NOT WORKING)"
                            required
                        >
                        </b-form-input>
                    </b-form-group>
                    <br /><br />
                    <b-btn v-b-modal.modalPopUp variant="primary" class="text"
                        >Send Retrieval Link!</b-btn
                    >
                    <b-modal id="modalPopUp" class="text">
                        <p class="my-4">
                            🐦 We are out of post pigeons, better luck next time
                            🐦
                        </p>
                    </b-modal>
                    <br /><br /><br />
                </b-form>
            </b-col>
            <b-col></b-col>
        </b-row>
    </b-container>
</template>

<script>
export default {
    name: 'ForgotPass',
    data() {
        return {
            form: {
                email: '',
            },
        }
    },
    mounted() {
        //redirect to user homepage if user is already logged in
        if (
            localStorage.getItem('auth_token') !== 'null' &&
            localStorage.getItem('userId') !== 'null'
        )
            this.$router
                .push({
                    path: '/',
                })
                .catch((err) => err)
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text {
    font-family: Quicksand;
    font-size: 15px;
}
</style>
