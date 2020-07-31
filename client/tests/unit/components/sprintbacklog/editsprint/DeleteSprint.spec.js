import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import DeleteSprint from '@/components/sprintbacklog/editsprint/DeleteSprint.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('DeleteSprint', () => {
    // Mount DeleteSprint on wrapper
    const wrapper = shallowMount(DeleteSprint, {
        localVue,
        router,
    })
    // Make various tests
    it('DeleteSprint is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
})
