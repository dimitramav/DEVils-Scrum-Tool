import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import NewSprint from '@/views/NewSprint.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
const $route = {
    params: {
        id: 2,
    },
}
// bug in vue-test-utils when mount mock object
if (!process || process.env.NODE_ENV !== 'test') {
    localVue.use(VueRouter)
}

describe('NewSprint', () => {
    // Mount NewSprint on wrapper
    const wrapper = shallowMount(NewSprint, {
        localVue,
        router,
        mocks: {
            $route,
        },
    })
    // Make various tests
    it('NewSprint is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'Navbar' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'NewSprintForm' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'LoadEpics' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'SelectStories' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'SubmitButton' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.stories_options).toBeInstanceOf(Array)
        expect(wrapper.vm.selected_stories).toBeInstanceOf(Array)
        expect(wrapper.vm.validDate).toBeFalsy()
    })
    it('Assign values correctly when mounting', () => {
        expect(wrapper.vm.sprint.project.id).toBe(wrapper.vm.$route.params.id)
    })
})
