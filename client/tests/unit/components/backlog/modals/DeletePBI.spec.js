import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import DeletePBI from '@/components/backlog/modals/DeletePBI.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('DeletePBI', () => {
    // Mount DeletePBI on wrapper
    const wrapper = shallowMount(DeletePBI, {
        localVue,
        router,
    })
    // Make various tests
    it('DeletePBI is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
})
