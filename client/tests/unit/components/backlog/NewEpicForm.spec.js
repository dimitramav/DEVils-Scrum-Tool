import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import NewEpicForm from '@/components/backlog/NewEpicForm'

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

describe('NewEpicForm', () => {
    // Mount NewEpicForm on wrapper
    const wrapper = shallowMount(NewEpicForm, {
        localVue,
        router,
        mocks: {
            $route,
        },
    })
    // Make various tests
    it('NewEpicForm is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.currentProject_id).toBe(wrapper.vm.$route.params.id)
        expect(wrapper.vm.newEpicAlert).toBeFalsy()
    })
})
