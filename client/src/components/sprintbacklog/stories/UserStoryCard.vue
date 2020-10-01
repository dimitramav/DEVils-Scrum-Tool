<template>
    <b-card :title="story.title" class="mb-2 text-font">
        <b-row slot="header" style="padding-left: 15px;">
            <b-col>
                <b-btn v-b-modal="'new_task' + story.id" variant="info"
                    >Add Task</b-btn
                >
                <AddTask
                    v-on:addTask="addTask"
                    :story="story"
                    :modalId="'new_task'"
                />
            </b-col>
            <b-col>
                <b-button
                    :disabled="isDeveloper()"
                    v-b-modal="'remove_story' + story.id"
                    variant="danger"
                    >Remove Story</b-button
                >
                <RemoveStory
                    v-on:removeStoryAndTasks="removeStoryAndTasks"
                    :story="story"
                    :modalId="'remove_story'"
                />
            </b-col>
        </b-row>
    </b-card>
</template>

<script>
import AddTask from '@/components/sprintbacklog/stories/modals/AddTask.vue'
import RemoveStory from '@/components/sprintbacklog/stories/modals/RemoveStory'

export default {
    name: 'UserStoryCard',
    components: {
        AddTask,
        RemoveStory,
    },
    props: {
        story: Object,
    },
    methods: {
        addTask(newTask) {
            this.$emit('addTask', newTask)
        },
        removeStoryAndTasks(storyId) {
            this.$emit('removeStoryAndTasks', storyId)
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
</style>
