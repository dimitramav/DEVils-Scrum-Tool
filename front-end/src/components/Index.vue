<template>
<div>
    <template v-if="notLoggedIn()===true">
        <b-container class="Title" style="background-color:#2d2d2d;" fluid>
            <b-row>
                <p></p>
            </b-row>
            <b-row>
                <b-col>
                    <b-img style="min-width:50px; max-width:35%; margin-top: 3px; margin-bottom: -60px" v-on:click="gotoIndex"
                        src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png">
                    </b-img>
                </b-col>
                <b-col cols="8">
                    <h1 align="center" class="text-enhancement" style="margin: 0;padding: 0;color: white;"> Scrum Tool </h1>
                </b-col>
                <b-col>
                    <button type="button" class="btn btn-primary pcsprint" v-on:click="gotoSignIn" style="margin-right:5px">Log In</button>
                    <button type="button" class="btn btn-primary pcsprint" v-on:click="gotoSignUp">Sign Up</button>
                </b-col>
            </b-row>
            <b-row align-h="center">
                <p class="text-enhancement" style="color:white; font-style: italic;font-weight:normal;" align="center">Be agile together! </p>
            </b-row>
            <b-row>
                <p></p>
            </b-row>
            <b-row class="justify-content-md-center" style="margin: 0 auto;float: none; padding-bottom: 2px; padding-top: 2px; background: #464668">
                <b-col col lg="1">
                    <a class="active anchors" href="#home" align="center">home</a>
                </b-col>
                <b-col col lg="1">
                    <a href="#features" class="anchors" align="center">features</a>
                </b-col>
                <b-col col lg="1">
                    <a href="#about" class="anchors" align="center">about</a>
                </b-col>
            </b-row>

            <div id="home" class="Home">
                <img src="../assets/ScrumWordCloud.jpg" class="wordcloud">
                <b-button variant="primary" size="lg" type="button" v-on:click="gotoSignUp" class="pcsprint">Join Free Now</b-button>
                <p>
                    <br />
                </p>
            </div>

            <b-container class="Features" id="features" style="background-color: #ffffff;" fluid>
                <b-row align-h="center">
                    <h2 class="pcsprint" style="font-family:Courier New;"> Features </h2>
                </b-row>
                <b-row>
                    <p></p>
                </b-row>
                <b-row>
                    <p></p>
                </b-row>
                <b-row>
                    <b-col cols="1"></b-col>
                    <b-col cols="4">
                        <b-list-group class="pcsprint" style="text-align:center; font-weight: normal;color:#2d2d2d;font-size: 13px">
                            <b-list-group-item>Implement scrum methodology</b-list-group-item>
                            <b-list-group-item>Free of charge forever</b-list-group-item>
                            <b-list-group-item>Variety of tools to choose by</b-list-group-item>
                            <b-list-group-item>Create & Participate in projects</b-list-group-item>
                            <b-list-group-item>Update Product and Sprint Backlogs</b-list-group-item>
                            <b-list-group-item>Manage your sprints and deadlines</b-list-group-item>
                            <b-list-group-item>Review old and current sprints </b-list-group-item>
                            <b-list-group-item>Edit profiles and view others</b-list-group-item>
                            <b-list-group-item>Invite members , assign roles</b-list-group-item>
                            <b-list-group-item>Create Epics , Tasks , Stories</b-list-group-item>
                        </b-list-group>
                    </b-col>
                    <b-col align-self="center">
                        <iframe width="560" height="315" src="https://www.youtube.com/embed/9leSPphzYeI"
                                frameborder="0" allow="autoplay; encrypted-media" allowfullscreen>
                        </iframe>
                    </b-col>
                </b-row>
            </b-container>

            <b-container class="About Us" id="about" style="background-color: #fbfbff;" fluid>
                <b-row align-h="center">
                    <h2 style="font-family:Courier New;"> About Us </h2>
                </b-row>
                <b-row>
                    <p></p>
                </b-row>
                <b-row>
                    <b-card-group v-for="teamMember in teamData" :key="teamMember.member" deck style="margin: 0 auto;float: none; margin-bottom: 10px;">
                        <b-card :img-src="teamMember.img" img-alt="Img" style="max-width: 9rem" class="mb-2">
                            <h5 style="font-family:Courier New, Courier, monospace;font-size:18px"> {{teamMember.name}} <br><br> </h5>
                            <p class="lang">{{teamMember.lang}}</p>
                            <p class="lang" style="font-size: 16px;">{{teamMember.dev}}</p>
                            <p> <i style="color: rgba(47,75,72,0.85);font-size:13px;"> '{{teamMember.phrase}}'</i></p>
                            <b-card-footer style="font-size:14px">
                                <b-img style="width: 50%" v-on:click="gotoGithub(teamMember.github)"
                                    src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Octicons-mark-github.svg/240px-Octicons-mark-github.svg.png">
                                </b-img>
                            </b-card-footer>
                        </b-card>
                    </b-card-group>
                </b-row>
                <b-row>
                    <p></p>
                </b-row>
                <b-row align-h="center">
                    <p class="description who">
                        - We are a team of junior developers , developing our first project , implementing a web cooperative platform
                        for software management. This platform helps teams to implement software projects based on agile methodology
                        (SCRUM) via backlogs, issues, epics and sprints.
                    </p>
                </b-row>

                <b-container class="bottom" style="background-color:#2d2d2d; margin-top: 5px" fluid>
                    <b-row>
                        <p></p>
                    </b-row>
                    <b-row class="justify-content-md-center" style="margin: 0 auto;float: none;">
                        <!--margin-bottom: 10px;-->
                        <b-col col lg="1">
                            <a class="active anchors" href="#home" align="center">home</a>
                        </b-col>
                        <b-col col lg="1">
                            <a href="#features" class="anchors" align="center">features</a>
                        </b-col>
                        <b-col col lg="1">
                            <a href="#about" class="anchors" align="center">about</a>
                        </b-col>
                    </b-row>
                    <b-row>
                        <p></p>
                    </b-row>
                    <b-row class="justify-content-md-center" style="margin: 0 auto;float: none;">
                        <!--margin-bottom: 10px;-->
                        <b-col cols="12"></b-col>
                        <b-col align-self="end" lg="2">
                            <a class="activ anchors" href="#home" style="font-size:13px;"> terms of use</a>
                        </b-col>
                        <b-col align-self="end" lg="2">
                            <a href="#features" class="anchors" style="font-size:13px;">privacy policy</a>
                        </b-col>
                        <b-col cols="12">
                            <p></p>
                        </b-col>
                    </b-row>
                </b-container>
            </b-container>
        </b-container>
    </template>

    <!--USER HOMEPAGE-->
    <template v-else>
        <b-container class="Navigation" fluid>

            <navbar :dashboard="false"></navbar>

            <b-jumbotron v-show="gotProjects && !totalNumOfCurProjects">
                <h1 class="pcsprint">Welcome to ScrumTool!</h1>
                <b class="pcsprint">No projects yet. Start creating!</b><br><br>
                <b-dropdown id="ddown1" variant="info" size="lg" text="Create a New Project" class="m-md-2 pcsprint">
                    <b-form inline style="margin: 10px;" @submit="newProject">
                        <h4 style="margin-top: -13px">Title:</h4>
                        <label class="sr-only" for="newProjectName"></label>
                        <b-form-input id="newProjectName" placeholder="New Project's Name" v-model="form.newTitle" required style="margin-top: -7px" />
                        <h5 style="margin-top: 5px">Deadline: &nbsp &nbsp &nbsp</h5>
                        <label class="sr-only" for="newProjectDate"></label>

                        <div>
                            <b-form-input id="inputLive" v-model.trim="form.deadlineDate" type="date" :state="dateState" required></b-form-input>
                            <b-form-invalid-feedback id="inputLiveFeedback">
                                Project's Deadline cannot be a past or current date.
                            </b-form-invalid-feedback>
                        </div>
                        <b-button variant="success" size="lg" type="submit" :disabled="dateState===false" style="margin-top: 10px; width: 100%;">Add to Projects</b-button>
                    </b-form>
                </b-dropdown>
            </b-jumbotron>

            <b-container>
                <div v-show="totalNumOfCurProjects">
                    <b-row>
                        <p></p>
                    </b-row>
                    <b-row>
                        <p></p>
                    </b-row>
                    <b-row>
                        <b-col class="text-left" style="padding-top:10px;">
                            <h2 class="pcsprint" style="font-weight:normal;"> Current Projects</h2>
                        </b-col>
                        <b-col class="text-right">
                            <div>
                                <b-dropdown id="ddown1" variant="info" size="lg" text="Create a New Project" class="m-md-2 pcsprint">
                                    <b-form inline style="margin: 10px;" @submit="newProject">
                                        <h4 style="margin-top: -13px">Title:</h4>
                                        <label class="sr-only" for="newProjectName"></label>
                                        <b-form-input id="newProjectName" placeholder="New Project's Name" v-model="form.newTitle" required style="margin-top: -7px" />
                                        <h5 style="margin-top: 5px">Deadline: &nbsp &nbsp &nbsp</h5>
                                        <label class="sr-only" for="newProjectDate"></label>

                                        <div>
                                            <b-form-input id="inputLive" v-model.trim="form.deadlineDate" type="date" :state="dateState" required></b-form-input>
                                            <b-form-invalid-feedback id="inputLiveFeedback">
                                                Project's Deadline cannot be a past or current date.
                                            </b-form-invalid-feedback>
                                        </div>
                                        <b-button variant="success" size="lg" type="submit" :disabled="dateState===false"
                                                style="margin-top: 10px; width: 100%;">Add to Projects
                                        </b-button>
                                    </b-form>
                                </b-dropdown>
                            </div>
                        </b-col>
                    </b-row>
                    <b-row>
                        <div class="line">.</div>
                    </b-row>
                    <br>

                    <b-row>
                        <b-card-group v-for="cur_project in currentProjects" :key="cur_project.idProject" deck style="margin-bottom: 10px; padding-left: 10px;" deck class="mb-2">
                            <b-card img-top tag="article" style="max-width: 15rem;" img-src="https://picsum.photos/600/300/?image=527" img-alt="Image">
                                <h3 class="pcsprint"> {{cur_project.title}}</h3>
                                <p class="card-text pcsprint">
                                    Deadline: {{cur_project.deadlineDate}}
                                </p>
                                <router-link class="pcsprint" tag="b-button" :to="{name: 'ProjectPageOverview', params: {id:cur_project.idProject}}"> Proceed </router-link>
                            </b-card>
                        </b-card-group>
                    </b-row>
                    <br>

                    <!--<b-row v-if="totalNumOfCurProjects===0 && totalNumOfCurProjects===null" class="text-center">-->
                    <!--<b-col></b-col>-->
                    <!--<b-col cols="8">-->
                    <!--<div>-->
                    <!--<b-jumbotron class="pcsprint" header="Welcome to ScrumTool" lead="Create a new project now !" >-->
                    <!--</b-jumbotron>-->
                    <!--</div>-->
                    <!--</b-col>-->
                    <!--<b-col></b-col>-->
                    <!--</b-row>-->

                    <b-row>
                        <!--per-page: max number of records per page - total-rows: total number of records in your data-->
                        <b-col></b-col>
                        <b-col></b-col>
                        <b-col></b-col>
                        <b-col>
                            <template>
                                <div>
                                    <b-pagination v-if="totalNumOfCurProjects" size="md" :total-rows=totalNumOfCurProjects v-model="currentPageCurrent" :per-page="recordsPerPageCurrent">
                                    </b-pagination>
                                </div>
                            </template>
                        </b-col>
                    </b-row>
                </div>

                <div v-if="totalNumOfDoneProjects !== 0">
                    <b-row style="padding-top:10px;">
                        <b-col class="text-left">
                            <h2 class="pcsprint" style="font-weight:normal;">Done Projects</h2>
                        </b-col>
                    </b-row>
                    <b-row>
                        <div class="line">.</div>
                    </b-row>
                    <br>
                    <b-row>
                        <b-card-group v-for="done_project in doneProjects" :key="done_project.idProject" deck>
                            <b-card img-top tag="article" style="max-width: 15rem;" class="mb-2" img-src="https://picsum.photos/600/300/?image=1068" img-alt="Image">
                                <h3 class="pcsprint"> {{done_project.title}} </h3>
                                <p class="card-text pcsprint">
                                    Deadline: {{done_project.deadlineDate}}
                                </p>
                                <router-link class="pcsprint" tag="b-button" :to="{name: 'ProjectPageOverview', params: {id:done_project.idProject}}">Proceed</router-link>
                            </b-card>
                        </b-card-group>
                    </b-row>
                    <br>

                    <b-row>
                        <!--per-page: max number of records per page - total-rows: total number of records in your data-->
                        <b-col></b-col>
                        <b-col></b-col>
                        <b-col></b-col>
                        <b-col>
                            <template>
                                <div>
                                    <b-pagination size="md" :total-rows=totalNumOfDoneProjects v-model="currentPageDone" :per-page="recordsPerPageDone">
                                    </b-pagination>
                                </div>
                            </template>
                        </b-col>
                    </b-row>

                </div>
            </b-container>
        </b-container>
    </template>
