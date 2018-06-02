<template>
  <b-container class="Title" fluid>
    <b-row>
      <p></p>
    </b-row>
    <b-row>
      <b-col>
        <b-img style ="width: 30%" v-on:click="gotoIndex" src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png"></b-img>
      </b-col>
      <b-col cols="8">
      </b-col>
    </b-row>
    <b-row align-h="center">
    </b-row>
<br>
  <b-jumbotron header="Sprint page not ready yet!" lead="It will be available soon!" >
  </b-jumbotron>
  </b-container>

</template>

<script>
    export default {
        name: "OldSprints",
        methods: {
          gotoIndex() {
            this.$router.push({path: '/'});
          }
        },
    }
</script>

<style scoped>
  .Title{
    position: absolute;
    top: 0;
    left: 0;
  }
</style>
