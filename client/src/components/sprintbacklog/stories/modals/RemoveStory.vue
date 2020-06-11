<template>
    <b-modal
        @ok="removeStoryAndTasks(story.idPBI)"
        :id="modalId + story.idPBI"
        title="Remove User Story from Sprint Backlog"
    >
        <p class="alert alert-danger">
            Warning!! By removing a user story from this sprint backlog,
            automatically its tasks and issues are also removed from this sprint
            (but not deleted)
        </p>
        <template v-slot:modal-footer="{ cancel, ok }">
            <b-button variant="secondary" @click="cancel()">Cancel</b-button>
            <b-button variant="danger" @click="ok()">Remove</b-button>
        </template>
    </b-modal>
</template>

<script>
import axios from 'axios'

export default {
    name: 'RemoveStory',
    props: {
        story: Object,
        modalId: String,
    },
    data() {
        return {
            currentProject_id: -1,
        }
    },
    methods: {
        removeStoryAndTasks(storyId) {
            const self = this
            let pbis_list = []
            pbis_list.push({
                idPBI: storyId,
                Sprint_id: -1, // insert null on database
                Project_id: this.currentProject_id,
            })
            axios
                .patch(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.currentProject_id +
                        '/pbis',
                    pbis_list,
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
                        console.log(response)
                        self.$emit('removeStoryAndTasks', storyId)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        this.currentProject_id = parseInt(this.$route.params.id)
    },
}
</script>

<style scoped></style>
