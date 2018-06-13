<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="false"></navbar>
    <br>
    <b-row>
      <b-breadcrumb :items="items" style="position: relative;left: 41px;"/>
    </b-row>
    <!--User Infos-->
    <b-row>
      <b-col>
      </b-col>
      <b-col>
        <b-form style="text-align: left" @submit="updateProfile">
      <b-form-group id="firstname"
                    label="First Name"
                    label-for="firstname">
        <b-form-input id="firstname"
                      type="text"
                      v-model="userInfos.firstname" :readonly="NonEdit">
        </b-form-input>
      </b-form-group>
      <b-form-group id="lastname"
                    label="Last Name"
                    label-for="lastname">
        <b-form-input id="lastname"
                      type="text"
                      v-model="userInfos.lastname" :readonly="NonEdit">
        </b-form-input>
      </b-form-group>
          <b-form-group id="emailForm"
                        label="Email address"
                        label-for="email">
            <b-form-input id="email"
                          type="email"
                          v-model="userInfos.mail" :readonly="NonEdit"> <!--autocomplete has a bug and v-model does not syncronize it-->
            </b-form-input>

          </b-form-group>
          <b-form-group id="JobId"
                        label="Job"
                        label-for="job">
            <b-form-input id="job"
                          type="text"
                          v-model="userInfos.job" :readonly="NonEdit"> <!--autocomplete has a bug and v-model does not syncronize it-->
            </b-form-input>

          </b-form-group>
          <b-form-group id="CompanyId"
                        label="Company"
                        label-for="company">
            <b-form-input id="company"
                          type="text"
                          v-model="userInfos.company" :readonly="NonEdit"> <!--autocomplete has a bug and v-model does not syncronize it-->
            </b-form-input>

          </b-form-group>
          <b-form-group id="CountryId"
                        label="Country"
                        label-for="Country">
            <b-form-input id="Country"
                          type="text"
                          v-model="userInfos.country" :readonly="NonEdit"> <!--autocomplete has a bug and v-model does not syncronize it-->
            </b-form-input>

          </b-form-group>
          <b-form-group id="descriptionId"
                        label="About me"
                        label-for="description">
           <b-form-textarea id="description"
                          type="text"
                            :rows="3"
                            :max-rows="6"
                         v-model="userInfos.description" :readonly="NonEdit">
            </b-form-textarea>
          </b-form-group>
          <!--TODO bio-->
          <p  :v-show="same"> aaaaa </p>
          <b-button v-if="NonEdit" @click="EnableEditing">Edit Information</b-button>
          <b-button v-if="!NonEdit" type="submit">Submit changes</b-button>
        </b-form>

      </b-col>

      <b-col>
      </b-col>
    </b-row>
  </b-container>
</template>


<script>
  import axios from 'axios'
  import Navbar from "./Navbar.vue"
  import json from '../assets/team.json'
  export default {
    components: {
      navbar: Navbar,
    },
  data () {
    return {
      userInfos: {
        idUser: null,
        firstname: null,
        lastname: null,
        mail: null,
        job: null,
        company: null,
        country: null,
        description: null
      },

      items: [{
        text: 'Home',
        href: '#/'
      },
        {
        text: 'Profile',
        active: true
      }],

      NonEdit: true,
      loggedin_user:'',
      same: false,
    }
  },
    computed:{
      userIsOwner(){
        var self= this
        console.log(self.loggedin_user + this.userInfos.idUser)
        if(self.loggedin_user===this.userInfos.idUser) {
            this.$nextTick(() => {
              console.log(self.loggedin_user + this.userInfos.idUser)
              same=true;
            });
        }
      },

    },
  methods: {
    EnableEditing() {
      this.NonEdit = false
    },
    getProfile() {
      //evt.preventDefault();
      const self = this;
      axios.get(this.$url +'users/' + localStorage.getItem('userId') + '/profile/' + this.current_username, {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          if (response.data.results) {
            self.userInfos = response.data.results;
            console.log(self.userInfos);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    updateProfile(evt) {
      evt.preventDefault();
      const self = this;
      axios.put(this.$url +'users/' + localStorage.getItem('userId') + '/profile/' + this.current_username, self.userInfos, {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          if (response.data.error) {
            if (response.data.error === "Unauthorized user") {
              console.log("Unauthorized user");
            }
          }
          if (response.data.results) {
            self.userInfos = response.data.results;
            console.log(self.userInfos);
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  },
    mounted() {
      if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
      this.current_username=this.$route.params.id;
      this.loggedin_user=localStorage.getItem('userId');
      this.getProfile();
      this.userIsOwner();
    },
}
</script>
<style scoped>

  .Navigation {
    position: absolute;
    top: 0;
    left: 0;
  }

  .container-fluid {
    padding-right: 0;
    padding-left:0;
  }


  .row {
    margin-right: 0px;
    margin-left: 0px;
  }
</style>

