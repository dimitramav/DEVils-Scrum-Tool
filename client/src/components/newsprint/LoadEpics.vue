<template>
    <b-row class="text-font">
        <b-col style="margin-bottom: 15px;">
            <h5>Select Epics:</h5>
            <select
                id="selectEpics"
                v-model="selectedValue"
                @change="loadStories(selectedValue.idPBI)"
            >
                <option value="" disabled>Click to add epics</option>
                <option
                    v-for="(item, index) in epics"
                    :key="index"
                    :value="item"
                >
                    {{ item.title }}
                </option>
            </select>
        </b-col>
    </b-row>
</template>

<script>
import axios from 'axios'
import config from '@/router/auth.js'

export default {
    name: 'LoadEpics',
    data() {
        return {
            selectedValue: '',
            stories_options: [],
            epics: [],
        }
    },
    methods: {
        loadStories(idPBI) {
            // if selectedValue is empty do not call the server
            if (typeof idPBI === 'undefined') return
            const self = this
            axios
                .get(
                    self.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis?epicId=' +
                        idPBI,
                    config
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.results) {
                        //self.stories_options = []
                        let stories = response.data.results
                        // Create an array of object
                        for (let i = 0; i < stories.length; i++) {
                            // Check if this story has already been loaded
                            var storyFound = self.stories_options.some(
                                (story) => story.value == stories[i].idPBI
                            )
                            if (storyFound === false) {
                                self.stories_options.push({
                                    value: stories[i].idPBI,
                                    text: stories[i].title,
                                })
                            } else console.log('Story already loaded')
                        }
                        self.$emit('stories_options', self.stories_options)
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        getEpics() {
            const self = this
            axios
                .get(
                    self.$url +
                        'users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis?isEpic=true',
                    config
                )
                .then(function (response) {
                    if (response.data.error) {
                        console.log(response.data.error)
                    }
                    if (response.data.results) {
                        self.epics = response.data.results
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    mounted() {
        this.getEpics()
    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Quicksand');

.text-font {
    font-family: Quicksand;
}

select {
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 20px;
    padding-right: 20px;
    margin-top: 10px;
    border-radius: 5px;
    background-color: #f3f3f3;
}
</style>
