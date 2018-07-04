<template>
<div>
  <b-btn v-b-toggle.changepass class="m-1">Change Password</b-btn>
  <b-collapse id="changepass" class="mt-2">
    <b-card>
      <p class="card-text">
        <b-form style="text-align: left" >
          <b-form-group id="currentpass"
                        label="Current password"
                        :invalid-feedback="currentPassword===false ? 'Please insert your correct password' : ''"
                        label-for="currentpass">
            <b-form-input id="currentpass"
                          type="password"
                          @change="passwordMatch"
                          :state="currentPassword"
                          v-model="form.password"
                          autocomplete="off" required>
            </b-form-input>
          </b-form-group>
          <b-form-group id="newpass"
                        label="New password"
                        label-for="newpass">
            <b-form-input id="newpass"
                          type="password"
                          :state="checkPassword"
                          v-model="form.newpass"
                          >
            </b-form-input>
          </b-form-group>
          <b-form-group id="verifypass"
                        label="Verify new password"
                        label-for="verifypass">
            <b-form-input id="verifypass"
                          type="password"
                          :state="verifyPassword"
                          v-model="form.verifypass"
                          >
            </b-form-input>
          </b-form-group>
        </b-form>
      </p>
      <div class="text-center">
        <b-btn @click="updatePass" size="sm" :disabled=" validPassword===false || verifiedPassword===false || verifiedPassword===null ||validPassword===null ">Update Password</b-btn>
      </div>
    </b-card>
  </b-collapse>
</div>
</template>

<script>import axios from 'axios'
export default {
  name: "ChangePass",
  data(){
    return {
      validEmail: null,
      form: {
        password: '',
        newpass: '',
        verifypass: '',
      },
      verifiedPassword:null,
      validPassword:null,
      currentPassword: null,
      userInfos:'',
    }
  },
  computed: {
    verifyPassword(){
      if (this.form.verifypass==='') {
        this.verifiedPassword=null;
        return null;
      }
      if (this.form.newpass===this.form.verifypass && this.form.newpass!=='' &&this.validPassword===true){
        this.verifiedPassword=true;
        return true;
      }
      else {
        this.verifiedPassword=false;
        return false;
      }
    },
    checkPassword() {
      if (this.form.newpass==='') return null;
      const regex = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/ ;
      if(this.form.newpass.match(regex)) {
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
    updatePass(){
      const self = this;
      axios.patch(this.$url + 'matches/' + localStorage.getItem('userId'),{
        password: this.form.newpass
      }, {
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function(response) {
          if(response.data.result === true) {
             // Notify User
          }
          else {

          }
        })
        .catch(function(error){

        })

    },
    passwordMatch() {
      const self=this;
      axios.post(this.$url +'matches/' + localStorage.getItem('userId'), {
        password: this.form.password,
      },{
        headers: {"auth": localStorage.getItem('auth_token')}
      })
        .then(function (response) {
          console.log(response.data.exists);
          if(response.data.exists) {
            self.currentPassword = true;
          }
          else {
            self.currentPassword = false;
          }
        })
        .catch(function (error) {
          console.log(error);
          //self.validPassword = false;
        })
    }
  }

}
</script>
