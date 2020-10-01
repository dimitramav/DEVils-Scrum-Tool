<template>
    <b-row align-v="center">
        <b-col>
            <b-btn
                :disabled="isDeveloper()"
                v-b-modal="'new_story' + current_epic.id"
                >Add User Story</b-btn
            >
            <NewPBI
                v-on:new_pbi="newUserStory"
                :idProject="currentProject_id"
                :Epic_id="current_epic.id"
            />
        </b-col>
        <b-col>
            <EditPBI
                v-on:edit_pbi="editEpic"
                v-on:delete_pbi="deleteEpic"
                :idPBI="current_epic.id"
                :idProject="currentProject_id"
                :title="current_epic.title"
                :desc="current_epic.description"
                :priority="current_epic.priority"
            />
        </b-col>
    </b-row>
</template>

<script>
import NewPBI from '@/components/backlog/modals/NewPBI.vue'
import EditPBI from '@/components/backlog/modals/EditPBI.vue'

export default {
    name: 'CardHeader',
    props: {
        current_epic: Object,
        currentPbis: Array,
        currentUserStories: Array,
    },
    components: {
        NewPBI,
        EditPBI,
    },
    data() {
        return {
            currentProject_id: -1,
        }
    },
    methods: {
        newUserStory(data) {
            //console.log(data)
            if (this.currentUserStories[data.epic.id]) {
                if (
                    this.currentUserStories[data.epic.id].length === 1 &&
                    this.currentUserStories[data.epic.id][0].id === -1
                ) {
                    this.currentUserStories[data.epic.id].splice(0, 1, data)
                } else this.currentUserStories[data.epic.id].push(data)
            }
            this.$emit('newUserStory', this.currentUserStories)
        },
        editEpic(id, title, desc, priority) {
            let i = this.currentPbis.findIndex((o) => o.id === id)
            this.currentPbis[i].title = title
            this.currentPbis[i].description = desc
            this.currentPbis[i].priority = priority
            this.$emit('editEpic', this.currentPbis)
        },
        deleteEpic(id) {
            let i = this.currentPbis.findIndex((o) => o.id === id)
            if (i != -1) this.currentPbis.splice(i, 1)
            this.$emit('editEpic', this.currentPbis)
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
    },
}
</script>

<style scoped></style>
