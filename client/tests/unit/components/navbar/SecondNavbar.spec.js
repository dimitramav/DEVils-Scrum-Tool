import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import SecondNavbar from '@/components/navbar/SecondNavbar.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('SecondNavbar', () => {
    // Mount SecondNavbar on wrapper
    const wrapper = shallowMount(SecondNavbar, {
        localVue,
        router,
        propsData: {
            logout_prop: true,
        },
    })
    // Make various tests
    it('SecondNavbar is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Check logout_prop', () => {
        expect(wrapper.vm.logout_prop).toBeDefined()
        expect(wrapper.props().logout_prop).toBe(true)
    })
})
