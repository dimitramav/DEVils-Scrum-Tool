import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import NameForm from '@/components/signup/NameForm.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

describe('NameForm', () => {
    // Mount NameForm on wrapper
    const wrapper = shallowMount(NameForm, {
        localVue,
    })
    // Make various tests
    it('NameForm is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        expect(wrapper.find('#firstname').attributes().required).toBeTruthy()
        expect(wrapper.find('#firstname').attributes().type).toBe('text')
        expect(wrapper.find('#lastname').attributes().required).toBeTruthy()
        expect(wrapper.find('#lastname').attributes().type).toBe('text')
    })
    it('Default values', () => {
        expect(wrapper.vm.firstname).toBe('')
        expect(wrapper.vm.lastname).toBe('')
    })
})
