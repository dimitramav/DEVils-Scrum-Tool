import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import NewProjectForm from '@/components/index/NewProjectForm.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('NewProjectForm', () => {
    // Mount NewProjectForm on wrapper
    const wrapper = shallowMount(NewProjectForm, {
        localVue,
        router,
    })
    // Make various tests
    it('NewProjectForm is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        let projectName = wrapper.find('#newProjectName')
        expect(projectName.attributes().required).toBeTruthy()
        expect(wrapper.find('#inputLive').attributes().required).toBeTruthy()
        expect(wrapper.find('#inputLive').attributes().type).toBe('date')
    })
    it('Default values', () => {
        expect(wrapper.vm.newTitle).toBe('')
        expect(wrapper.vm.deadlineDate).toBe('')
    })
    it('Check dates valid input', () => {
        let targetDate = new Date()
        let dd = ('0' + targetDate.getDate()).slice(-2)
        let mm = ('0' + (targetDate.getMonth() + 2)).slice(-2) // future
        let mm2 = ('0' + (targetDate.getMonth() - 3)).slice(-2) // past
        let yyyy = targetDate.getFullYear()
        wrapper.vm.deadlineDate = yyyy + '-' + mm + '-' + dd
        expect(wrapper.vm.dateState).toBeTruthy()
        wrapper.vm.deadlineDate = yyyy + '-' + mm2 + '-' + dd
        expect(wrapper.vm.dateState).toBeFalsy()
    })
})
