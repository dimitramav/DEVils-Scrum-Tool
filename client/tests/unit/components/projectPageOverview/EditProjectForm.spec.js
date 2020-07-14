import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import EditProjectForm from '@/components/projectPageOverview/EditProjectForm'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('EditProjectForm', () => {
    // Mount EditProjectForm on wrapper
    const wrapper = shallowMount(EditProjectForm, {
        localVue,
        router,
    })
    // Make various tests
    it('EditProjectForm is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Edit image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/edit.png')
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'ArchiveProject' }).exists()).toBeTruthy()
    })
    it('Form attributes', () => {
        expect(wrapper.find('#newTitle').attributes().required).toBeTruthy()
        expect(wrapper.find('#newTitle').attributes().type).toBe('text')
        expect(wrapper.find('#dateInput').attributes().required).toBeTruthy()
        expect(wrapper.find('#dateInput').attributes().type).toBe('date')
    })
    it('Default values', () => {
        expect(wrapper.vm.form.id).toBe(0)
        expect(wrapper.vm.form.newTitle).toBe('')
        expect(wrapper.vm.form.deadlineDate).toBe('')
        expect(wrapper.vm.form.isDone).toBeFalsy()
    })
    it('Check dates valid input', () => {
        let targetDate = new Date()
        let dd = ('0' + targetDate.getDate()).slice(-2)
        let mm = ('0' + (targetDate.getMonth() + 2)).slice(-2) // future
        let mm2 = ('0' + (targetDate.getMonth() - 3)).slice(-2) // past
        let yyyy = targetDate.getFullYear()
        wrapper.vm.form.deadlineDate = yyyy + '-' + mm + '-' + dd
        expect(wrapper.vm.dateState).toBeTruthy()
        wrapper.vm.form.deadlineDate = yyyy + '-' + mm2 + '-' + dd
        expect(wrapper.vm.dateState).toBeFalsy()
    })
})
