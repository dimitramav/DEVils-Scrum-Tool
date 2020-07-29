<template>
    <b-card img-top tag="article" class="mb-2">
        <div slot="header">
            <b-row align-h="start">
                <EditTask
                    v-on:editTask="editTask"
                    v-on:deleteTask="deleteTask"
                    :desc="cur_task.description"
                    :PBI_id="cur_task.storyId"
                    :state="cur_task.state"
                    :Task_id="cur_task.id"
                />
                <Issue :PBI_id="cur_task.storyId" :idTask="cur_task.id" />
            </b-row>
        </div>
        <p class="card-text">
            {{ cur_task.description }}
        </p>
    </b-card>
</template>

<script>
import EditTask from '@/components/sprintbacklog/tasks/modals/EditTask.vue'
import Issue from '@/components/sprintbacklog/tasks/Issue.vue'

export default {
    name: 'TaskCard',
    components: {
        EditTask,
        Issue,
    },
    props: {
        cur_task: Object,
        tasks: Array,
        typeofTasks: Number,
    },
    methods: {
        editTask(taskItem) {
            let i = this.tasks[this.typeofTasks].findIndex(
                (o) => o.id === taskItem.id
            )
            this.tasks[this.typeofTasks][i].description = taskItem.description
            this.$emit('editTask', this.tasks)
        },
        deleteTask(taskId) {
            let i = this.tasks[this.typeofTasks].findIndex(
                (o) => o.id === taskId
            )
            if (i != -1) this.tasks[this.typeofTasks].splice(i, 1)
            // Special manipulation if this was the only task of this type
            if (this.tasks[this.typeofTasks].length === 0) {
                let emptyData = [
                    {
                        state: this.typeofTasks, // If now task category is empty
                        id: -1, // Update its typeofDo task array
                    },
                ]
                this.$set(this.tasks, this.typeofTasks, emptyData)
            }
            this.$emit('editTask', this.tasks)
        },
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}
</style>
