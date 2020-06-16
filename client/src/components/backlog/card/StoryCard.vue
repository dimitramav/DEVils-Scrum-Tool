<template>
    <b-card v-if="cur_us.idPBI > 0" class="mb-1">
        <b-card-header class="p-1" role="tab">
            <b-btn block v-b-toggle="'collapse' + cur_us.idPBI">
                {{ cur_us.title }}
            </b-btn>
        </b-card-header>
        <b-collapse :id="'collapse' + cur_us.idPBI">
            <b-card-body>
                <p class="card-text">{{ cur_us.description }}</p>
                <b-row class="card-text">
                    <PriorityFooter :cur_pbi_priority="cur_us.priority" />
                </b-row>
            </b-card-body>
            <EditPBI
                v-on:edit_pbi="editStory"
                v-on:delete_pbi="deleteStory"
                :epicId="current_epic_idPBI"
                :idPBI="cur_us.idPBI"
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
        current_epic_idPBI: Number,
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
        editStory(idPBI, title, desc, priority, epicId) {
            let i = this.currentUserStories[epicId].findIndex(
                (o) => o.idPBI === idPBI
            )
            this.currentUserStories[epicId][i].title = title
            this.currentUserStories[epicId][i].description = desc
            this.currentUserStories[epicId][i].priority = priority
            this.$emit('editStory', this.currentUserStories)
        },
        deleteStory(idPBI) {
            let i = this.currentUserStories[this.current_epic_idPBI].findIndex(
                (o) => o.idPBI === idPBI
            )
            this.currentUserStories[this.current_epic_idPBI].splice(i, 1)
            if (this.currentUserStories[this.current_epic_idPBI].length === 0) {
                let val = [
                    {
                        Epic_id: this.current_epic_idPBI, // Same practice
                        idPBI: -1, // Update its stories array
                    },
                ]
                this.$set(this.currentUserStories, this.current_epic_idPBI, val)
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
