<template>
<div>
    <b-card-group v-for="cur_us in currentUserStories[cur_pbi.idPBI]" :key="cur_us.idPBI"
                    deck style="margin: 0 auto;float: none;margin-bottom: 10px;">
        <b-card class="mb-1">
            <b-card-header header-tag="header" class="p-1" role="tab">
                <b-btn block href="#" v-b-toggle="'collapse'+cur_us.idPBI" variant="info">{{cur_us.title}}</b-btn>
            </b-card-header>
            <b-collapse :id="'collapse'+cur_us.idPBI" visible accordion="my-accordion" role="tabpanel">
                <b-card-body>
                    <p class="card-text">
                        {{cur_us.description}}
                    </p>
                </b-card-body>
            </b-collapse>
        </b-card>
    </b-card-group>
    <!--
    <b-btn v-b-toggle="'collapse'+cur_pbi.idPBI" v-on:click="getEpicUserStories(cur_pbi.idPBI)" variant="primary">Toggle Collapse</b-btn>
    <b-collapse :id="'collapse'+cur_pbi.idPBI" class="mt-2">
      <b-card>
        <p class="card-text">Description:{{currentUserStories[0].description}} </p>
      </b-card>
    </b-collapse>
    -->
</div>
</template>



<script>
import axios from 'axios'

export default {
    name: "BacklogUserStories",
    data() {
        return {
            currentUserStories: [],
        }
    },
    methods: {
        getEpicUserStories() {
            console.log("mphka me id =" + this.epicId);
            const self = this;
            axios.get('http://localhost:8765/app/api/users/' + localStorage.getItem('userId') + '/projects/' + 3
                        + '/pbis?isEpic=false&epicId=' + this.epicId, {
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
                        //self.currentUserStories[epicId] = response.data.results;
                        self.$set(self.currentUserStories, self.epicId, response.data.results);
                        console.log(self.currentUserStories[self.epicId]);

                    }
                })
                .catch(function(error) {
                    console.log(error);
                })
        }
    },
    props: {
        epicId: Number,
    },
    mounted() {
        this.getEpicUserStories();
    },
}
</script>


<style scoped>

</style>
