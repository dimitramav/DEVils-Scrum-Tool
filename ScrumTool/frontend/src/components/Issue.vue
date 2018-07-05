<template>
  <b-col class="text-right">
    <b-button @click="openModal()" class="pcsprint">
      <b-img src="https://cdn2.iconfinder.com/data/icons/social-productivity-line-art-1/128/alert-triangle-512.png" style="width:20px; margin-right: 5px"></b-img>Issues
    </b-button>
    <b-modal v-model="modalShow" title="Preview Issues" @ok="updateEpic()">
      <div class="text-left" id="updateEpic">
        <b-row>
          <b-col> Issues: </b-col>
        </b-row>
        <b-row>
          <p></p>
        </b-row>
        <div id="scrollspy-nested" style="position:relative;height:250px;overflow-y:auto">
          <b-list-group>
            <b-list-group-item>Cras justo odio</b-list-group-item>
            <b-list-group-item>Dapibus ac facilisis in</b-list-group-item>
            <b-list-group-item>Morbi leo risus</b-list-group-item>
                        <b-list-group-item>Cras justo odio</b-list-group-item>
            <b-list-group-item>Dapibus ac facilisis in</b-list-group-item>
            <b-list-group-item>Morbi leo risus</b-list-group-item>
                        <b-list-group-item>Cras justo odio</b-list-group-item>
            <b-list-group-item>Dapibus ac facilisis in</b-list-group-item>
            <b-list-group-item>Morbi leo risus</b-list-group-item>
          </b-list-group>
        </div>
        <br><br>
        <b-form>
          <b-form-group label="Add an issue:">
            <b-form-input :id="'desc'+idPBI"
                          type="text"
                          v-model="updateEpic_form.desc"
                          required>
            </b-form-input>
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
            self.$emit('edit_pbi', self.idPBI, response.data.results.title, response.data.results.description, response.data.results.priority, self.epicId);
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
  @import url('https://fonts.googleapis.com/css?family=Merienda');
  @import url('https://fonts.googleapis.com/css?family=VT323');
  @import url('https://fonts.googleapis.com/css?family=Antic');
  @import url('https://fonts.googleapis.com/css?family=Quicksand');
  .ptitle{
    font-family: Merienda;
  }
  .pdeadline{
    font-family: VT323;font-size:24px; margin-top: 1%;
  }
  .pcsprint{
    font-family: Quicksand;
  }
</style>
