<template>
<b-modal :id="'new_story'+Epic_id" title="Create" @ok="newStory(Epic_id)" class="pcsprint">
    <div class="text-left" :id="'new_story'+Epic_id">
        <b-form>
            <b-form-group label="Title:" :label-for="'addStoryTitle'+Epic_id">
                <b-form-input :id="'addStoryTitle'+Epic_id" type="text" v-model="newPBI_form.title" required>
                </b-form-input>
            </b-form-group>
            <b-form-group label="Description:" :label-for="'addStoryDesc'+Epic_id">
                <b-form-input :id="'updateEpicDesc'+Epic_id" type="text" v-model="newPBI_form.desc" required>
                </b-form-input>
            </b-form-group>
            <b-form-group label="Priority">
                <b-form-radio-group :id="'new_story_radio'+Epic_id" v-model="newPBI_form.selected" :options="options">
                </b-form-radio-group>
            </b-form-group>
        </b-form>
    </div>
</b-modal>
</template>



<script>
import axios from 'axios'

export default {
    name: "NewPBI",
    props: {
        Epic_id: Number,
        idProject: Number,
    },
    data() {
        return {
            newPBI_form: {
                title: '',
                desc: '',
                selected: -1,
            },
            options: [{
                    text: 'High',
                    value: 1
                },
                {
                    text: 'Medium',
                    value: 2
                },
                {
                    text: 'Low',
                    value: 3
                },
            ],
            isEpic: null,
        }
    },
    mounted() {
        this.isEpic = (typeof(this.Epic_id) === "undefined");
        //console.log(this.Epic_id);
        //console.log ("isEpic = " + this.isEpic);
    },
    methods: {
        newStory(current_epicId) {
            //evt.preventDefault();
            console.log("current_epicId = " + current_epicId);
            const self = this;
            let config = {
                headers: {
                    "auth": localStorage.getItem('auth_token'),
                    "Content-Type": 'application/json'
                }
            };
            let data = {
                title: this.newPBI_form.title,
                description: this.newPBI_form.desc,
                priority: this.newPBI_form.selected,
                Project_id: this.idProject,
                Epic_id: current_epicId,
            };
            //console.log(data);
            axios.post(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.idProject
                        + '/pbis?isEpic=' + this.isEpic, data, config)
                .then(function(response) {
                    if (response.data.error) {
                        if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
                        else if (response.data.error === "Unauthorized projects") console.log("Unauthorized projects");
                        else if (response.data.error === "null") console.log("Null token");
                        else console.log(error);
                    }
                    if (response.data.results) {
                        console.log(response.data.results);
                        //response.data.results.priority=self.priorityToString(response.data.results.priority);
                        //self.currentUserStories[current_epicId].push(response.data.results);
                        self.newPBI_form.selected = '';
                        self.newPBI_form.desc = '';
                        self.newPBI_form.title = '';
                        self.$emit('new_pbi', response.data.results);
                        //self.$set(self.currentUserStories,current_epicId,response.data.results);
                    }
                })
                .catch(function(error) {
                    console.log(error);
                })
        },
    }
}
</script>



<style scoped>
@import url('https://fonts.googleapis.com/css?family=Merienda');
@import url('https://fonts.googleapis.com/css?family=VT323');
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.ptitle {
    font-family: Merienda;
}

.pdeadline {
    font-family: VT323;
    font-size: 24px;
    margin-top: 1%;
}

.pcsprint {
    font-family: Quicksand;
}
</style>
