<template>
    <div class="text-right">
        <b-button @click="openModal()" variant="outline-secondary">
            <img src="@/assets/edit.png" class="btn-img" />
            Edit
        </b-button>
        <b-modal v-model="modalShow" title="Edit" @ok="updateEpic()">
            <b-form class="text-left" id="updateEpic">
                <b-form-group label="Title:">
                    <b-form-input
                        :id="'title' + idPBI"
                        type="text"
                        v-model="updateEpic_form.title"
                        required
                    >
                    </b-form-input>
                </b-form-group>
                <b-form-group label="Description:">
                    <b-form-input
                        :id="'desc' + idPBI"
                        type="text"
                        v-model="updateEpic_form.desc"
                        required
                    >
                    </b-form-input>
                </b-form-group>
                <b-form-group label="Priority:">
                    <b-form-radio-group
                        :id="'radios' + idPBI"
                        v-model="updateEpic_form.selected"
                        :options="options"
                    >
                    </b-form-radio-group>
                </b-form-group>
            </b-form>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'EditEpic',
    props: {
        idPBI: Number,
        epicId: Number,
        idProject: Number,
        title: String,
        desc: String,
        priority: Number,
    },
    data: function () {
        return {
            updateEpic_form: {
                title: '',
                desc: '',
                selected: -1,
            },
            modalShow: false,
            isEpic: null,
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
        }
    },
    methods: {
        openModal() {
            this.isEpic = typeof this.epicId === 'undefined'
            //console.log(this.isEpic, this.idPBI, this.priority)
            this.updateEpic_form.title = this.title
            this.updateEpic_form.desc = this.desc
            this.updateEpic_form.selected = this.priority
            this.modalShow = !this.modalShow
        },
        updateEpic() {
            const self = this
            let data = {
                title: this.updateEpic_form.title,
                description: this.updateEpic_form.desc,
                priority: this.updateEpic_form.selected,
                Project_id: this.idProject,
                idPBI: this.idPBI,
                Epic_id: this.epicId,
            }
            axios
                .put(
                    this.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
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
                        self.$emit(
                            'edit_pbi',
                            self.idPBI,
                            response.data.results.title,
                            response.data.results.description,
                            response.data.results.priority,
                            self.epicId
                        )
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
}
</script>

<style scoped>
.btn-img {
    width: 20px;
    margin-right: 2px;
    margin-bottom: 2px;
}
</style>
