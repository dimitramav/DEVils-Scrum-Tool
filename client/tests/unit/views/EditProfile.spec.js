import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import EditProfile from '@/views/EditProfile.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('EditProfile', () => {
    // Mount EditProfile on wrapper
    const wrapper = shallowMount(EditProfile, {
        localVue,
        router,
    })
    // Make various tests
    it('EditProfile is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'Navbar' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'ChangePass' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'DeleteAccount' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.userInfos).toBeInstanceOf(Array)
    })
    it('Form attributes', () => {
        expect(wrapper.find('#firstname').attributes().type).toBe('text')
        expect(wrapper.find('#lastname').attributes().type).toBe('text')
        expect(wrapper.find('#email').attributes().type).toBe('email')
        expect(wrapper.find('#job').attributes().type).toBe('text')
        expect(wrapper.find('#company').attributes().type).toBe('text')
        expect(wrapper.find('#country').attributes().type).toBe('text')
        expect(wrapper.find('#description').attributes().type).toBe('text')
    })
})
