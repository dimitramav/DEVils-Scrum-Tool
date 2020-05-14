import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import ChangePass from '@/components/profile/ChangePass.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('ChangePass', () => {
    // Mount ChangePass on wrapper
    const wrapper = shallowMount(ChangePass, {
        localVue,
        router,
    })
    // Make various tests
    it('ChangePass is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        expect(wrapper.find('#currentpass').attributes().required).toBeTruthy()
        expect(wrapper.find('#currentpass').attributes().type).toBe('password')
        expect(wrapper.find('#newpass').attributes().required).toBeTruthy()
        expect(wrapper.find('#newpass').attributes().type).toBe('password')
        expect(wrapper.find('#verifypass').attributes().required).toBeTruthy()
        expect(wrapper.find('#verifypass').attributes().type).toBe('password')
    })
    it('Default values', () => {
        expect(wrapper.vm.validEmail).toBeNull()
        expect(wrapper.vm.ok).toBeFalsy()
        expect(wrapper.vm.password).toBe('')
        expect(wrapper.vm.newpass).toBe('')
        expect(wrapper.vm.verifypass).toBe('')
        expect(wrapper.vm.validPassword).toBeNull()
        expect(wrapper.vm.validVerifyPass).toBeNull()
        expect(wrapper.vm.currentPassword).toBeNull()
    })
    it('Check Password equality', () => {
        wrapper.setData({ newpass: 'pass' })
        expect(wrapper.vm.checkPassword).toBeFalsy()
        wrapper.setData({ newpass: 'pa123*' })
        expect(wrapper.vm.checkPassword).toBeFalsy()
        wrapper.setData({ newpass: 'pass123*' })
        expect(wrapper.vm.checkPassword).toBeTruthy()
    })
    it('Verify Password', () => {
        wrapper.setData({ newpass: 'pass123*' })
        wrapper.setData({ verifypass: 'pass*' })
        expect(wrapper.vm.verifyPassword).toBeFalsy()
        wrapper.setData({ verifypass: 'pass123*' })
        expect(wrapper.vm.verifyPassword).toBeTruthy()
    })
})
