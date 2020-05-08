import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import UsernameForm from '@/components/signup/UsernameForm.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('UsernameForm', () => {
    // Mount UsernameForm on wrapper
    const wrapper = shallowMount(UsernameForm, {
        localVue,
        router,
    })
    // Make various tests
    it('UsernameForm is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        expect(wrapper.find('#username').attributes().required).toBeTruthy()
        expect(wrapper.find('#username').attributes().type).toBe('text')
    })
    it('Default values', () => {
        expect(wrapper.vm.username).toBe('')
        expect(wrapper.vm.validUsername).toBeNull()
    })
})
