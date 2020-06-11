<template>
    <b-col class="text-right">
        <b-button @click="openModal()">
            <img src="@/assets/edit.png" class="btn-img" />
            Edit
        </b-button>
        <b-modal v-model="modalShow" title="Edit" @ok="updateTask()">
            <b-form class="text-left" id="updateEpic">
                <b-form-group
                    label="Description:"
                    :label-for="'editTask' + Task_id"
                >
                    <b-form-input
                        :id="'editTask' + Task_id"
                        type="text"
                        v-model="updateTask_desc"
                        required
                    >
                    </b-form-input>
                </b-form-group>
            </b-form>
            <template v-slot:modal-footer="{ remove, cancel, ok }">
                <b-btn class="mr-auto" variant="danger" @click="deleteTask()">
                    Delete Task (and its Issues)</b-btn
                >
                <b-btn variant="secondary" @click="cancel()">Cancel</b-btn>
                <b-btn variant="primary" @click="ok()">OK</b-btn>
            </template>
        </b-modal>
    </b-col>
</template>

<script>
import axios from 'axios'

export default {
    name: 'EditTask',
    props: {
        desc: String,
        PBI_id: Number,
        state: Number,
        Task_id: Number,
    },
    data: function () {
        return {
            updateTask_desc: '',
            modalShow: false,
        }
    },
    methods: {
        openModal() {
            //console.log(typeof(this.epicId));
            this.updateTask_desc = this.desc
            this.modalShow = !this.modalShow
        },
        updateTask() {
            const self = this
            let data = {
                PBI_id: this.PBI_id,
                description: this.updateTask_desc,
                state: this.state,
                idTask: this.Task_id,
            }
            axios
                .put(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/tasks',
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
                    if (response.data.task) {
                        console.log(response.data.task)
                        self.$emit('editTask', response.data.task)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        deleteTask() {
            const self = this
            let data = {
                PBI_id: this.PBI_id,
                description: this.desc,
                state: this.state,
                idTask: this.Task_id,
            }
            axios
                .patch(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/tasks',
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
                    if (response.data.task) {
                        console.log('Task and its Issues deleted')
                        self.modalShow = false
                        self.$emit('deleteTask', self.Task_id)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
}
</script>

<style scoped>
.btn-img {
    width: 20px;
    margin-right: 2px;
    margin-bottom: 2px;
}
</style>
