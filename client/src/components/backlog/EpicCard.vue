<template>
    <b-card id="epicCard" :title="cur_pbi.title" style="max-width: 17rem;">
        <!-- Card header with 2 buttons -->
        <div slot="header">
            <CardHeader
                v-on:newUserStory="editCurrentUserStories"
                v-on:editEpic="editEpic"
                :current_epic="cur_pbi"
                :currentPbis="currentPbis"
                :currentUserStories="currentUserStories"
            />
        </div>
        <!-- Epic description -->
        <p class="card-text" style="color: grey;">
            {{ cur_pbi.description }}
        </p>
        <!--Get User Stories of each epic-->
        <b-btn
            v-b-toggle="'collapse' + cur_pbi.id"
            v-on:click="getEpicUserStories(cur_pbi.id)"
            variant="info"
        >
            User Stories
        </b-btn>
        <UserStoriesGroup
            v-on:editCurrentUserStories="editCurrentUserStories"
            v-on:newCurrentEpicId="newCurrentEpicId"
            :current_epic="cur_pbi"
            :currentUserStories="currentUserStories"
            :currentEpicId="currentEpicId"
        />
        <div slot="footer">
            <PriorityFooter :cur_pbi_priority="cur_pbi.priority" />
        </div>
    </b-card>
</template>

<script>
import axios from 'axios'
import CardHeader from '@/components/backlog/card/CardHeader.vue'
import UserStoriesGroup from '@/components/backlog/card/UserStoriesGroup.vue'
import PriorityFooter from '@/components/backlog/card/PriorityFooter.vue'

export default {
    name: 'EpicCard',
    components: {
        CardHeader,
        UserStoriesGroup,
        PriorityFooter,
    },
    props: {
        cur_pbi: Object,
        currentPbis: Array,
        currentUserStories: Array,
        currentEpicId: Number,
    },
    methods: {
        getEpicUserStories(epicId) {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis?isEpic=false&epicId=' +
                        epicId,
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
                        // For empty epic add a special entry in stories array
                        if (response.data.length > 0) {
                            self.$set(
                                self.currentUserStories,
                                epicId,
                                response.data
                            )
                        } else {
                            let data = [
                                {
                                    epic: {
                                        id: epicId,
                                    },
                                    id: -1,
                                },
                            ]
                            self.$set(self.currentUserStories, epicId, data)
                        }
                        self.$emit(
                            'editCurrentUserStories',
                            self.currentUserStories
                        )
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        editEpic(updatedCurrentPbis) {
            this.currentPbis = updatedCurrentPbis
            this.$emit('editEpic', this.currentPbis)
        },
        newCurrentEpicId(updatedCurrentEpicId) {
            this.$emit('newCurrentEpicId', updatedCurrentEpicId)
        },
        editCurrentUserStories(updatedCurrentUserStories) {
            this.currentUserStories = updatedCurrentUserStories
            this.$emit('editCurrentUserStories', this.currentUserStories)
        },
    },
}
</script>

<style scoped></style>
