<template>
    <b-row class="text-font">
        <h5>Click to add a user story:</h5>
        <b-form-select
            v-model="selected"
            :options="stories_options"
            :select-size="4"
            style="min-width: 280px;"
        >
        </b-form-select>
        <h5 style="margin-top: 15px;">
            User Stories selected for sprint: (click to remove)
        </h5>
        <b-form-select
            v-model="toremove"
            :options="selected_stories"
            :select-size="4"
            style="min-width: 280px;"
        >
        </b-form-select>
    </b-row>
</template>

<script>
export default {
    name: 'SelectStories',
    props: {
        updateBacklogProp: {
            type: Boolean,
            default: false,
        },
        stories_options: Array,
    },
    data() {
        return {
            toremove: null,
            selected: null,
            selected_stories: [],
        }
    },
    methods: {
        selectStory(value) {
            // filter using the pbi id
            var result = this.stories_options.filter(function (obj) {
                return obj.value == value
            })
            var find_pbi = this.selected_stories.find(function (find_pbi) {
                return find_pbi.value === value
            })
            if (find_pbi == undefined) {
                this.selected_stories.push({
                    value: value,
                    text: result[0].text,
                })
                //console.log('selected ', value)
            }
            this.$emit('selected_stories', this.selected_stories)
        },
        removeStory(value) {
            var index = -1
            for (var x in this.selected_stories) {
                if (this.selected_stories[x].value == value) {
                    index = x
                    break
                }
            }
            //console.log('position ', index)
            if (index >= 0) {
                this.selected_stories.splice(index, 1)
                //console.log('removed ', value)
            }
            this.$emit('selected_stories', this.selected_stories)
        },
    },
    watch: {
        selected: function (value) {
            this.selectStory(value)
        },
        toremove: function (value) {
            this.removeStory(value)
        },
        updateBacklogProp: function (newVal) {
            if (newVal == true) {
                this.selected_stories = []
            }
        },
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
