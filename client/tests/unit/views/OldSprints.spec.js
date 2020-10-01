import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import OldSprints from '@/views/OldSprints.vue'

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

describe('OldSprints', () => {
    // Mount OldSprints on wrapper
    const wrapper = shallowMount(OldSprints, {
        localVue,
        router,
        mocks: {
            $route,
        },
    })
    // Make various tests
    it('OldSprints is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'Navbar' }).exists()).toBeTruthy()
        //expect(wrapper.find({ name: 'BadgeInfo' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'NewSprintButton' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.sprints).toBeInstanceOf(Array)
    })
})
