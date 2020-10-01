import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import DeleteAccount from '@/components/profile/DeleteAccount.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('DeleteAccount', () => {
    // Mount DeleteAccount on wrapper
    const wrapper = shallowMount(DeleteAccount, {
        localVue,
        router,
    })
    // Make various tests
    it('DeleteAccount is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
})
