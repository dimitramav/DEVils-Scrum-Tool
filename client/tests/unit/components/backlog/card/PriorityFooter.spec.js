import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import PriorityFooter from '@/components/backlog/card/PriorityFooter.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

describe('PriorityFooter', () => {
    // Mount PriorityFooter on wrapper
    const wrapper = shallowMount(PriorityFooter, {
        localVue,
        propsData: {
            cur_pbi_priority: 2,
        },
    })
    // Make various tests
    it('PriorityFooter is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.cur_pbi_priority).toBe(2)
    })
})
