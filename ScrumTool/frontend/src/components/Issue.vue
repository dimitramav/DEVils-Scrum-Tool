<template>
  <div>
    <b-button @click="openModal()" class="pcsprint" variant="danger" style="background-color: rgba(210,0,12,0.72)">
      <b-img src="https://cdn2.iconfinder.com/data/icons/social-productivity-line-art-1/128/alert-triangle-512.png" style="width:20px; margin-right: 5px"></b-img>Issues
    </b-button>
    <b-modal v-model="modalShow" title="Preview Issues" @ok="addIssue()">
      <div class="text-left" id="addIssue">
        <b-row>
          <b-col> Issues: </b-col>
        </b-row>
        <b-row>
        </b-row>
        <div id="scrollspy-nested" style="position:relative;height:250px;overflow-y:auto">
          <b-list-group v-for="issue in Issues" v-bind:data="issue" v-bind:key="issue.description">
            <b-list-group-item class="flex-column align-items-start">
              <div class="d-flex w-100 justify-content-between pcsprint">
                <h5 class="mb-1" style="font-weight: normal;">{{issue.description}}</h5>
              </div>
            </b-list-group-item>
          </b-list-group>
        </div>

        <br><br>
        <b-form>
          <b-form-group label="Add an issue:">
            <b-form-input v-model="newIssue" type="text" placeholder="Description">
            </b-form-input>
          </b-form-group>
        </b-form>
      </div>
    </b-modal>
  </div>
</template>

<script>import axios from 'axios'
export default {
  props: {
    idProject: Number,
    idTask: Number,
  },
  data: function() {
    return {
      loggedUserID: 0,
      Issues: [],
      newIssue: '',
      modalShow: false,
      issueToAdd: {
        description: '',
        Task_id: 0,
      },
    }
  },
  methods: {
    openModal(){
      console.log("openModal");
      this.loggedUserID = localStorage.getItem('userId');
      this.modalShow = !this.modalShow;
      this.getIssues();
    },

    getIssues() {
      console.log("getIssues");
      const self = this;
      axios.get(this.$url+ 'users/'+ self.loggedUserID + '/projects/' + self.idProject + '/tasks/' + self.idTask + '/issues', {
        headers: { "auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json' }
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
            else if (response.data.error === "Unauthorized issues") {
              console.log("Unauthorized issues");
            }
            else if (response.data.error === "null") {
              console.log("Null token");
            }
          }
          if (response.data.results) {
            self.Issues = response.data.results;
            console.log("Got the issues");
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },

    addIssue () {
      console.log("addIssue");
      const self = this;
      self.issueToAdd.description = self.newIssue;
      self.issueToAdd.Task_id = self.idTask;
      //app/api/sprint/{sprintId}/pbis/{pbiId}/tasks/{taskId}/issues
      axios.post(this.$url+ 'users/'+ self.loggedUserID + '/projects/' + self.idProject + '/tasks/' + self.idTask + '/issues', self.issueToAdd, {
        headers: { "auth": localStorage.getItem('auth_token'), "Content-Type": 'application/json' }
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
            else if (response.data.error === "Unauthorized issues") {
              console.log("Unauthorized issues");
            }
            else if (response.data.error === "null") {
              console.log("Null token");
            }
          }
          if (response.data.results) {
            console.log("Add Issue");
            self.newIssue='';
          }
        })
        .catch(function (error) {
          console.log(error);
        })
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
