<template>
  <b-container style="padding-top:100px;">
      <b-row>
      <b-col></b-col>
      <b-col>
      <b-form @submit="onSubmit" id="form_login">
        <b-alert show variant="warning" v-if="wrongLogin===true">Wrong email or password</b-alert>

        <b-form-group id="mail_group">
          <b-form-input id="mail"
                        type="email"
                        v-model="form.mail"
                        autocomplete="off"
                        placeholder="Enter email"
                        required>
          </b-form-input>
        </b-form-group>


        <b-form-group id="password_group">
          <b-form-input id="password"
                        type="password"
                        v-model="form.password"
                        required
                        placeholder="Enter password">
          </b-form-input>
        </b-form-group>
        <b-button size = "sm" variant="link">Forgot your password?</b-button>
        <br><br>
        <b-button size="lg" type="submit" variant="primary"> Sign in</b-button>
        <br><br><br>
        <b-button variant="link" v-on:click="gotoSignUp">Not a member? Sign up</b-button>
      </b-form>
      </b-col>
      <b-col></b-col>
      </b-row>
  </b-container>
</template>

<script>import axios from 'axios'
export default {
  data () {
    return {
      form: {
        mail: '',
        password: '',
      },
      wrongLogin: false,
    }
  },
  methods: {
    onSubmit (evt) {
      evt.preventDefault();
      // alert(JSON.stringify(this.form));
      const self = this;
      axios.post('http://localhost:8765/app/api/signin', {
        mail: this.form.mail,
        password: this.form.password
      })
        .then(function (response) {
          console.log(response);
          if (response.data.results) {
            localStorage.setItem('auth_token', response.data.results.auth_token);
            localStorage.setItem('userId', response.data.results.userId);
            self.$router.push({path: '/home'})
          }
          else if (response.data.Message) {
            if (response.data.Message === "Wrong Email or Password") {
              console.log("wrong email or password");
              self.wrongLogin = true;
              self.form.password = '';
            }
          }


        })
        .catch(function (error) {
          console.log(error);
        });

    },
    gotoSignUp () {
      this.$router.push({path: '/signup'});
    },
  }
}
</script>

<style scoped>
  #form_login {
    display: table-cell;
    text-align: center;
    vertical-align: central;
  }
</style>
