import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import Index from '@/views/Index.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

describe('Index', () => {
    // Mount Index on wrapper
    const wrapper = shallowMount(Index, {
        localVue,
    })
    // Make various tests
    it('Index is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.totalNumOfCurProjects).toBe(0)
    })
})
