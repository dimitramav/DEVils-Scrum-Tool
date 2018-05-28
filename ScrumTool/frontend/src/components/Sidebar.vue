<template>
        <div class="sidenav">
            <a href="#Overview">Overview</a>
            <a id="pressed" href="#Backlog">Backlog</a>
            <a href="#Sprints">Sprints</a>
        </div>
    </b-container>
</template>

<script>
  export default {
    name: 'Sidebar',
  }
</script>

<style  scoped>

.Navigation {
    position: fixed;
    top: 0;
    left: 0;
  }

.container-fluid {
    padding-right: 0;
    padding-left:0;
  }

.sidenav {
    height: 100%;
    width: 140px;
    position: fixed;
    z-index:-1;
    top: 0;
    left: 0;
    background-color: #495457;
    padding-top: 100px;
}

.sidenav a {
    padding: 20px 8px 20px 16px;
    text-decoration: none;
    font-family:Courier New;
    font-size: 20px;
    color: white;
    display: block;
}

.sidenav a:hover {
    color: orange;
}

#pressed{
  background-color: #3B4446;
  font-weight: normal;
}

</style>
