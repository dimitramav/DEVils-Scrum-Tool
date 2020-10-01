import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import SignIn from '@/views/SignIn.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('SignIn', () => {
    // Mount SignIn on wrapper
    const wrapper = shallowMount(SignIn, {
        localVue,
        router,
    })
    // Make various tests
    it('SignIn is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Index Canban image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/kanban.png')
    })
    it('Form attributes', () => {
        expect(wrapper.find('#email').attributes().required).toBeTruthy()
        expect(wrapper.find('#email').attributes().type).toBe('email')
        expect(wrapper.find('#password').attributes().required).toBeTruthy()
        expect(wrapper.find('#password').attributes().type).toBe('password')
    })
    it('Default values', () => {
        expect(wrapper.vm.form.email).toBe('')
        expect(wrapper.vm.form.password).toBe('')
        expect(wrapper.vm.wrongLogin).toBe(false)
    })
})
