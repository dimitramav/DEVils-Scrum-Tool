<template>
<div>
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group id="exampleInputGroup1"
                    label="Email address:"
                    label-for="exampleInput1"
                    description="We'll never share your email with anyone else.">
        <b-form-input id="exampleInput1"
                      type="email"
                      v-model="form.email"
                      required
                      placeholder="Enter email">
        </b-form-input>
      </b-form-group>
      <b-form-group id="exampleInputGroup2"
                    label="Your Name:"
                    label-for="exampleInput2">
        <b-form-input id="exampleInput2"
                      type="text"
                      v-model="form.name"
                      required
                      placeholder="Enter name">
        </b-form-input>
      </b-form-group>
      <b-form-group id="exampleInputGroup3"
                    label="Food:"
                    label-for="exampleInput3">
        <b-form-select id="exampleInput3"
                      :options="foods"
                      required
                      v-model="form.food">
        </b-form-select>
      </b-form-group>
      <b-form-group id="exampleGroup4">
        <b-form-checkbox-group v-model="form.checked" id="exampleChecks">
          <b-form-checkbox value="me">Check me out</b-form-checkbox>
          <b-form-checkbox value="that">Check that out</b-form-checkbox>
        </b-form-checkbox-group>
      </b-form-group>
      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
  </div>
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
