<template>
    <b-container class="Navigation" fluid>
        <b-navbar toggleable="md" class="navbar navbar-expand-lg bg-dark navbar-dark navbar-static-top" variant="info" style="height: 41px;" fluid>
            <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
            <b-navbar variant="faded" type="light">
                <b-navbar-brand href="#">
                    <img src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png" width="45" height="45" alt="BV">
                </b-navbar-brand>
            </b-navbar>
            <b-collapse is-nav id="nav_collapse">
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item-dropdown right>
                        <template slot="button-content">
                            <em>
                  <img src="https://support.rocketchatlauncher.com/wp-content/uploads/2017/03/bell.png" style="width:27px;">
                </em>
                        </template>
                        <b-dropdown-item href="#">#Notification 1</b-dropdown-item>
                        <b-dropdown-item href="#">#Notification 2</b-dropdown-item>
                        <b-dropdown-item href="#">#Notification 3</b-dropdown-item>
                        <b-dropdown-item href="#">#Notification 4</b-dropdown-item>
                    </b-nav-item-dropdown>
                    <b-nav-item-dropdown right>
                        <template slot="button-content">
                            <em>
                  <img src="https://www.mindvoize.com/images/userImage.png" style="width:30px;">
                </em>
                        </template>
                        <b-dropdown-item href="#">Profile</b-dropdown-item>
                        <b-dropdown-item href="#">Signout</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <b-row>
            <p></p>
        </b-row>
        <b-row>
            <b-col>
                <h2> Current Projects</h2>
            </b-col>
            <b-col>
                <br>
            </b-col>
            <b-col>
                <br>
            </b-col>
            <b-col>
                <div>
                    <b-dropdown id="ddown1" text="Create a New Project" class="m-md-2">
                        <b-form inline>
                            Tittle:
                            <label class="sr-only" for="inlineFormInputName2"></label>
                            <b-input class="mb-2 mr-sm-2 mb-sm-0" id="inlineFormInputName2" placeholder="New Project's Name" />
                            <b-button variant="warning" style="margin-top: 5px; height: 35px;">Add to Projects</b-button>
                        </b-form>
                    </b-dropdown>
                </div>
            </b-col>
        </b-row>
        <b-row>
            <br>
        </b-row>
        <b-row>
            <div class="line">.</div>
        </b-row>
        <b-row>
            <b-card-group deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
                <b-card title="Project Title" img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv2Pgjy86vhJ7j3aq6vxH3ZqyfypJfhreVM6_yWWB7dx9ObDkt3w" img-alt="Image" img-top tag="article" style="max-width: 15rem;" class="mb-2">
                    <p class="card-text">
                        Small paragraph describing project's info
                    </p>
                    <b-button href="#" variant="warning">Proceed</b-button>
                </b-card>
                <b-card title="Project Title" img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv2Pgjy86vhJ7j3aq6vxH3ZqyfypJfhreVM6_yWWB7dx9ObDkt3w" img-alt="Image" img-top tag="article" style="max-width: 15rem;" class="mb-2">
                    <p class="card-text">
                        Small paragraph describing project's info
                    </p>
                    <b-button href="#" variant="warning">Proceed</b-button>
                </b-card>
                <b-card title="Project Title" img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv2Pgjy86vhJ7j3aq6vxH3ZqyfypJfhreVM6_yWWB7dx9ObDkt3w" img-alt="Image" img-top tag="article" style="max-width: 15rem;" class="mb-2">
                    <p class="card-text">
                        Small paragraph describing project's info
                    </p>
                    <b-button href="#" variant="warning">Proceed</b-button>
                </b-card>
                <b-card title="Project Title" img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv2Pgjy86vhJ7j3aq6vxH3ZqyfypJfhreVM6_yWWB7dx9ObDkt3w" img-alt="Image" img-top tag="article" style="max-width: 15rem;" class="mb-2">
                    <p class="card-text">
                        Small paragraph describing project's info
                    </p>
                    <b-button href="#" variant="warning">Proceed</b-button>
                </b-card>
            </b-card-group>
        </b-row>
        <b-row>
            <br>
        </b-row>

        <b-row>
            <b-col>
                <h2>Done Projects</h2>
            </b-col>
            <b-col>
                <br>
            </b-col>
            <b-col>
                <br>
            </b-col>
            <b-col>
                <br>
            </b-col>
        </b-row>

        <b-row>
            <br>
        </b-row>
        <b-row>
            <div class="line">.</div>
        </b-row>

        <b-row>
            <b-card-group deck class="mb-3" style="margin: 0 auto;float: none;margin-bottom: 10px;">
                <b-card title="Project Title" img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv2Pgjy86vhJ7j3aq6vxH3ZqyfypJfhreVM6_yWWB7dx9ObDkt3w" img-alt="Image" img-top tag="article" style="max-width: 15rem;" class="mb-2">
                    <p class="card-text">
                        Small paragraph describing project's info
                    </p>
                    <b-button href="#" variant="warning">Proceed</b-button>
                </b-card>
                <b-card title="Project Title" img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv2Pgjy86vhJ7j3aq6vxH3ZqyfypJfhreVM6_yWWB7dx9ObDkt3w" img-alt="Image" img-top tag="article" style="max-width: 15rem;" class="mb-2">
                    <p class="card-text">
                        Small paragraph describing project's info
                    </p>
                    <b-button href="#" variant="warning">Proceed</b-button>
                </b-card>
                <b-card title="Project Title" img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv2Pgjy86vhJ7j3aq6vxH3ZqyfypJfhreVM6_yWWB7dx9ObDkt3w" img-alt="Image" img-top tag="article" style="max-width: 15rem;" class="mb-2">
                    <p class="card-text">
                        Small paragraph describing project's info
                    </p>
                    <b-button href="#" variant="warning">Proceed</b-button>
                </b-card>
                <b-card title="Project Title" img-src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv2Pgjy86vhJ7j3aq6vxH3ZqyfypJfhreVM6_yWWB7dx9ObDkt3w" img-alt="Image" img-top tag="article" style="max-width: 15rem;" class="mb-2">
                    <p class="card-text">
                        Small paragraph describing project's info
                    </p>
                    <b-button href="#" variant="warning">Proceed</b-button>
                </b-card>
            </b-card-group>
        </b-row>
    </b-container>

</template>

<script>
  import axios from 'axios'
  export default {
    name: 'SignUp',
    data() {
      return {
        projects: {
          idProject: '',
          title: '',
          isDone: '',
          deadlineDate: '',
        },
        validEmail: null,
        validPassword: null,
        response: [],
        errors: []
      }
    },

    methods: {
      getProjects () {
        //evt.preventDefault();
        const self = this;
        console.log("auth_token = " + sessionStorage.getItem('auth_token'));
        axios.get('http://localhost:8765/app/api/users/'+sessionStorage.getItem('userId') + '/projects?limit=10', {
          headers: { "auth": sessionStorage.getItem('auth_token') }
        })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          })
      },
    },
    mounted (){
      this.getProjects();
    },
  }
</script>

<style scoped>
    .Navigation {
        position: absolute;
        top: 0;
        left: 0;
    }

    .line {
        width: 99%;
        font-size: 1px;
        line-height: 2px;
        background-color: lavender;
        margin-top: -6px;
        margin-bottom: 10px;
    }
</style>
