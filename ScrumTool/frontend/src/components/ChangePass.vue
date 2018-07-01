<template>
<div>
  <b-btn v-b-toggle.changepass class="m-1">Change Password</b-btn>
  <b-collapse id="changepass" class="mt-2">
    <b-card>
      <p class="card-text">
        <b-form style="text-align: left" >
          <b-form-group id="currentpass"
                        label="Current password"
                        label-for="currentpass">
            <b-form-input id="currentpass"
                          type="password"
                          v-model="form.currentpass">
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
      form: {
        currentpass: '',
        newpass: '',
        verifypass: '',
      },
      verifiedPassword:null,
      validPassword:null,
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

    }
  }

}
</script>
