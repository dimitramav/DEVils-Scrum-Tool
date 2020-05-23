import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import SubmitButton from '@/components/newsprint/SubmitButton'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('SubmitButton', () => {
    // Mount SubmitButton on wrapper
    const wrapper = shallowMount(SubmitButton, {
        localVue,
        router,
    })
    // Make various tests
    it('SubmitButton is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Button attributes', () => {
        expect(wrapper.find('#submitBtn').attributes().type).toBe('submit')
        expect(wrapper.find('#submitBtn').attributes().size).toBe('lg')
        expect(wrapper.find('#submitBtn').attributes().variant).toBe('success')
    })
    it('Default values', () => {
        expect(wrapper.vm.pbis_list).toBeInstanceOf(Array)
    })
})
