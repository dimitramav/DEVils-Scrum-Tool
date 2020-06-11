import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import RemoveStory from '@/components/sprintbacklog/stories/modals/RemoveStory'

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

describe('RemoveStory', () => {
    // Mount RemoveStory on wrapper
    const wrapper = shallowMount(RemoveStory, {
        localVue,
        router,
        mocks: {
            $route,
        },
        propsData: {
            story: {
                idPBI: 1,
            },
            modalId: 'remove_story',
        },
    })
    // Make various tests
    it('RemoveStory is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.currentProject_id).toBe(wrapper.vm.$route.params.id)
    })
})
