import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import AddStories from '@/components/sprintbacklog/stories/AddStories'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
const $route = {
    params: {
        id: 2,
        sprintId: 15,
    },
}
// bug in vue-test-utils when mount mock object
if (!process || process.env.NODE_ENV !== 'test') {
    localVue.use(VueRouter)
}

describe('AddStories', () => {
    // Mount AddStories on wrapper
    const wrapper = shallowMount(AddStories, {
        localVue,
        router,
        mocks: {
            $route,
        },
    })
    // Make various tests
    it('AddStories is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'LoadEpics' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'SelectStories' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.ok).toBeFalsy()
        expect(wrapper.vm.stories_options).toBeInstanceOf(Array)
        expect(wrapper.vm.selected_stories).toBeInstanceOf(Array)
        expect(wrapper.vm.pbis_list).toBeInstanceOf(Array)
        expect(wrapper.vm.currentSprint_id).toBe(
            wrapper.vm.$route.params.sprintId
        )
        expect(wrapper.vm.currentProject_id).toBe(wrapper.vm.$route.params.id)
    })
})
