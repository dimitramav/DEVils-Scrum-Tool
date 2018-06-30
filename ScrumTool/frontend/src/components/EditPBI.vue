<template>
  <b-col class="text-right">
    <b-button @click="openModal()">Edit</b-button>
    <b-modal v-model="modalShow" title="Update Epic" @ok="updateEpic()">
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
    priority: String,
  },
  data: function() {
    return {
      updateEpic_form:{
        title: '',
        desc: '',
        selected: '',
      },
      modalShow: false,
      isEpic: null,
      options: [
        { text: 'High', value: 'high' },
        { text: 'Medium', value: 'medium'},
        { text: 'Low', value: 'low' },
      ],
    }
  },
  methods: {
    openModal(){
      //console.log("idpbi = " + this.idPBI);
      this.isEpic = (this.idPBI===this.epicId);
      console.log(this.isEpic);
      console.log(this.idPBI);
      console.log(this.priority);
      this.updateEpic_form.title = this.title;
      this.updateEpic_form.desc = this.desc;
      this.updateEpic_form.selected = this.priority;
      console.log (this.updateEpic_form);
      this.modalShow = !this.modalShow;
    },

    priorityToNumber(priority) {
      let num_priority;
      switch(priority) {
        case "high":
          num_priority=1;
          break;
        case "medium":
          num_priority=2;
          break;
        case "low":
          num_priority=3;
          break;
        default:
          console.log ("Unknown value" + priority);
      }
      return num_priority;
    },
    priorityToString(priority) {
      let str_priority;
      switch(priority) {
        case 1:
          str_priority="high";
          break;
        case 2:
          str_priority="medium";
          break;
        case 3:
          str_priority="low";
          break;
        default:
          console.log ("Unknown value" + priority);
      }
      return str_priority;
    },

    updateEpic() {
      //console.log("current_id = " + current_id);
      const self = this;

      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      };
      let data = {
        title: this.updateEpic_form.title, description: this.updateEpic_form.desc, priority: self.priorityToNumber(this.updateEpic_form.selected), Project_id: this.idProject, idPBI: this.idPBI, Epic_id: this.epicId,
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
            self.$emit('edit_epic', self.idPBI, response.data.results.title, response.data.results.description, self.priorityToString(response.data.results.priority), self.epicId);
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
