import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import SignUp from '@/views/SignUp.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('SignUp', () => {
    // Mount SignUp on wrapper
    const wrapper = shallowMount(SignUp, {
        localVue,
        router,
    })
    // Make various tests
    it('SignUp is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Index Canban image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/kanban.png')
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'EmailForm' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'UsernameForm' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'NameForm' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'PasswordForm' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.form.email).toBe('')
        expect(wrapper.vm.form.username).toBe('')
        expect(wrapper.vm.form.firstname).toBe('')
        expect(wrapper.vm.form.lastname).toBe('')
        expect(wrapper.vm.form.password).toBe('')
        expect(wrapper.vm.validEmail).toBeNull()
        expect(wrapper.vm.validUsername).toBeNull()
        expect(wrapper.vm.validPassword).toBeFalsy()
    })
    it('Form Methods calls', () => {
        wrapper.vm.editEmail('name@example.com', true)
        expect(wrapper.vm.form.email).toBe('name@example.com')
        expect(wrapper.vm.validEmail).toBeTruthy()
        wrapper.vm.editUsername('name@example.com', false)
        expect(wrapper.vm.form.username).toBe('name@example.com')
        expect(wrapper.vm.validUsername).toBeFalsy()
        wrapper.vm.editPassword('namepass123$', true)
        expect(wrapper.vm.form.password).toBe('namepass123$')
        expect(wrapper.vm.validPassword).toBeTruthy()
        wrapper.vm.editFirstname('first')
        expect(wrapper.vm.form.firstname).toBe('first')
        wrapper.vm.editLastname('last')
        expect(wrapper.vm.form.lastname).toBe('last')
    })
})
