import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import LeaveProject from '@/components/projectPageOverview/actions/LeaveProject.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('LeaveProject', () => {
    // Mount LeaveProject on wrapper
    const wrapper = shallowMount(LeaveProject, {
        localVue,
        router,
    })
    // Make various tests
    it('LeaveProject is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
})
