<template>
    <b-modal
        @ok="addTask(story.id)"
        class="text-left"
        :id="modalId + story.id"
        title="Add new task"
    >
        <b-form>
            <b-form-group
                label="Description:"
                :label-for="'addTask' + story.id"
            >
                <b-form-input
                    :id="'addTask' + story.id"
                    type="text"
                    v-model="newTask_desc"
                    required
                >
                </b-form-input>
            </b-form-group>
        </b-form>
    </b-modal>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AddTask',
    props: {
        story: Object,
        modalId: String,
    },
    data() {
        return {
            newTask_desc: '',
            currentProject_id: -1,
        }
    },
    methods: {
        addTask(storyId) {
            const self = this
            let data = {
                description: this.newTask_desc,
                state: 1,
            }
            axios
                .post(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.currentProject_id +
                        '/pbis/' +
                        storyId +
                        '/tasks',
                    data,
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
                        self.$emit('addTask', response.data)
                        self.newTask_desc = ''
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
