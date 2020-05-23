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
import config from '@/router/auth.js'

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
        }
    },
    methods: {
        submit() {
            const self = this
            axios
                .post(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.sprint.Project_id +
                        '/sprints',
                    this.sprint,
                    config
                )
                .then(function (response) {
                    for (var i = 0; i < self.selected_stories.length; i++) {
                        self.pbis_list.push({
                            idPBI: self.selected_stories[i].value,
                            Sprint_id: response.data.Sprint_id,
                            Project_id: self.sprint.Project_id,
                        })
                    }
                    console.log(self.pbis_list)
                    axios
                        .patch(
                            self.$url +
                                'users/' +
                                localStorage.getItem('userId') +
                                '/projects/' +
                                self.sprint.Project_id +
                                '/pbis',
                            self.pbis_list,
                            config
                        )
                        .then(function (response) {
                            console.log(response)
                            self.$router.push({
                                path:
                                    '/project/' +
                                    self.sprint.Project_id +
                                    '/overview/',
                            })
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
