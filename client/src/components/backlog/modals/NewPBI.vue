<template>
    <b-modal :id="'new_story' + Epic_id" title="Create" @ok="newStory()">
        <b-form class="text-left" :id="'new_story' + Epic_id">
            <b-form-group label="Title:">
                <b-form-input
                    :id="'addStoryTitle' + Epic_id"
                    type="text"
                    v-model="newPBI_form.title"
                    required
                >
                </b-form-input>
            </b-form-group>
            <b-form-group label="Description:">
                <b-form-input
                    :id="'updateEpicDesc' + Epic_id"
                    type="text"
                    v-model="newPBI_form.desc"
                    required
                >
                </b-form-input>
            </b-form-group>
            <b-form-group label="Priority">
                <b-form-radio-group
                    :id="'new_story_radio' + Epic_id"
                    v-model="newPBI_form.selected"
                    :options="options"
                >
                </b-form-radio-group>
            </b-form-group>
        </b-form>
    </b-modal>
</template>

<script>
import axios from 'axios'

export default {
    name: 'NewPBI',
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
            options: [
                {
                    text: 'High',
                    value: 1,
                },
                {
                    text: 'Medium',
                    value: 2,
                },
                {
                    text: 'Low',
                    value: 3,
                },
            ],
            isEpic: null,
        }
    },
    methods: {
        newStory() {
            const self = this
            let data = {
                title: this.newPBI_form.title,
                description: this.newPBI_form.desc,
                priority: this.newPBI_form.selected,
                Project_id: this.idProject,
                Epic_id: this.Epic_id,
            }
            axios
                .post(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.idProject +
                        '/pbis?isEpic=' +
                        this.isEpic,
                    data,
                    {
                        headers: {
                            auth: localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.results) {
                        self.newPBI_form.selected = ''
                        self.newPBI_form.desc = ''
                        self.newPBI_form.title = ''
                        self.$emit('new_pbi', response.data.results)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        this.isEpic = typeof this.Epic_id === 'undefined'
        //console.log(this.Epic_id, this.isEpic)
    },
}
</script>

<style scoped></style>
