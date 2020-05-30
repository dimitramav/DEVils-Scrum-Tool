import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import CardHeader from '@/components/backlog/card/CardHeader.vue'

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

describe('CardHeader', () => {
    // Mount CardHeader on wrapper
    const wrapper = shallowMount(CardHeader, {
        localVue,
        router,
        mocks: {
            $route,
        },
        propsData: {
            current_epic: {
                idPBI: 1,
                title: 'sometitle',
                description: 'desc',
                priority: 2,
            },
        },
    })
    // Make various tests
    it('CardHeader is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        //expect(wrapper.find({ name: 'EditPBI' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'NewPBI' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.currentProject_id).toBe(wrapper.vm.$route.params.id)
    })
})
