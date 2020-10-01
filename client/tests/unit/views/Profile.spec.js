import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import Profile from '@/views/Profile.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('Profile', () => {
    // Mount Profile on wrapper
    const wrapper = shallowMount(Profile, {
        localVue,
        router,
    })
    // Make various tests
    it('Profile is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'Navbar' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.userInfos).toBeInstanceOf(Array)
        expect(wrapper.vm.loggedin_username).toBeNull()
    })
})
