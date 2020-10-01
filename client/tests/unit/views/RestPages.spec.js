import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import ForgotPass from '@/views/ForgotPass.vue'
import Unauthorized from '@/views/Unauthorized.vue'
import PageNotFound from '@/views/PageNotFound.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('ForgotPass', () => {
    // Mount ForgotPass on wrapper
    const wrapper = shallowMount(ForgotPass, {
        localVue,
        router,
    })
    // Make various tests
    it('ForgotPass is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Index Canban image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/kanban.png')
    })
    it('Form attributes', () => {
        expect(wrapper.find('#email').attributes().required).toBeTruthy()
        expect(wrapper.find('#email').attributes().type).toBe('email')
    })
    it('Default values', () => {
        expect(wrapper.vm.form.email).toBe('')
    })
})

describe('Unauthorized', () => {
    // Mount Unauthorized on wrapper
    const wrapper = shallowMount(Unauthorized, {
        localVue,
    })
    // Make various tests
    it('Unauthorized is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'Navbar' }).exists()).toBeTruthy()
    })
})

describe('PageNotFound', () => {
    // Mount PageNotFound on wrapper
    const wrapper = shallowMount(PageNotFound, {
        localVue,
    })
    // Make various tests
    it('PageNotFound is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'Navbar' }).exists()).toBeTruthy()
    })
})
