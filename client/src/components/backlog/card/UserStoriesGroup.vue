<template>
    <b-collapse
        :id="'collapse' + current_epic.id"
        class="mt-2 stories-field text-font"
    >
        <draggable
            v-model="currentUserStories[current_epic.id]"
            :disabled="isDeveloper()"
            :move="onUserStoryMove"
            @change="onUserStoryChange"
            group="UserStories"
        >
            <b-card-group
                v-for="cur_us in currentUserStories[current_epic.id]"
                :key="cur_us.id"
                class="stories-card-group"
                deck
            >
                <StoryCard
                    v-on:editStory="editCurrentUserStories"
                    :currentUserStories="currentUserStories"
                    :current_epic_id="current_epic.id"
                    :cur_us="cur_us"
                />
                <div v-show="cur_us.id === -1">
                    <b>No User Stories yet.</b><br />
                    <i>Create a new one or drag one here from another epic</i>
                </div>
            </b-card-group>
        </draggable>
    </b-collapse>
</template>

<script>
import axios from 'axios'
import draggable from 'vuedraggable'
import StoryCard from '@/components/backlog/card/StoryCard.vue'

export default {
    name: 'UserStoriesGroup',
    props: {
        current_epic: Object,
        currentUserStories: Array,
        currentEpicId: Number,
    },
    components: {
        StoryCard,
        draggable,
    },
    methods: {
        onUserStoryMove({ relatedContext, draggedContext }) {
            console.log('On Move:', relatedContext, draggedContext)
            let newCurrentEpicId = relatedContext.element.epic.id
            this.$emit('newCurrentEpicId', newCurrentEpicId)
        },
        onUserStoryChange(input) {
            console.log('Dragged', input)
            // Update the dragged story with epic's info in which is moved into
            if (input.added) {
                // For an empty epic (no stories) change its stories array
                let storyItem = this.currentUserStories[this.currentEpicId]
                if (storyItem.length === 2) {
                    if (storyItem[0].id === -1) {
                        storyItem.splice(0, 1)
                        input.added.newIndex = 0
                    } else if (storyItem[1].id === -1) {
                        storyItem.splice(1, 1)
                    }
                }
                let element = input.added.element
                let data = {
                    title: element.title,
                    description: element.description,
                    priority: element.priority,
                    isEpic: false,
                    epic: {
                        id: this.currentEpicId,
                    },
                    id: element.id,
                }
                const self = this
                axios
                    .put(
                        this.$url +
                            '/users/' +
                            localStorage.getItem('userId') +
                            '/projects/' +
                            this.$route.params.id +
                            '/pbis',
                        data,
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
                            self.currentUserStories[self.currentEpicId][
                                input.added.newIndex
                            ].epic.id = self.currentEpicId
                        }
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            } else if (input.removed) {
                let Epic_id = input.removed.element.epic.id
                if (this.currentUserStories[Epic_id].length === 0) {
                    let data = [
                        {
                            epic: {
                                id: Epic_id, // If now epic is empty
                            },
                            id: -1, // Update its stories array
                        },
                    ]
                    this.$set(this.currentUserStories, Epic_id, data)
                }
            }
            this.$emit('editCurrentUserStories', this.currentUserStories)
        },
        editCurrentUserStories(updatedCurrentUserStories) {
            this.currentUserStories = updatedCurrentUserStories
            this.$emit('editCurrentUserStories', this.currentUserStories)
        },
        isDeveloper() {
            if (localStorage.getItem('teamRole') == 'Developer') {
                return true
            } else {
                return false
            }
        },
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.stories-field {
    background-color: #ebebeb;
    padding-top: 15px;
    padding-bottom: 5px;
}

.stories-card-group {
    margin: 0 auto;
    float: none;
    margin-bottom: 10px;
}
</style>
