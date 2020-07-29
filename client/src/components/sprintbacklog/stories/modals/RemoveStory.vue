<template>
    <b-modal
        @ok="removeStoryAndTasks(story.id)"
        :id="modalId + story.id"
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
                sprintId: -1, // insert null on database
                projectId: this.currentProject_id,
            })
            axios
                .put(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.currentProject_id +
                        '/pbis/sprintUpdate',
                    pbis_list,
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
