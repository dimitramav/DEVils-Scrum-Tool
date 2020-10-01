<template>
    <b-row class="text-font">
        <b-col style="margin-bottom: 15px;">
            <h5>Select Epics:</h5>
            <select
                id="selectEpics"
                v-model="selectedValue"
                @change="loadStories(selectedValue.id)"
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

export default {
    name: 'LoadEpics',
    props: {
        updateBacklogProp: {
            type: Boolean,
            default: false,
        },
    },
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
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis?isEpic=false&epicId=' +
                        idPBI,
                    {
                        headers: {
                            Authorization:
                                'Bearer ' + localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.serverErrorMessage) {
                        console.log(response.data.serverErrorMessage)
                    } else {
                        //self.stories_options = []
                        let stories = response.data
                        // Create an array of object
                        for (let i = 0; i < stories.length; i++) {
                            // Check if this story has already been loaded
                            var storyFound = self.stories_options.some(
                                (story) => story.value == stories[i].id
                            )
                            if (storyFound === false) {
                                self.stories_options.push({
                                    value: stories[i].id,
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
                        '/users/' +
                        localStorage.getItem('userId') +
                        '/projects/' +
                        this.$route.params.id +
                        '/pbis?isEpic=true',
                    {
                        headers: {
                            Authorization:
                                'Bearer ' + localStorage.getItem('auth_token'),
                            'Content-Type': 'application/json',
                        },
                    }
                )
                .then(function (response) {
                    if (response.data.serverErrorMessage) {
                        console.log(response.data.serverErrorMessage)
                    } else {
                        self.epics = response.data
                    }
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },
    watch: {
        updateBacklogProp: function (newVal) {
            if (newVal == true) {
                this.selectedValue = ''
                this.stories_options = []
                this.epics = []
            } else {
                this.getEpics()
            }
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
