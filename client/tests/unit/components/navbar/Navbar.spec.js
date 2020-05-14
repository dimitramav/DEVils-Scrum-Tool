import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import Navbar from '@/components/navbar/Navbar.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('Navbar', () => {
    // Mount Navbar on wrapper
    const wrapper = shallowMount(Navbar, {
        localVue,
        router,
        propsData: {
            logout_prop: true,
            dashboard: true,
        },
    })
    // Make various tests
    it('Navbar is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Index Canban image', () => {
        let imgUrl = wrapper.find('#logo').attributes().src
        expect(imgUrl).toBe('@/assets/kanban.png')
    })
    it('Check logout_prop', () => {
        expect(wrapper.vm.logout_prop).toBeDefined()
        expect(wrapper.props().logout_prop).toBe(true)
        expect(wrapper.props().dashboard).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'SecondNavbar' }).exists()).toBeTruthy()
        //expect(wrapper.find({ name: 'Notifications' }).exists()).toBeTruthy()
    })
})
