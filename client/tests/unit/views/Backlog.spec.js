import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import Backlog from '@/views/Backlog.vue'

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

describe('Backlog', () => {
    // Mount Backlog on wrapper
    const wrapper = shallowMount(Backlog, {
        localVue,
        router,
        mocks: {
            $route,
        },
    })
    // Make various tests
    it('Backlog is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'Navbar' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'NewEpicForm' }).exists()).toBeTruthy()
        //expect(wrapper.find({ name: 'EpicCard' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.currentPbis).toBeInstanceOf(Array)
        expect(wrapper.vm.currentUserStories).toBeInstanceOf(Array)
        expect(wrapper.vm.currentProject_id).toBe(wrapper.vm.$route.params.id)
    })
})
