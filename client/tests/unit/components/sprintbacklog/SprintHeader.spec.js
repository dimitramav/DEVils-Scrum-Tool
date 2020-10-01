import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import SprintHeader from '@/components/sprintbacklog/SprintHeader.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
const $route = {
    params: {
        id: 2,
        sprintId: 15,
    },
}
// bug in vue-test-utils when mount mock object
if (!process || process.env.NODE_ENV !== 'test') {
    localVue.use(VueRouter)
}

describe('SprintHeader', () => {
    // Mount SprintHeader on wrapper
    const getSprintInfos = jest.fn()
    const wrapper = shallowMount(SprintHeader, {
        localVue,
        router,
        mocks: {
            $route,
        },
        methods: {
            getSprintInfos,
        },
    })
    // Make various tests
    it('SprintHeader is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'EditSprintForm' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.routeSprintId).toBe(wrapper.vm.$route.params.sprintId)
    })
    it('Calls getSprintInfos on mount', () => {
        expect(getSprintInfos).toBeCalled()
    })
})
