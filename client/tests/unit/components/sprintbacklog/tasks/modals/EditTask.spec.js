import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import EditTask from '@/components/sprintbacklog/tasks/modals/EditTask'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()

describe('EditTask', () => {
    // Mount EditTask on wrapper
    const wrapper = shallowMount(EditTask, {
        localVue,
        router,
        propsData: {
            desc: 'blabla',
            PBI_id: 11,
            state: 3,
            Task_id: 17,
        },
    })
    // Make various tests
    it('EditTask is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('EditTask image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/edit.png')
    })
    it('Form attributes', () => {
        let formId = '#editTask' + wrapper.vm.Task_id
        expect(wrapper.find(formId).attributes().required).toBeTruthy()
        expect(wrapper.find(formId).attributes().type).toBe('text')
    })
    it('Default values', () => {
        expect(wrapper.vm.updateTask_desc).toBe('')
        expect(wrapper.vm.modalShow).toBeFalsy()
    })
})
