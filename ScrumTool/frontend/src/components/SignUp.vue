<!--<template>-->
<!--<div>-->
<!--<button class=”Search__button” @click="callRestService()">CALL Spring Boot REST backend service</button>-->
<!--<p>{{response}}</p>-->
<!--</div>-->
<!--</template>-->

<template>
  <b-container style="padding-top:100px;">
    <b-row>
    <b-col></b-col>
    <b-col class="w-100">
    <b-form @submit="onSubmit" id="form_signup">
      <b-form-group id="emailForm"
                    label="Email address"
                    label-for="email"
                    :invalid-feedback="wrongEmail===true ? 'Email is already in use' : ''"
                    description="We'll never share your email with anyone else.">
        <b-form-input id="emailInput"
                      type="email"
                      v-model="form.email"
                      @change="checkEmail"
                      :state="rightEmail"
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
                    label-for="password">
        <b-form-input id="password"
                      label="Password"
                      label-for="password"
                      type="password"
                      v-model="form.password"
                      required>
        </b-form-input>
      </b-form-group>
      <br>
      <b-button size="lg" type="submit" variant="primary" :disabled="wrongEmail===true" > Sign up</b-button>
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
      wrongEmail: null,
      rightEmail: null, //we need this because vue directives throw errors in ! commands
      response: [],
      errors: []
    }
  },
  methods: {
    onSubmit () {
      // evt.preventDefault();
      // alert(JSON.stringify(this.form));
      // axios.get(`http://localhost:8765/app/api/projects`, { headers: { auth: 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYWxhQGdtYWlsLmNvbSJ9.s-cqurwwavJEr8KE5vinX6TroN-1GXwWvI0YZTRtCRk4FT6fB3uSiZ08nwZEY3bKBFbC4eWhupzTUkxjfLNBYA' } })
      const self = this;
      axios.post('http://localhost:8765/app/api/users', {
        mail: this.form.email,
        firstname: this.form.firstname,
        lastname: this.form.lastname,
        password: this.form.password
      })
        .then(function (response) {
          if (response.data.results) {
            sessionStorage.setItem('auth_token', response.data.results.auth_token);
            sessionStorage.setItem('userId', response.data.results.userId);
            self.$router.push({path: '/home'})
          }
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
          self.wrongEmail=(response.data.exists===1);
          self.rightEmail=!self.wrongEmail;
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




