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
    },
    methods: {
        deletePBI() {
            const self = this
            axios
                .delete(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis/' +
                        this.idPBI,
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
