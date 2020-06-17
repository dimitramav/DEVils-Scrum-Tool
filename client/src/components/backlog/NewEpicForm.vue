<template>
    <div class="text-font">
        <b-jumbotron v-if="numOfPbis === 0">
            <h1>No Epics in Product Backlog</h1>
            <b>Start creating!</b><br /><br />
            <b-btn
                v-b-modal="'new_storyundefined'"
                size="lg"
                variant="success"
                class="m-md-3"
                >Create New Epic</b-btn
            >
            <NewPBI v-on:new_pbi="newEpic" :idProject="currentProject_id" />
        </b-jumbotron>
        <b-row v-if="numOfPbis > 0">
            <b-col class="text-right">
                <b-btn
                    v-b-modal="'new_storyundefined'"
                    size="lg"
                    variant="success"
                    class="m-md-3"
                    >Create New Epic</b-btn
                >
                <NewPBI v-on:new_pbi="newEpic" :idProject="currentProject_id" />
            </b-col>
        </b-row>
    </div>
</template>

<script>
import NewPBI from '@/components/backlog/modals/NewPBI.vue'

export default {
    name: 'NewEpicForm',
    props: {
        numOfPbis: Number,
    },
    components: {
        NewPBI,
    },
    data() {
        return {
            currentProject_id: -1,
        }
    },
    methods: {
        newEpic(data) {
            //console.log(data);
            this.$emit('newEpic', data)
        },
    },
    mounted() {
        this.currentProject_id = parseInt(this.$route.params.id)
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}
</style>
