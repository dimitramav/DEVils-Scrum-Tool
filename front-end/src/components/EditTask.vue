<template>
<b-col class="text-right">
    <b-button @click="openModal()">
        <b-img src="https://cdn3.iconfinder.com/data/icons/3d-printing-icon-set/512/Edit.png"
                style="width:20px; margin-right: 5px"></b-img>Edit
    </b-button>
    <b-modal v-model="modalShow" title="Edit" @ok="updateTask()">
        <div class="text-left" id="updateEpic">
            <b-form>
                <b-form-group label="Description:" :label-for="'editTask'+Task_id">
                    <b-form-input :id="'addTask'+Task_id" type="text" v-model="updateTask_desc" required>
                    </b-form-input>
                </b-form-group>
            </b-form>
        </div>
    </b-modal>
</b-col>
</template>



<script>
import axios from 'axios'

export default {
    name: "EditTask",
    props: {
        desc: String,
        PBI_id: Number,
        state: Number,
        Task_id: Number,
    },
    data: function() {
        return {
            updateTask_desc: '',
            modalShow: false,
        }
    },
    methods: {
        openModal() {
            //console.log(typeof(this.epicId));
            this.updateTask_desc = this.desc;
            this.modalShow = !this.modalShow;
        },

        updateTask() {
            const self = this;
            let config = {
                headers: {
                    "auth": localStorage.getItem('auth_token'),
                    "Content-Type": 'application/json'
                }
            };
            let data = {
                PBI_id: this.PBI_id,
                description: this.updateTask_desc,
                state: this.state,
                idTask: this.Task_id,
            };
            //console.log(data);

            axios.put(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/tasks', data, config)
                .then(function(response) {
                    //console.log(response);
                    if (response.data.error) {
                        if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
                        else if (response.data.error === "Unauthorized projects") console.log("Unauthorized projects");
                        else if (response.data.error === "null") console.log("Null token");
                        else console.log(error);
                    } else if (response.data.task) {
                        console.log(response.data.task);
                        self.$emit('editTask', response.data.task);
                    } else console.log("Unresolved response: " + response);
                })
                .catch(function(error) {
                    console.log(error);
                });
        },
    }
}
</script>


<style scoped>

</style>
