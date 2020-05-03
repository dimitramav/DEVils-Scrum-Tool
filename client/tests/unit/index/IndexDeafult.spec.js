import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
//import VueRouter from 'vue-router'
import IndexDefault from '@/views/IndexDefault.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
//const router = new VueRouter()
/*if (!process || process.env.NODE_ENV !== 'test') {
    localVue.use(VueRouter)
}*/

describe('IndexDefault', () => {
    // Mount IndexDefault on wrapper
    const wrapper = shallowMount(IndexDefault, {
        localVue,
    })
    // Make various tests
    it('IndexDefault component exists', () => {
        expect(wrapper.exists()).toBe(true)
    })
    it('Json objects loaded correctly', () => {
        expect(wrapper.vm.featuresData).toHaveProperty('item1')
        expect(wrapper.vm.teamData).toHaveProperty('member1.img')
        expect(wrapper.vm.teamData).toHaveProperty('member1.name')
        expect(wrapper.vm.teamData).toHaveProperty('member1.dev')
        expect(wrapper.vm.teamData).toHaveProperty('member1.phrase')
    })
    it('Join button exists', () => {
        wrapper.find('#join').trigger('click')
    })
})
