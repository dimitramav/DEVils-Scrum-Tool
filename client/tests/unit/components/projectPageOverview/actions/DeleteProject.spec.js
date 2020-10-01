import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import DeleteProject from '@/components/projectPageOverview/actions/DeleteProject.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('DeleteProject', () => {
    // Mount DeleteProject on wrapper
    const wrapper = shallowMount(DeleteProject, {
        localVue,
        router,
    })
    // Make various tests
    it('DeleteProject is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
})
