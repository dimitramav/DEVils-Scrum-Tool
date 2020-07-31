<template>
    <div class="text-font">
        <b-button @click="openModal()" variant="warning">
            <img src="@/assets/issue.png" class="btn-img" />
            Issues ({{ Issues.length }})
        </b-button>
        <b-modal v-model="modalShow" title="Preview Issues" @ok="newIssue()">
            <div class="text-left" id="addIssue">
                <b-row style="margin-bottom: 5px;">
                    <b-col>Issues:</b-col>
                </b-row>
                <div id="scrollspy-nested" class="issues-list">
                    <b-list-group
                        v-for="issue in Issues"
                        v-bind:data="issue"
                        v-bind:key="issue.description"
                    >
                        <b-list-group-item
                            button
                            v-b-modal="'edit_issue' + issue.id"
                        >
                            <h5 class="text-font">
                                {{ issue.description }}
                            </h5>
                            <EditIssue
                                v-on:editIssue="editIssue"
                                v-on:deleteIssue="deleteIssue"
                                :PBI_id="PBI_id"
                                :idTask="idTask"
                                :issue="issue"
                            />
                        </b-list-group-item>
                    </b-list-group>
                </div>
                <AddIssue
                    v-on:addIssue="addIssue"
                    :PBI_id="PBI_id"
                    :idTask="idTask"
                    ref="add"
                />
            </div>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
import EditIssue from '@/components/sprintbacklog/tasks/modals/EditIssue.vue'
import AddIssue from '@/components/sprintbacklog/tasks/modals/AddIssue.vue'

export default {
    name: 'Issue',
    components: {
        EditIssue,
        AddIssue,
    },
    props: {
        PBI_id: Number,
        idTask: Number,
    },
    data: function () {
        return {
            Issues: [],
            modalShow: false,
        }
    },
    methods: {
        openModal() {
            this.modalShow = !this.modalShow
            //this.getIssues()
        },
        newIssue() {
            this.$refs.add.addIssue()
        },
        getIssues() {
            const self = this
            axios
                .get(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis/' +
                        this.PBI_id +
                        '/tasks/' +
                        this.idTask +
                        '/issues',
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
                        self.Issues = response.data
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        addIssue(newIssue) {
            this.Issues.push(newIssue)
        },
        editIssue(issue) {
            let i = this.Issues.findIndex((o) => o.id == issue.id)
            this.Issues[i].description = issue.description
        },
        deleteIssue(issueId) {
            let i = this.Issues.findIndex((o) => o.id == issueId)
            if (i != -1) this.Issues.splice(i, 1)
        },
    },
    mounted() {
        this.getIssues()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

.btn-img {
    width: 20px;
    margin-right: 2px;
    margin-bottom: 3px;
}

.issues-list {
    position: relative;
    height: 250px;
    overflow-y: auto;
    margin-bottom: 35px;
}
</style>
