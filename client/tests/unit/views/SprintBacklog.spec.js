import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import SprintBacklog from '@/views/SprintBacklog.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()

describe('SprintBacklog', () => {
    // Mount SprintBacklog on wrapper
    const getTasks = jest.fn()
    const wrapper = shallowMount(SprintBacklog, {
        localVue,
        router,
        methods: {
            getTasks,
        },
    })
    // Make various tests
    it('SprintBacklog is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'Navbar' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'SprintHeader' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'LoadStories' }).exists()).toBeTruthy()
        //expect(wrapper.find({ name: 'UserStoryCard' }).exists()).toBeTruthy()
        //expect(wrapper.find({ name: 'TaskGroup' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'AddStories' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.currentStories).toBeInstanceOf(Array)
        expect(wrapper.vm.tasks).toBeInstanceOf(Array)
        expect(wrapper.vm.currentTaskState).toBe(-1)
    })
    it('Calls getTasks on mount', () => {
        expect(getTasks).toBeCalled()
    })
})
