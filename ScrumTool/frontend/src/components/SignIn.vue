<template>
  <b-container>

      <b-form @submit="onSubmit" id="form_login">
        <b-form-group id="mail_group">
          <b-form-input id="mail"
                        type="email"
                        v-model="form.mail"
                        required
                        placeholder="Enter email">
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
        <b-button variant="link">Forgot your password?</b-button>
        <br><br>
        <b-button size="lg" type="submit" variant="primary"> Log in</b-button>
        <br><br><br>
        <b-button variant="link">Not a member? Sign up</b-button>

      </b-form>
  </b-container>
</template>

<script> import axios from 'axios'
export default {
  data () {
    return {
      form: {
        mail: '',
        password: '',
      },
    }
  },
  methods: {
    onSubmit () {
      // evt.preventDefault();
      // alert(JSON.stringify(this.form));

      axios.post('http://localhost:8765/app/api/signin', {
        mail: this.form.mail,
        password: this.form.password
      })
        .then(function (response) {
          console.log(response);
          var object = JSON.parse(response.data);
          //console.log(response.data.authtoken);

          sessionStorage.setItem('token', response.data.authtoken);
          var data = sessionStorage.getItem('token');
          console.log(data);
          //this.$router.push({path: '/'}) // It's works!!
        })
        .catch(function (error) {
          console.log(error);
        });

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
