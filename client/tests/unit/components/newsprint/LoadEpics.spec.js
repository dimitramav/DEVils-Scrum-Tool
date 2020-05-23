import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import LoadEpics from '@/components/newsprint/LoadEpics'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('LoadEpics', () => {
    // Mount LoadEpics on wrapper
    const wrapper = shallowMount(LoadEpics, {
        localVue,
        router,
    })
    // Make various tests
    it('LoadEpics is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.selectedValue).toBe('')
        expect(wrapper.vm.stories_options).toBeInstanceOf(Array)
        expect(wrapper.vm.epics).toBeInstanceOf(Array)
    })
    it('For selectedValue empty, stories_options must be empty', () => {
        expect(wrapper.vm.stories_options).toHaveLength(0)
    })
})
