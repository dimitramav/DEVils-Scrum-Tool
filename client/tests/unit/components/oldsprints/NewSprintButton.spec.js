import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import NewSprintButton from '@/components/oldsprints/NewSprintButton.vue'

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

describe('NewSprintButton', () => {
    // Mount NewSprintButton on wrapper
    const wrapper = shallowMount(NewSprintButton, {
        localVue,
        router,
        mocks: {
            $route,
        },
    })
    // Make various tests
    it('NewSprintButton is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.projectOverview.project.id).toBe(0)
        expect(wrapper.vm.projectOverview.project.isCurrent).toBeFalsy()
    })
})
