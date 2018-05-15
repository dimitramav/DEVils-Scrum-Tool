<template>

  <b-container>
    <b-row>
    <b-col></b-col>
    <b-col class="w-100">

        <!--<b-img src="../../../src/main/webapp/assets/kanban-512.png"></b-img>-->
      <b-img style ="width: 50%" src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png"></b-img>


        <!--<b-card :src="require('https://lorempixel.com/300/150/')"></b-card>-->
        <!--<b-img style ="width: 50%" src="https://static1.squarespace.com/static/538df61ae4b055cc3310e47f/t/59124725893fc098b54c30a7/1494370113194/vassardevil.jpg"></b-img>-->
      <br><br>
    <b-form @submit="onSubmit" id="form_signup">

      <b-form-group id="emailForm"
                    label="Email address"
                    label-for="email"
                    :invalid-feedback="validEmail===false ? 'Email is already in use' : ''"
                    description="We'll never share your email with anyone else.">
        <b-form-input id="emailInput"
                      type="email"
                      v-model="form.email"
                      @change="checkEmail"
                      :state="validEmail"
                      autocomplete="off" required> <!--autocomplete has a bug and v-model does not syncronize it-->
        </b-form-input>

      </b-form-group>
      <b-form-group id="firstname"
                    label="First Name"
                    label-for="firstname">
        <b-form-input id="firstname"
                      type="text"
                      v-model="form.firstname"
                      required>
        </b-form-input>
      </b-form-group>
      <b-form-group id="lastname"
                    label="Last Name"
                    label-for="lastname">
        <b-form-input id="lastname"
                      type="text"
                      v-model="form.lastname"
                      required>
        </b-form-input>
      </b-form-group>

      <b-form-group id="password"
                    label="Password"
                    label-for="password"
                    description='Password must contain at least eight characters, at least one number and both lower and special characters' >
        <b-form-input id="password"
                      type="password"
                      v-model="form.password"
                      :state="checkPassword"
                      required>
        </b-form-input>
      </b-form-group>
      <br>
      <b-button size="lg" type="submit" variant="primary" :disabled="validEmail===false || validPassword===false" > Sign up</b-button>
      <br><br><br>
      <b-button variant="link" v-on:click="gotoSignIn">Already a member? Sign in</b-button>
    </b-form>
    </b-col>
    <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>import axios from 'axios'
export default {
  name: 'SignUp',
  data() {
    return {
      form: {
        email: '',
        firstName: '',
        lastName: '',
        password: '',
      },
      validEmail: null,
      validPassword: null,
      response: [],
      errors: []
    }
  },
  computed: {
    checkPassword() {
      if (this.form.password==='') return null;
      const regex = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/ ;
      if(this.form.password.match(regex)) {
        this.validPassword=true;
        return true;
      }
      else {
        this.validPassword=false;
        return false;
      }
    },
  },
  methods: {
    onSubmit (evt) {
      evt.preventDefault();
      const self = this;
      console.log("mphka sto on submit");
      axios.post('http://localhost:8765/app/api/users', {
        mail: this.form.email,
        firstname: this.form.firstname,
        lastname: this.form.lastname,
        password: this.form.password
      })
        .then(function (response) {
          console.log("mphka sto then");
          if (response.data.results) {
            console.log("mphka sthn if");
            sessionStorage.setItem('auth_token', response.data.results.auth_token);
            sessionStorage.setItem('userId', response.data.results.userId);
            self.$router.push({path: '/home'})
          }
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    gotoSignIn() {
      this.$router.push({path: '/signin'});
    },
    checkEmail() {
      const self = this;
      axios.post('http://localhost:8765/app/api/exists', {
        mail: this.form.email,
      })
        .then(function (response) {
          self.validEmail=(response.data.exists===0);
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  }
}
</script>

<style scoped>
  #form_signup {
    display: table-cell;
    text-align: center;
    vertical-align: central;
  }
</style>




