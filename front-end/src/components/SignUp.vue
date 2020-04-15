<template>
  <b-container>
    <b-row>
    <b-col></b-col>
    <b-col class="w-100">
      <b-img style ="width: 50%" src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png" v-on:click="gotoIndex"></b-img>
      <br><br>
    <b-form @submit="onSubmit" class="pcsprint">

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

      <b-form-group id="username"
                    label="Username"
                    label-for="username"
                    :invalid-feedback="validUsername===false ? 'Invalid username' : ''"
                    description="Username should not containt '@' character">
        <b-form-input id="username"
                      type="text"
                      v-model="form.username"
                      @change="checkUsername"
                      :state="validUsername"
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
                    description='Password must contain at least eight characters, at least one number and one special character' >
        <b-form-input id="password"
                      type="password"
                      v-model="form.password"
                      :state="checkPassword"
                      required>
        </b-form-input>
      </b-form-group>
      <b-form-group id="verify_password"
                    label="Verify password"
                    label-for="verify_password">
        <b-form-input id="verify_password"
                      type="password"
                      v-model="form.verify_password"
                      :state="verifyPassword"
                      required>
        </b-form-input>
      </b-form-group>
      <br>
      <b-button size="lg" type="submit" variant="primary" :disabled="validEmail===false || validUsername===false || validPassword===false || verifiedPassword===false" > Sign up</b-button>
      <br><br><br>
      <b-button variant="link" v-on:click="gotoSignIn">Already a member? Sign in</b-button>
      <br><br>
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
        username: '',
        firstName: '',
        lastName: '',
        password: '',
        verify_password: '',
      },
      validEmail: null,
      validUsername: null,
      validPassword: null,
      verifiedPassword:null,
    }
  },
  computed: {
    verifyPassword(){
      if (this.form.verify_password==='') {
        this.verifiedPassword=null;
        return null;
      }
      if (this.form.password===this.form.verify_password && this.form.password!=='' &&this.validPassword===true){
        this.verifiedPassword=true;
        return true;
      }
      else {
        this.verifiedPassword=false;
        return false;
      }
    },
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
      axios.post(this.$url+'users', {
        mail: this.form.email,
        username: this.form.username,
        firstname: this.form.firstname,
        lastname: this.form.lastname,
        password: this.form.password
      })
        .then(function (response) {
          if (response.data.results) {
            localStorage.setItem('auth_token', response.data.results.auth_token);
            localStorage.setItem('username', response.data.results.username);
            localStorage.setItem('userId', response.data.results.userId);
            self.$router.push({path: '/'})
          }
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    gotoSignIn() {
      this.$router.push({path: '/signin'});
    },
    gotoIndex() {
      this.$router.push({path: '/'});
    },
    checkEmail() {
      if (this.form.email==='') {
        this.validEmail=null;
        return;
      }
      const self = this;
      axios.post(this.$url +'exists', {
        mail: this.form.email,
      })
        .then(function (response) {
          self.validEmail=(response.data.exists===0);
        })
        .catch(function (error) {
          console.log(error);
        })
    },
    checkUsername() {
      if (this.form.username==='') {
        this.validUsername=null;
        return;
      }
      if (this.form.username.indexOf("@") !== -1) {
        this.validUsername=false;
        return false;
      }
      const self = this;
      axios.post(this.$url +'exists', {
        mail: this.form.username,
      })
        .then(function (response) {
          self.validUsername=(response.data.exists===0);
        })
        .catch(function (error) {
          console.log(error);
        })
    },
  },
  mounted (){
    //redirect to user homepage if user is already logged in
    if (localStorage.getItem('auth_token')!=='null' && localStorage.getItem('userId')!=='null') this.$router.push({path: '/'});
  },
}
</script>

<style scoped>
  @import url('https://fonts.googleapis.com/css?family=Merienda');
  @import url('https://fonts.googleapis.com/css?family=VT323');
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




