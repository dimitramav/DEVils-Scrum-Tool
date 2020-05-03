<template>
    <b-container fluid>
        <b-container id="home" class="Home" fluid>
            <b-row><p></p></b-row>
            <img src="@/assets/ScrumWordCloud.jpg" class="wordcloud" />
            <b-button
                id="join"
                variant="primary"
                size="lg"
                v-on:click="gotoSignUp"
                style="font-family: Quicksand;"
                >Join Free Now</b-button
            >
            <br /><br /><br />
        </b-container>
        <b-container id="features" fluid>
            <b-row><p></p></b-row>
            <b-row align-h="center">
                <h2 style="font-family: Courier New;">Features</h2>
            </b-row>
            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="4">
                    <b-list-group
                        v-for="item in featuresData"
                        :key="item"
                        class="Features"
                    >
                        <b-list-group-item>{{ item }}</b-list-group-item>
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
            <br /><br />
        </b-container>

        <b-container id="about" class="About" fluid>
            <b-row><p></p></b-row>
            <b-row align-h="center">
                <h2 style="font-family: Courier New;">About Us</h2>
            </b-row>
            <b-row><p></p></b-row>
            <b-row>
                <b-card-group
                    v-for="teamMember in teamData"
                    :key="teamMember.member"
                    style="margin: 0 auto; float: none; margin-bottom: 10px;"
                >
                    <b-card :img-src="teamMember.img" style="max-width: 9rem;">
                        <h5 style="font-family: Courier New; font-size: 18px;">
                            {{ teamMember.name }} <br /><br />
                        </h5>
                        <p class="lang">{{ teamMember.dev }}</p>
                        <p>
                            <i style="font-size: 13px;">
                                '{{ teamMember.phrase }}'</i
                            >
                        </p>
                        <b-button
                            style="font-size: 48px; color: #494965;"
                            variant="link"
                            v-on:click="gotoGithub(teamMember.github)"
                        >
                            <i class="fa fa-github"></i>
                        </b-button>
                    </b-card>
                </b-card-group>
            </b-row>
            <b-row><p></p></b-row>
            <b-row align-h="center">
                <p class="description">
                    - We are a team of junior developers, developing our first
                    project, implementing a web cooperative platform for
                    software management. This platform helps teams to implement
                    software projects based on agile methodology (SCRUM) via
                    backlogs, issues, epics and sprints.
                </p>
            </b-row>
            <b-container class="bottom" fluid>
                <b-row><p></p></b-row>
                <b-row class="justify-content-md-center">
                    <p style="color: white;">&copy; Under MIT Lisence</p>
                </b-row>
            </b-container>
        </b-container>
    </b-container>
</template>

<script>
import teamJson from '@/assets/team.json'
import featuresJson from '@/assets/features.json'

export default {
    name: 'IndexDefault',
    data() {
        return {
            teamData: teamJson.team,
            featuresData: featuresJson.features,
        }
    },
    methods: {
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
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.Home {
    background-color: #fafaff;
    width: 100%;
}

.wordcloud {
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 51%;
}

.About {
    background-color: #fbfbff;
    font-family: Quicksand;
}

.Features {
    font-family: Quicksand;
    text-align: center;
    font-weight: normal;
    color: #2d2d2d;
    font-size: 13px;
}

.bottom {
    position: absolute;
    background-color: #2d2d2d;
    left: 0px;
}

.description {
    font-size: 14px;
    width: 500px;
    text-align: justify;
    word-wrap: break-word;
    font-family: Quicksand;
    padding: 8px;
    text-indent: 50px;
    text-align: justify;
    letter-spacing: 3px;
}

.row {
    margin-right: 0px;
    margin-left: 0px;
}

.lang {
    font-family: VT323;
    font-size: 25px;
}
</style>