</div>
</template>

<script>
import axios from 'axios'
import Navbar from "./Navbar.vue"
import json from '../assets/team.json'
import constVars from '../router/constants.js'
export default {
    components: {
        navbar: Navbar,
    },
    computed: {

        dateState() {
            let mindate = new Date();
            let dd = ("0" + mindate.getDate()).slice(-2);
            let mm = ("0" + (mindate.getMonth() + 1)).slice(-2);
            let yyyy = mindate.getFullYear();
            mindate = yyyy + "-" + mm + "-" + dd;
            if (this.form.deadlineDate === '') return null;
            else return this.form.deadlineDate > mindate
        },
    },
    data() {
        return {
            form: {
                newTitle: '',
                deadlineDate: '',
                isDone: false
            },
            logOut: null,
            currentProjects: [],
            doneProjects: [],
            teamData: json.team,
            isProject: false,
            totalNumOfCurProjects: null,
            gotProjects: false,

            currentPageCurrent: 1,
            recordsPerPageCurrent: constVars.recordsPerPage,
            totalNumOfDoneProjects: 0,
            currentPageDone: 1,
            recordsPerPageDone: constVars.recordsPerPage
        }
    },
    methods: {
        gotoSignIn() {
            this.$router.push({
                path: '/signin'
            });
        },
        gotoIndex() {
            this.$router.push({
                path: '/'
            });
        },
        gotoSignUp() {
            this.$router.push({
                path: '/signup'
            });
        },
        gotoGithub(github) {
            console.log(github);
            var win = window.open(github, '_blank');
            win.focus();
        },
        notLoggedIn() {
            return (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null' || this.logOut);
        },
        getCurrentProjects() {
            //evt.preventDefault();
            const self = this;
            axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects?isDone=false&currentPage=' + self.currentPageCurrent, {
                    headers: {
                        "auth": localStorage.getItem('auth_token')
                    }
                })
                .then(function(response) {
                    if (response.data.error) {
                        if (response.data.error === "Unauthorized user") {
                            console.log("Unauthorized user");
                        }
                    }
                    if (response.data.results) {
                        self.currentProjects = response.data.results;
                        self.gotProjects = true;
                    }
                })
                .catch(function(error) {
                    console.log(error);
                })
        },
        getDoneProjects() {
            const self = this;
            axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects?isDone=true&currentPage=' + self.currentPageDone, {
                    headers: {
                        "auth": localStorage.getItem('auth_token')
                    }
                })
                .then(function(response) {
                    if (response.data.error) {
                        if (response.data.error === "Unauthorized user") {
                            console.log("Unauthorized user");
                        }
                    }
                    if (response.data.results) {
                        self.doneProjects = response.data.results;
                    }
                })
                .catch(function(error) {
                    console.log(error);
                })
        },
        newProject(evt) {
            evt.preventDefault();
            const self = this;
            let config = {
                headers: {
                    "auth": localStorage.getItem('auth_token'),
                    "Content-Type": 'application/json'
                }
            };
            let data = {
                title: this.form.newTitle,
                isDone: this.form.isDone,
                deadlineDate: this.form.deadlineDate
            };
            axios.post(this.$url + 'users/' + localStorage.getItem('userId') + '/projects', data, config)
                .then(function(response) {
                    if (response.data.error) {
                        if (response.data.error === "Unauthorized user") {
                            console.log("Unauthorized user");
                        } else if (response.data.error === "Unauthorized projects") {
                            console.log("Unauthorized projects");
                        } else if (response.data.error === "System Exception") {
                            console.log("System Exception");
                        } else if (response.data.error === "null") {
                            console.log("Null token");
                        }
                    }
                    if (response.data.results) {
                        console.log(response.data.results);
                        self.currentProjects.push(response.data.results);
                        self.getNumOfCurProjects();
                    }
                })
                .catch(function(error) {
                    console.log(error);
                })
        },
        logout() {
            localStorage.setItem('userId', 'null');
            localStorage.setItem('auth_token', 'null');
            localStorage.setItem('username', 'null');
            this.logOut = true;
        },
        getNumOfCurProjects() {
            const self = this;
            axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects?isDone=false', {
                    headers: {
                        "auth": localStorage.getItem('auth_token')
                    }
                })
                .then(function(response) {
                    if (response.data.error) {
                        console.log(response.data.error);
                    }
                    if (response.data.results) {
                        self.totalNumOfCurProjects = response.data.results;
                        console.log(self.totalNumOfCurProjects);
                    }
                })
                .catch(function(error) {
                    console.log(error);
                })
        },
        getNumOfDoneProjects() {
            const self = this;
            axios.get(this.$url + 'users/' + localStorage.getItem('userId') + '/projects?isDone=true', {
                    headers: {
                        "auth": localStorage.getItem('auth_token')
                    }
                })
                .then(function(response) {
                    if (response.data.error) {
                        console.log(response.data.error);
                    }
                    if (response.data.results) {
                        self.totalNumOfDoneProjects = response.data.results;
                        //console.log(self.totalNumOfDoneProjects);
                    }
                })
                .catch(function(error) {
                    console.log(error);
                })
        }
    },
    mounted() {
        if (localStorage.getItem('auth_token') === 'null' || localStorage.getItem('userId') === 'null') return;
        this.getNumOfCurProjects();
        this.getNumOfDoneProjects();
        this.getCurrentProjects();
        this.getDoneProjects();
    },
    watch: {
        currentPageCurrent: function() {
            this.getCurrentProjects();
        },
        currentPageDone: function() {
            this.getDoneProjects();
        }
    }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Merienda');
