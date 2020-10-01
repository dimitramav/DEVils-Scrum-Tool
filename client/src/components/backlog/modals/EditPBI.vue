<template>
    <div class="text-right">
        <b-button
            :disabled="isDeveloper()"
            @click="openModal()"
            variant="outline-secondary"
        >
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
            <template v-slot:modal-footer="{ remove, cancel, ok }">
                <b-btn class="mr-auto" variant="danger" v-b-modal="'del_pbi'">
                    Delete PBI</b-btn
                >
                <DeletePBI
                    v-on:delete_pbi="deletePBI"
                    :modalId="'del_pbi'"
                    :idPBI="idPBI"
                />
                <b-btn variant="secondary" @click="cancel()">Cancel</b-btn>
                <b-button variant="primary" @click="ok()">OK</b-button>
            </template>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
import DeletePBI from '@/components/backlog/modals/DeletePBI.vue'

export default {
    name: 'EditPBI',
    components: {
        DeletePBI,
    },
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
                isEpic: this.isEpic,
                project: {
                    id: this.idProject,
                },
                epic: {
                    id: this.epicId,
                },
                id: this.idPBI,
            }
            axios
                .put(
                    this.$url +
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.idProject +
                        '/pbis',
                    data,
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
                        self.$emit(
                            'edit_pbi',
                            self.idPBI,
                            response.data.title,
                            response.data.description,
                            response.data.priority,
                            self.epicId
                        )
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        deletePBI(idPBI) {
            this.modalShow = false
            this.$emit('delete_pbi', idPBI)
        },
        isDeveloper() {
            if (localStorage.getItem('teamRole') == 'Developer') {
                return true
            } else {
                return false
            }
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
