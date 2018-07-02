<template>
  <b-col class="text-right">
    <b-button @click="openModal()">
      <b-img src="https://cdn3.iconfinder.com/data/icons/3d-printing-icon-set/512/Edit.png" style="width:20px; margin-right: 5px"></b-img>Edit
    </b-button>
    <b-modal v-model="modalShow" title="Edit" @ok="updateEpic()">
      <div class="text-left" id="updateEpic">
        <b-form>
          <b-form-group
            label="Title:">
            <b-form-input :id="'title'+idPBI"
                          type="text"
                          v-model="updateEpic_form.title"
                          required>
            </b-form-input>
          </b-form-group>
          <b-form-group label="Description:">
            <b-form-input :id="'desc'+idPBI"
                          type="text"
                          v-model="updateEpic_form.desc"
                          required>
            </b-form-input>
          </b-form-group>

          <b-form-group label="Priority:">
            <b-form-radio-group :id="'radios'+idPBI" v-model="updateEpic_form.selected" :options="options">
            </b-form-radio-group>
          </b-form-group>

        </b-form>
      </div>
    </b-modal>
  </b-col>
</template>

<script>import axios from 'axios'
export default {
  name: "EditEpic",
  props: {
    idPBI: Number,
    epicId: Number,
    idProject: Number,
    title: String,
    desc: String,
    priority: Number,
  },
  data: function() {
    return {
      updateEpic_form:{
        title: '',
        desc: '',
        selected: -1,
      },
      modalShow: false,
      isEpic: null,
      options: [
        { text: 'High', value: 1 },
        { text: 'Medium', value: 2},
        { text: 'Low', value: 3 },
      ],
    }
  },
  methods: {
    openModal(){
      //console.log(typeof(this.epicId));
      this.isEpic = (typeof(this.epicId)==="undefined");
      console.log(this.isEpic);
      console.log(this.idPBI);
      console.log(this.priority);
      this.updateEpic_form.title = this.title;
      this.updateEpic_form.desc = this.desc;
      this.updateEpic_form.selected = this.priority;
      console.log (this.updateEpic_form);
      this.modalShow = !this.modalShow;
    },


    updateEpic() {
      //console.log("current_id = " + current_id);
      const self = this;

      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      };
      let data = {
        title: this.updateEpic_form.title, description: this.updateEpic_form.desc, priority: this.updateEpic_form.selected, Project_id: this.idProject, idPBI: this.idPBI, Epic_id: this.epicId,
      };
      //console.log(data);

      axios.put(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis?isEpic=' + self.isEpic, data, config)
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
            else if (response.data.error === "Unauthorized projects") console.log("Unauthorized projects");
            else if (response.data.error === "null") console.log("Null token");
            else console.log(error);
          }
          else if (response.data.results) {
            console.log(response.data.results);
            self.$emit('edit_epic', self.idPBI, response.data.results.title, response.data.results.description, response.data.results.priority, self.epicId);
          }
        })
        .catch(function (error) {
          console.log(error);
        });

    },
  }
}

</script>

<style scoped>

</style>
