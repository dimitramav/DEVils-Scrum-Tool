import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import Members from '@/components/projectPageOverview/Members'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('Members', () => {
    // Mount Members on wrapper
    const wrapper = shallowMount(Members, {
        localVue,
        router,
        propsData: {
            projectOverview: {
                project: {
                    isDone: false,
                },
            },
            teamRole: 'Product Owner',
        },
    })
    // Make various tests
    it('Members is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        //expect(wrapper.find({ name: 'LeaveProject' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'AddMembers' }).exists()).toBeTruthy()
    })
})
