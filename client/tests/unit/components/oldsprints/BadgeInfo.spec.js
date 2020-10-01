import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import BadgeInfo from '@/components/oldsprints/BadgeInfo.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
const $route = {
    params: {
        id: 2,
    },
}
// bug in vue-test-utils when mount mock object
if (!process || process.env.NODE_ENV !== 'test') {
    localVue.use(VueRouter)
}

describe('BadgeInfo', () => {
    // Mount BadgeInfo on wrapper
    const wrapper = shallowMount(BadgeInfo, {
        localVue,
        router,
        mocks: {
            $route,
        },
    })
    // Make various tests
    it('BadgeInfo is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.tasks).toBe(0)
        expect(wrapper.vm.issues).toBe(0)
    })
})
