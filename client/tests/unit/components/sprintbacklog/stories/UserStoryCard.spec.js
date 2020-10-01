import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import UserStoryCard from '@/components/sprintbacklog/stories/UserStoryCard.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()

describe('UserStoryCard', () => {
    // Mount UserStoryCard on wrapper
    const wrapper = shallowMount(UserStoryCard, {
        localVue,
        router,
        propsData: {
            story: {
                title: 'storyTitle',
                idPBI: 1,
            },
        },
    })
    // Make various tests
    it('UserStoryCard is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'AddTask' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'RemoveStory' }).exists()).toBeTruthy()
    })
})
