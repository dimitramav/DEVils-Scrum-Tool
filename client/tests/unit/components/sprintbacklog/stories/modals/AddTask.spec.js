import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import AddTask from '@/components/sprintbacklog/stories/modals/AddTask'

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

describe('AddTask', () => {
    // Mount AddTask on wrapper
    const wrapper = shallowMount(AddTask, {
        localVue,
        router,
        mocks: {
            $route,
        },
        propsData: {
            story: {
                idPBI: 1,
            },
            modalId: 'new_task',
        },
    })
    // Make various tests
    it('AddTask is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        let formId = '#addTask' + wrapper.vm.storyId
        expect(wrapper.find(formId).attributes().required).toBeTruthy()
        expect(wrapper.find(formId).attributes().type).toBe('text')
    })
    it('Default values', () => {
        expect(wrapper.vm.newTask_desc).toBe('')
        expect(wrapper.vm.currentProject_id).toBe(wrapper.vm.$route.params.id)
    })
})
