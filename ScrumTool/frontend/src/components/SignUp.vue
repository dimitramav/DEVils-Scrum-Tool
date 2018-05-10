<template>
<div>
  <button class=”Search__button” @click="callRestService()">CALL Spring Boot REST backend service</button>
  <p>{{response}}</p>
</div>
</template>

<script>import axios from 'axios'
export default {
  name: 'SignUp',
  data () {
    return {
      response: [],
      errors: []
    }
  },
  methods: {
    // Fetches posts when the component is created.
    callRestService () {
      axios.get(`http://localhost:8765/app/api/projects`, { headers: { auth: 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYWxhQGdtYWlsLmNvbSJ9.xoWiGcX5ekJsXF6gkFze16JmLODQW0oCGHgQ462lr_OE2Dt_yKmacI_3iQ4NVKAabDunmst066c2xtFI25uhnw' } })
        .then(response => {
          // JSON responses are automatically parsed.
          this.response = response.data
          console.log(response.data)
        })
        .catch(e => {
          this.errors.push(e)
        })
    }
  }
}
</script>
