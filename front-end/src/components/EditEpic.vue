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
    idProject: Number,
  },
  data: function() {
    return {
      updateEpic_form:{
        title: '',
        desc: '',
        selected: '',
      },
      modalShow: false,
      options: [
        { text: 'High', value: 'high' },
        { text: 'Medium', value: 'medium'},
        { text: 'Low', value: 'low' },
      ],
    }
  },
  methods: {
    openModal(){
      // console.log("idPBI = " + pbi.idPBI);
      // this.updateEpic_form.title=pbi.title;
      // this.updateEpic_form.desc=pbi.description;
      // this.updateEpic_form.selected=pbi.priority;
      //
      // console.log("modalShow prin = " + this.modalShow[pbi.idPBI]);
      // //this.modalShow[pbi.idPBI]=!this.modalShow[pbi.idPBI];
      // this.modalShow[pbi.idPBI]=true;
      //
      // console.log("modalShow meta = " + this.modalShow[pbi.idPBI]);

      console.log("idpbi = " + this.idPBI);
      this.getPBIS();
      this.modalShow = !this.modalShow;

    },

    getPBIS() {
      //evt.preventDefault();
      const self = this;
      axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.$route.params.id + '/pbis?isEpic=true', {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
            else console.log(response.data.error);
          }
          if (response.data.results) {
            //console.log(response.data.results);
            //prepei na to spasw se synartisi
            response.data.results.forEach(function(arrayItem) {
              arrayItem.priority=self.priorityToString(arrayItem.priority);
              //self.modalShow[arrayItem.idPBI]=false;
              if (arrayItem.idPBI===self.idPBI) {
                //self.$set(self.updateEpic_form.title, 'title', arrayItem.title);
                //console.log("mphka");
                self.updateEpic_form.title = arrayItem.title;
                self.updateEpic_form.desc = arrayItem.description;
                self.updateEpic_form.selected = arrayItem.priority;
              }
            });
            //self.currentPbis = response.data.results;
            //console.log(self.currentPbis);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
      console.log(self.updateEpic_form);
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
      //console.log("hi "+ this.updateEpic_form.title);

      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      };
      let data = {
        title: this.updateEpic_form.title, description: this.updateEpic_form.desc, priority: self.priorityToNumber(this.updateEpic_form.selected), Project_id: this.idProject, idPBI: this.idPBI,
      };
      //console.log(data);

      axios.put(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.currentProject_id + '/pbis?isEpic=true', data, config)
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
            else if (response.data.error === "Unauthorized projects") {
              console.log("Unauthorized projects");
            }
            else if (response.data.error === "null") {
              console.log("Null token");
            }
          }
          else if (response.data.results) {
            console.log(response.data.results);
            self.$emit('edit_epic', self.idPBI, response.data.results.title, response.data.results.description, self.priorityToString(response.data.results.priority));


            //response.data.results.priority=self.priorityToString(response.data.results.priority);

            //console.log("response.data.results = " + response.data.results);

            //let i = self.currentPbis.findIndex(o => o.idPBI === response.data.results.idPBI);

            //self.$set(self.currentPbis,i,response.data.results);
            //self.modalShow[current_id]=false;


            //remove previous epic
            //console.log("current id" + current_id);
            //self.currentPbis = self.currentPbis.filter(el => el.idPBI !== self.current_id);
            //console.log(self.currentPbis);
            //add updated epic
            //console.log("after push");
            //console.log(self.currentPbis);
            //console.log("eimai prin to emit");
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
