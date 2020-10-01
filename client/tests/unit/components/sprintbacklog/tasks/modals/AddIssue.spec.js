import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import AddIssue from '@/components/sprintbacklog/tasks/modals/AddIssue.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()

describe('AddIssue', () => {
    // Mount AddIssue on wrapper
    const wrapper = shallowMount(AddIssue, {
        localVue,
        router,
        propsData: {
            idTask: 17,
        },
    })
    // Make various tests
    it('AddIssue is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.newIssue).toBe('')
        expect(wrapper.vm.issueToAdd.description).toBe('')
        expect(wrapper.vm.issueToAdd.task.id).toBe(0)
    })
})
