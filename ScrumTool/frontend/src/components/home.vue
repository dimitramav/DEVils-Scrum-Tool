<template>
  <div>
    <b-alert show dismissible>User Homepage!</b-alert>
    <b-button variant = "success" v-on:click="DOSE">DOSTA</b-button>
    {{auth_data.userId}}
    {{auth_data.token}}

  </div>
</template>

<script>
export default {
  data () {
    return {
      auth_data: {
        token: '',
        userId: '',
      },
    }
  },
  methods: {
    DOSE () {
      this.auth_data.token = sessionStorage.getItem('auth_token');
      this.auth_data.userId = sessionStorage.getItem('userId');
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>



