import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import PasswordForm from '@/components/signup/PasswordForm.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

describe('PasswordForm', () => {
    // Mount PasswordForm on wrapper
    const wrapper = shallowMount(PasswordForm, {
        localVue,
    })
    // Make various tests
    it('PasswordForm is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        expect(wrapper.find('#password').attributes().required).toBeTruthy()
        expect(wrapper.find('#password').attributes().type).toBe('password')
        let verify = wrapper.find('#verify_password')
        expect(verify.attributes().required).toBeTruthy()
        expect(verify.attributes().type).toBe('password')
    })
    it('Default values', () => {
        expect(wrapper.vm.password).toBe('')
        expect(wrapper.vm.verify_password).toBe('')
        expect(wrapper.vm.validPassword).toBeNull()
    })
    it('Check Password equality', () => {
        wrapper.setData({ password: 'pass' })
        expect(wrapper.vm.checkPassword).toBeFalsy()
        wrapper.setData({ password: 'pa123*' })
        expect(wrapper.vm.checkPassword).toBeFalsy()
        wrapper.setData({ password: 'pass123*' })
        expect(wrapper.vm.checkPassword).toBeTruthy()
    })
    it('Verify Password', () => {
        wrapper.setData({ password: 'pass123*' })
        wrapper.setData({ verify_password: 'pass*' })
        expect(wrapper.vm.verifyPassword).toBeFalsy()
        wrapper.setData({ verify_password: 'pass123*' })
        expect(wrapper.vm.verifyPassword).toBeTruthy()
    })
})
