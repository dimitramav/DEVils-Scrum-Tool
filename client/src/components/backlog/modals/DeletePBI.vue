<template>
    <b-modal @ok="deletePBI()" :id="modalId" title="Delete PBI">
        <p class="alert alert-danger">
            Warning!! By deleting a product backlog item from the backlog,
            automatically its tasks and issues (along with its user stories if
            this PBI is an epic) are also deleted permantly from the project
        </p>
        <template v-slot:modal-footer="{ cancel, ok }">
            <b-button variant="secondary" @click="cancel()">Cancel</b-button>
            <b-button variant="danger" @click="ok()">Delete</b-button>
        </template>
    </b-modal>
</template>

<script>
import axios from 'axios'

export default {
    name: 'DeletePBI',
    props: {
        modalId: String,
        idPBI: Number,
        epicId: Number,
        idProject: Number,
        title: String,
        desc: String,
        priority: Number,
        isEpic: Boolean,
    },
    methods: {
        deletePBI() {
            const self = this
            let data = {
                title: this.title,
                description: this.desc,
                priority: this.priority,
                Project_id: this.idProject,
                idPBI: this.idPBI,
                Epic_id: this.epicId,
                isEpic: this.isEpic,
            }
            axios
                .patch(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis?isEpic=' +
                        this.isEpic,
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
                        self.$emit('delete_pbi', self.idPBI)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
}
</script>

<style scoped></style>
