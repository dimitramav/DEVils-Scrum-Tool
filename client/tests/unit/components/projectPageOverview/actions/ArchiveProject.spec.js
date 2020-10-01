import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import ArchiveProject from '@/components/projectPageOverview/actions/ArchiveProject'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('ArchiveProject', () => {
    // Mount ArchiveProject on wrapper
    const wrapper = shallowMount(ArchiveProject, {
        localVue,
        router,
    })
    // Make various tests
    it('ArchiveProject is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.isDoneToBecome).toBeTruthy()
    })
})
