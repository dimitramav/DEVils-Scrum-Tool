<template>
  <b-container>
    <b-row>
      <b-col></b-col>
      <b-col class="w-100">
        <b-img style ="width: 50%" src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png" v-on:click="gotoIndex" ></b-img>
        <br><br>
        <b-form @submit="onSubmit">
          <b-alert show variant="warning" v-if="wrongLogin===true">Invalid email or password</b-alert>

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
          <br><br>
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
      const self = this;
      axios.post('http://localhost:8765/app/api/signin', {
        mail: this.form.mail,
        password: this.form.password
      })
        .then(function (response) {
          if (response.data.results) {
            localStorage.setItem('auth_token', response.data.results.auth_token);
            localStorage.setItem('userId', response.data.results.userId);
            self.$router.push({path: '/'})
          }
          else if (response.data.Message) {
            if (response.data.Message === "Wrong Email or Password") {
              self.wrongLogin = true;
              self.form.password = '';
            }
          }


        })
        .catch(function (error) {
          console.log(error);
        });

    },
    gotoIndex() {
      this.$router.push({path: '/'});
    },
    gotoSignUp () {
      this.$router.push({path: '/signup'});
    },
  },
  mounted (){
    //redirect to user homepage if user is already logged in
    if (localStorage.getItem('auth_token')!=='null' && localStorage.getItem('userId')!=='null') this.$router.push({path: '/'});
  },
}
</script>

<style scoped>
</style>
