import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import EmailForm from '@/components/signup/EmailForm.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('EmailForm', () => {
    // Mount EmailForm on wrapper
    const wrapper = shallowMount(EmailForm, {
        localVue,
        router,
    })
    // Make various tests
    it('EmailForm is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        expect(wrapper.find('#emailInput').attributes().required).toBeTruthy()
        expect(wrapper.find('#emailInput').attributes().type).toBe('email')
    })
    it('Default values', () => {
        expect(wrapper.vm.email).toBe('')
        expect(wrapper.vm.validEmail).toBeNull()
    })
})
