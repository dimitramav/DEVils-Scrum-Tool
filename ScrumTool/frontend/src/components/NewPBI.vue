<template>
  <b-modal :id="'new_story'+Epic_id" title="Create" @ok="newStory(Epic_id)">
    <div class="text-left" :id="'new_story'+Epic_id">
      <b-form>
        <b-form-group label="Title:" :label-for="'addStoryTitle'+Epic_id">
          <b-form-input :id="'addStoryTitle'+Epic_id"
                        type="text"
                        v-model="newPBI_form.title"
                        required>
          </b-form-input>
        </b-form-group>
        <b-form-group
          label="Description:"
          :label-for="'addStoryDesc'+Epic_id">
          <b-form-input :id="'updateEpicDesc'+Epic_id"
                        type="text"
                        v-model="newPBI_form.desc"
                        required>
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

<script>import axios from 'axios'
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
        selected: '',
      },
      options: [
        { text: 'High', value: 'high' },
        { text: 'Medium', value: 'medium' },
        { text: 'Low', value: 'low' },
      ],
      isEpic: null,
    }
  },
  mounted() {
    this.isEpic = (typeof(this.Epic_id)==="undefined");
    //console.log(this.Epic_id);
    //console.log ("isEpic = " + this.isEpic);
  },
  methods: {
    newStory (current_epicId) {
      //evt.preventDefault();
      console.log("current_epicId = " + current_epicId);
      const self = this;
      let config = {
        headers: {"auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json'}
      };
      let data = {
        title: this.newPBI_form.title, description: this.newPBI_form.desc, priority: self.priorityToNumber(this.newPBI_form.selected), Project_id: this.idProject, Epic_id: current_epicId ,
      };
      //console.log(data);
      axios.post(this.$url + 'users/' + localStorage.getItem('userId') + '/projects/' + this.idProject + '/pbis?isEpic=' + this.isEpic, data, config)
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
            else if (response.data.error === "Unauthorized projects") console.log("Unauthorized projects");
            else if (response.data.error === "null") console.log("Null token");
            else console.log(error);
          }
          if (response.data.results) {
            console.log(response.data.results);
            response.data.results.priority=self.priorityToString(response.data.results.priority);
            //self.currentUserStories[current_epicId].push(response.data.results);
            self.newPBI_form.selected='';self.newPBI_form.desc='';self.newPBI_form.title='';
            self.$emit('new_epic', response.data.results);
            //self.$set(self.currentUserStories,current_epicId,response.data.results);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
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
  }
}
</script>

<style scoped>

</style>
