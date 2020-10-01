import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import LoadStories from '@/components/sprintbacklog/stories/LoadStories'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()

describe('LoadStories', () => {
    // Mount LoadStories on wrapper
    const getSprintStories = jest.fn()
    const wrapper = shallowMount(LoadStories, {
        localVue,
        router,
        methods: {
            getSprintStories,
        },
    })
    // Make various tests
    it('LoadStories is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Calls getSprintStories on mount', () => {
        expect(getSprintStories).toBeCalled()
    })
})
