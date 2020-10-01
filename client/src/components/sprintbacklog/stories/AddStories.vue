<template>
    <b-container class="text-font btn-margin" v-if="!isDeveloper()">
        <b-row align-h="center" style="margin-top: 20px;">
            <b-button
                v-b-toggle.addStory
                size="lg"
                variant="primary"
                @click="emptyDropdownVars"
            >
                Add User Stories in Sprint Backlog
            </b-button>
        </b-row>
        <b-collapse id="addStory" class="mt-2" v-if="ok === false">
            <b-card class="card-text text-font">
                <b-row>
                    <b-col class="text-center">
                        <LoadEpics
                            v-on:stories_options="editStoriesOptions"
                            :updateBacklogProp="ok"
                        />
                        <b-button
                            variant="success"
                            style="margin-top: 100px;"
                            size="lg"
                            type="submit"
                            @click="addUserStories"
                        >
                            Add Stories</b-button
                        >
                    </b-col>
                    <b-col class="text-right" style="margin-right: 50px;">
                        <SelectStories
                            v-on:selected_stories="editSelectedStories"
                            :stories_options="stories_options"
                            :updateBacklogProp="ok"
                        />
                    </b-col>
                </b-row>
            </b-card>
        </b-collapse>
    </b-container>
</template>

<script>
import axios from 'axios'
import LoadEpics from '@/components/newsprint/LoadEpics.vue'
import SelectStories from '@/components/newsprint/SelectStories.vue'

export default {
    name: 'AddStories',
    components: {
        LoadEpics,
        SelectStories,
    },
    props: {
        currentStories: Array,
    },
    data() {
        return {
            currentProject_id: 0,
            currentSprint_id: 0,
            ok: false,
            stories_options: [],
            selected_stories: [],
            pbis_list: [],
        }
    },
    methods: {
        emptyDropdownVars() {
            this.ok = false
            this.stories_options = []
            this.selectedStories = []
            this.pbis_list = []
        },
        editStoriesOptions(loadedStories) {
            this.stories_options = loadedStories
        },
        editSelectedStories(selectedStories) {
            this.selected_stories = selectedStories
        },
        addUserStories() {
            const self = this
            for (var i = 0; i < this.selected_stories.length; i++) {
                if (
                    this.currentStories.findIndex(
                        (o) => o.id === this.selected_stories[i].value
                    ) === -1
                ) {
                    this.pbis_list.push({
                        idPBI: this.selected_stories[i].value,
                        sprintId: this.currentSprint_id,
                        projectId: this.currentProject_id,
                    })
                }
            }
            axios
                .put(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.currentProject_id +
                        '/pbis/sprintUpdate',
                    this.pbis_list,
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
                        //location.reload()
                        self.ok = true
                        self.$emit('addStories')
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        isDeveloper() {
            if (localStorage.getItem('teamRole') == 'Developer') {
                return true
            } else {
                return false
            }
        },
    },
    mounted() {
        this.currentProject_id = parseInt(this.$route.params.id)
        this.currentSprint_id = parseInt(this.$route.params.sprintId)
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.btn-margin {
    margin-top: 50px;
    margin-bottom: 50px;
}
</style>
