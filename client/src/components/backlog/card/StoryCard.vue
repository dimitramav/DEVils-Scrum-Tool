<template>
    <b-card v-if="cur_us.id > 0" class="mb-1">
        <b-card-header class="p-1" role="tab">
            <b-btn block v-b-toggle="'collapse' + cur_us.id">
                {{ cur_us.title }}
            </b-btn>
        </b-card-header>
        <b-collapse :id="'collapse' + cur_us.id">
            <b-card-body>
                <p class="card-text">{{ cur_us.description }}</p>
                <b-row class="card-text">
                    <PriorityFooter :cur_pbi_priority="cur_us.priority" />
                </b-row>
            </b-card-body>
            <EditPBI
                v-on:edit_pbi="editStory"
                v-on:delete_pbi="deleteStory"
                :epicId="current_epic_id"
                :idPBI="cur_us.id"
                :idProject="currentProject_id"
                :title="cur_us.title"
                :desc="cur_us.description"
                :priority="cur_us.priority"
            />
        </b-collapse>
    </b-card>
</template>

<script>
import EditPBI from '@/components/backlog/modals/EditPBI.vue'
import PriorityFooter from '@/components/backlog/card/PriorityFooter.vue'

export default {
    name: 'StoryCard',
    props: {
        cur_us: Object,
        current_epic_id: Number,
        currentUserStories: Array,
    },
    components: {
        EditPBI,
        PriorityFooter,
    },
    data() {
        return {
            currentProject_id: -1,
        }
    },
    methods: {
        editStory(id, title, desc, priority, epicId) {
            let i = this.currentUserStories[epicId].findIndex(
                (o) => o.id === id
            )
            this.currentUserStories[epicId][i].title = title
            this.currentUserStories[epicId][i].description = desc
            this.currentUserStories[epicId][i].priority = priority
            this.$emit('editStory', this.currentUserStories)
        },
        deleteStory(id) {
            let i = this.currentUserStories[this.current_epic_id].findIndex(
                (o) => o.id === id
            )
            this.currentUserStories[this.current_epic_id].splice(i, 1)
            if (this.currentUserStories[this.current_epic_id].length === 0) {
                let val = [
                    {
                        epic: {
                            id: this.current_epic_id, // Same practice
                        },
                        id: -1, // Update its stories array
                    },
                ]
                this.$set(this.currentUserStories, this.current_epic_id, val)
            }
            this.$emit('editStory', this.currentUserStories)
        },
    },
    mounted() {
        this.currentProject_id = parseInt(this.$route.params.id)
    },
}
</script>

<style scoped></style>