@import url('https://fonts.googleapis.com/css?family=VT323');
@import url('https://fonts.googleapis.com/css?family=Antic');
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.ptitle {
    font-family: Merienda;
}

.pdeadline {
    font-family: VT323;
    font-size: 24px;
    margin-top: 1%;
}

.pcsprint {
    font-family: Quicksand;
}

.Title {
    position: absolute;
    top: 0;
    left: 0;
}

.Home {
    position: absolute;
    background-color: #fafaff;
    width: 100%;
    left: 0;
}

.wordcloud {
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 51%;
}

.Features {
    font-family: Quicksand;
    position: absolute;
    top: 540%;
    left: 0;
}

.stamp {
    border-style: double;
    border-color: grey;
    border-width: 4px;
}

.About {
    font-family: Quicksand;
    position: absolute;
    top: 960%;
    left: 4px;
}

.devs {
    height: 50%;
    width: 50%;
}

.bottom {
    position: absolute;
    left: -5px;
    width: 100%;
}

.description {
    font-size: 14px;
    width: 500px;
    text-align: justify;
    word-wrap: break-word;
    padding: 8px;
    text-indent: 50px;
    text-align: justify;
    letter-spacing: 3px;
}

.Navigation {
    position: absolute;
    top: 0;
    left: 0;
}

.line {
    width: 99%;
    font-size: 1px;
    line-height: 2px;
    background-color: #e6edf2;
    margin-top: 10px;
    margin-bottom: 10px;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}

.container-fluid {
    padding-right: 0;
    padding-left: 0;
}

.text-enhancement {
    font-family: 'Merienda', cursive;
}

.anchors {
    font-family: 'Merienda', cursive;
    color: white;
    font-style: italic;
    font-weight: 450;
}

.lang {
    font-family: VT323;
    font-size: 25px;
}

.who {
    font-family: Quicksand;
}
</style>
