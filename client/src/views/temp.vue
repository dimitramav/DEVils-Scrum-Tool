<template>
    <b-container class="Title" style="background-color: #2d2d2d;" fluid>
        <div class="indexDefault">
            <b-row>
                <p></p>
            </b-row>
            <b-row>
                <b-col>
                    <b-img
                        style="
                            min-width: 50px;
                            max-width: 35%;
                            margin-top: 3px;
                            margin-bottom: -60px;
                        "
                        v-on:click="gotoIndex"
                        src="https://cdn1.iconfinder.com/data/icons/flat-badges-vol-1/128/kanban-512.png"
                    >
                    </b-img>
                </b-col>
                <b-col cols="8">
                    <h1
                        align="center"
                        class="text-enhancement"
                        style="margin: 0; padding: 0; color: white;"
                    >
                        Scrum Tool
                    </h1>
                </b-col>
                <b-col>
                    <button
                        type="button"
                        class="btn btn-primary pcsprint"
                        v-on:click="gotoSignIn"
                        style="margin-right: 5px;"
                    >
                        Log In
                    </button>
                    <button
                        type="button"
                        class="btn btn-primary pcsprint"
                        v-on:click="gotoSignUp"
                    >
                        Sign Up
                    </button>
                </b-col>
            </b-row>
            <b-row align-h="center">
                <p
                    class="text-enhancement"
                    style="
                        color: white;
                        font-style: italic;
                        font-weight: normal;
                    "
                    align="center"
                >
                    Be agile together!
                </p>
            </b-row>
            <b-row>
                <p></p>
            </b-row>
            <b-row
                class="justify-content-md-center"
                style="
                    margin: 0 auto;
                    float: none;
                    padding-bottom: 2px;
                    padding-top: 2px;
                    background: #464668;
                "
            >
                <b-col col lg="1">
                    <a href="#home" class="active anchors" align="center"
                        >home</a
                    >
                </b-col>
                <b-col col lg="1">
                    <a href="#features" class="anchors" align="center"
                        >features</a
                    >
                </b-col>
                <b-col col lg="1">
                    <a href="#about" class="anchors" align="center">about</a>
                </b-col>
            </b-row>

            <div id="home" class="Home">
                <img src="../assets/ScrumWordCloud.jpg" class="wordcloud" />
                <b-button
                    variant="primary"
                    size="lg"
                    type="button"
                    v-on:click="gotoSignUp"
                    class="pcsprint"
                    >Join Free Now</b-button
                >
                <p>
                    <br />
                </p>
            </div>

            <b-container
                class="Features"
                id="features"
                style="background-color: #ffffff;"
                fluid
            >
                <b-row align-h="center">
                    <h2 class="pcsprint" style="font-family: Courier New;">
                        Features
                    </h2>
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
                        <b-list-group
                            class="pcsprint"
                            style="
                                text-align: center;
                                font-weight: normal;
                                color: #2d2d2d;
                                font-size: 13px;
                            "
                        >
                            <b-list-group-item
                                >Implement scrum methodology</b-list-group-item
                            >
                            <b-list-group-item
                                >Free of charge forever</b-list-group-item
                            >
                            <b-list-group-item
                                >Variety of tools to choose
                                by</b-list-group-item
                            >
                            <b-list-group-item
                                >Create and Participate in
                                projects</b-list-group-item
                            >
                            <b-list-group-item
                                >Update Product and Sprint
                                Backlogs</b-list-group-item
                            >
                            <b-list-group-item
                                >Manage your sprints and
                                deadlines</b-list-group-item
                            >
                            <b-list-group-item
                                >Review old and current sprints
                            </b-list-group-item>
                            <b-list-group-item
                                >Edit profiles and view
                                others</b-list-group-item
                            >
                            <b-list-group-item
                                >Invite members, assign roles</b-list-group-item
                            >
                            <b-list-group-item
                                >Create Epics, Tasks, Stories</b-list-group-item
                            >
                        </b-list-group>
                    </b-col>
                    <b-col align-self="center">
                        <iframe
                            width="560"
                            height="315"
                            src="https://www.youtube.com/embed/9leSPphzYeI"
                            frameborder="0"
                            allow="autoplay; encrypted-media"
                            allowfullscreen
                        >
                        </iframe>
                    </b-col>
                </b-row>
            </b-container>

            <b-container
                class="About Us"
                id="about"
                style="background-color: #fbfbff;"
                fluid
            >
                <b-row align-h="center">
                    <h2 style="font-family: Courier New;">About Us</h2>
                </b-row>
                <b-row>
                    <p></p>
                </b-row>
                <b-row>
                    <b-card-group
                        v-for="teamMember in teamData"
                        :key="teamMember.member"
                        deck
                        style="
                            margin: 0 auto;
                            float: none;
                            margin-bottom: 10px;
                        "
                    >
                        <b-card
                            :img-src="teamMember.img"
                            img-alt="Img"
                            style="max-width: 9rem;"
                            class="mb-2"
                        >
                            <h5
                                style="
                                    font-family: Courier New, Courier, monospace;
                                    font-size: 18px;
                                "
                            >
                                {{ teamMember.name }} <br /><br />
                            </h5>
                            <p class="lang">{{ teamMember.lang }}</p>
                            <p class="lang" style="font-size: 16px;">
                                {{ teamMember.dev }}
                            </p>
                            <p>
                                <i
                                    style="
                                        color: rgba(47, 75, 72, 0.85);
                                        font-size: 13px;
                                    "
                                >
                                    '{{ teamMember.phrase }}'</i
                                >
                            </p>
                            <b-card-footer style="font-size: 14px;">
                                <b-img
                                    style="width: 50%;"
                                    v-on:click="gotoGithub(teamMember.github)"
                                    src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Octicons-mark-github.svg/240px-Octicons-mark-github.svg.png"
                                >
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
                        - We are a team of junior developers, developing our
                        first project, implementing a web cooperative platform
                        for software management. This platform helps teams to
                        implement software projects based on agile methodology
                        (SCRUM) via backlogs, issues, epics and sprints.
                    </p>
                </b-row>

                <b-container
                    class="bottom"
                    style="background-color: #2d2d2d; margin-top: 5px;"
                    fluid
                >
                    <b-row>
                        <p></p>
                    </b-row>
                    <b-row
                        class="justify-content-md-center"
                        style="margin: 0 auto; float: none;"
                    >
                        <!--margin-bottom: 10px;-->
                        <b-col col lg="1">
                            <a
                                class="active anchors"
                                href="#home"
                                align="center"
                                >home</a
                            >
                        </b-col>
                        <b-col col lg="1">
                            <a href="#features" class="anchors" align="center"
                                >features</a
                            >
                        </b-col>
                        <b-col col lg="1">
                            <a href="#about" class="anchors" align="center"
                                >about</a
                            >
                        </b-col>
                    </b-row>
                    <b-row>
                        <p></p>
                    </b-row>
                    <b-row
                        class="justify-content-md-center"
                        style="margin: 0 auto; float: none;"
                    >
                        <!--margin-bottom: 10px;-->
                        <b-col cols="12"></b-col>
                        <b-col align-self="end" lg="2">
                            <a
                                href="#home"
                                class="activ anchors"
                                style="font-size: 13px;"
                                >terms of use</a
                            >
                        </b-col>
                        <b-col align-self="end" lg="2">
                            <a
                                href="#features"
                                class="anchors"
                                style="font-size: 13px;"
                                >privacy policy</a
                            >
                        </b-col>
                        <b-col cols="12">
                            <p></p>
                        </b-col>
                    </b-row>
                </b-container>
            </b-container>
        </div>
    </b-container>
</template>

<script>
import json from '@/assets/team.json'

export default {
    name: 'IndexDefault',
    data() {
        return {
            teamData: json.team,
        }
    },
    methods: {
        gotoIndex() {
            this.$router.push({
                path: '/',
            })
        },
        gotoSignIn() {
            this.$router.push({
                path: '/signin',
            })
        },
        gotoSignUp() {
            this.$router.push({
                path: '/signup',
            })
        },
        gotoGithub(github) {
            console.log(github)
            var win = window.open(github, '_blank')
            win.focus()
        },
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Merienda');
@import url('https://fonts.googleapis.com/css?family=VT323');
@import url('https://fonts.googleapis.com/css?family=Antic');
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.pcsprint {
    font-family: Quicksand;
}

.Home {
    position: absolute;
    background-color: #fafaff;
    width: 100%;
}

.wordcloud {
    display: block;
    margin-left: auto;
    margin-right: auto;
}

.Features {
    font-family: Quicksand;
    position: absolute;
    top: 540%;
    left: 0;
}

.About {
    font-family: Quicksand;
    position: absolute;
    top: 960%;
}

.bottom {
    position: absolute;
    left: 0px;
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

.row {
    margin-right: 0px;
    margin-left: 0px;
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
