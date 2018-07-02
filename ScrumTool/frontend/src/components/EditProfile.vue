<template>
  <b-container class="Navigation" fluid>

    <navbar :dashboard="false"></navbar>
    <br>
    <!--User Infos-->
    <b-row>
      <b-col>
      </b-col>
      <b-col>
        <b-alert variant="success" :show="showAlert" >Changes saved!</b-alert>
        <b-form style="text-align: left" @submit="updateProfile">
          <b-form-group id="firstname"
                        label="First Name"
                        label-for="firstname">
            <b-form-input id="firstname"
                          type="text"
                          v-model="userInfos.firstname">
            </b-form-input>
          </b-form-group>
          <b-form-group id="lastname"
                        label="Last Name"
                        label-for="lastname">
            <b-form-input id="lastname"
                          type="text"
                          v-model="userInfos.lastname">
            </b-form-input>
          </b-form-group>
          <b-form-group id="emailForm"
                        label="Email address"
                        label-for="email">
            <b-form-input id="email"
                          type="email"
                          v-model="userInfos.mail"> <!--autocomplete has a bug and v-model does not syncronize it-->
            </b-form-input>

          </b-form-group>
          <b-form-group id="JobId"
                        label="Job"
                        label-for="job">
            <b-form-input id="job"
                          type="text"
                          v-model="userInfos.job"> <!--autocomplete has a bug and v-model does not syncronize it-->
            </b-form-input>

          </b-form-group>
          <b-form-group id="CompanyId"
                        label="Company"
                        label-for="company">
            <b-form-input id="company"
                          type="text"
                          v-model="userInfos.company"> <!--autocomplete has a bug and v-model does not syncronize it-->
            </b-form-input>

          </b-form-group>
          <b-form-group id="CountryId"
                        label="Country"
                        label-for="Country">
            <b-form-input id="Country"
                          type="text"
                          v-model="userInfos.country"> <!--autocomplete has a bug and v-model does not syncronize it-->
            </b-form-input>

          </b-form-group>
          <b-form-group id="descriptionId"
                        label="About me"
                        label-for="description">
            <b-form-textarea id="description"
                             type="text"
                             :rows="3"
                             :max-rows="6"
                             v-model="userInfos.description">
            </b-form-textarea>
          </b-form-group>
          <change_pass></change_pass>
          <br>
          <!--TODO: bio-->
          <b-button variant="success" size="lg" type="submit">Submit changes</b-button>
        </b-form>

      </b-col>

      <b-col>
      </b-col>
    </b-row>
    <b-row>
      <p></p>
    </b-row>
  </b-container>
</template>


<script>
  import axios from 'axios'
  import Navbar from "./Navbar.vue"
  import ChangePass from "./ChangePass.vue"
  export default {
    components: {
      navbar: Navbar,
      change_pass: ChangePass,
    },
    data () {
      return {
        userInfos: [],
        items: [{
          text: 'Home',
          href: '#/'
        },
          {
            text: 'Profile',
            active: true
          }],
        showAlert: false,
      }
    },

    methods: {

      getProfile() {
        //evt.preventDefault();
        const self = this;
        axios.get(this.$url +'users/' + localStorage.getItem('userId') + '/profile/' + localStorage.getItem('username'), {
          headers: {"auth": localStorage.getItem('auth_token')}
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
              else console.log(response.data.error);
            }
            else if (response.data.results) self.userInfos = response.data.results;
            else console.log("Unresolved response: " + response);
          })
          .catch(function (error) {
            console.log(error);
          })
      },
      updateProfile(evt) {
        evt.preventDefault();
        const self = this;
        axios.put(this.$url +'users/' + localStorage.getItem('userId') + '/profile/' + localStorage.getItem('username'), self.userInfos, {
          headers: {"auth": localStorage.getItem('auth_token')}
        })
          .then(function (response) {
            if (response.data.error) {
              if (response.data.error === "Unauthorized user") console.log("Unauthorized user");
              else console.log(response.data.error);
            }
            else if (response.data.result) {
              console.log("mphka");
              self.userInfos = response.data.result;
              self.showAlert=true;
              console.log(self.userInfos);
            }
            else console.log("Unresolved response: " + response);
          })
          .catch(function (error) {
            console.log(error);
          })
      },
    },
    mounted() {
      if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
      this.getProfile();
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

