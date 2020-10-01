<template>
    <b-button
        id="submitBtn"
        class="button"
        type="submit"
        size="lg"
        variant="success"
        @click="submit"
        :disabled="validDate === false"
        >Create
    </b-button>
</template>

<script>
import axios from 'axios'

export default {
    name: 'SubmitButton',
    props: {
        sprint: Object,
        selected_stories: Array,
        validDate: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            pbis_list: [],
            newSprintId: -1,
        }
    },
    methods: {
        submit() {
            const self = this
            axios
                .post(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.sprint.project.id +
                        '/sprints',
                    this.sprint,
                    {
                        headers: {
                            Authorization:
                                'Bearer ' + localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    self.newSprintId = response.data.id
                    // Store all stories selected to update their sprintId
                    for (var i = 0; i < self.selected_stories.length; i++) {
                        self.pbis_list.push({
                            idPBI: self.selected_stories[i].value,
                            sprintId: self.newSprintId,
                            projectId: self.sprint.project.id,
                        })
                    }
                    //console.log(self.pbis_list)
                    axios
                        .put(
                            self.$url +
                                '/users/' +
                                localStorage.getItem('userId') +
                                '/projects/' +
                                self.sprint.project.id +
                                '/pbis/sprintUpdate',
                            self.pbis_list,
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
                                self.$router.push({
                                    path:
                                        '/project/' +
                                        self.sprint.project.id +
                                        '/sprintbacklog/' +
                                        self.newSprintId,
                                })
                            }
                        })
                        .catch(function (error) {
                            console.log(error)
                        })
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
}
</script>

<style scoped>
.button {
    padding-left: 100px;
    padding-right: 100px;
}
</style>
