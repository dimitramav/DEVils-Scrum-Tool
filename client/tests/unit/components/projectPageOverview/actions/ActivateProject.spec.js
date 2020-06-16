import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import ActivateProject from '@/components/projectPageOverview/actions/ActivateProject'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('ActivateProject', () => {
    // Mount ActivateProject on wrapper
    const wrapper = shallowMount(ActivateProject, {
        localVue,
        router,
    })
    // Make various tests
    it('ActivateProject is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.isDoneToBecome).toBeFalsy()
    })
})
