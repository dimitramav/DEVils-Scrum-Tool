import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import EditIssue from '@/components/sprintbacklog/tasks/modals/EditIssue.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()

describe('EditIssue', () => {
    // Mount EditIssue on wrapper
    const wrapper = shallowMount(EditIssue, {
        localVue,
        router,
        propsData: {
            issue: {
                id: 25,
                description: 'bla',
            },
            idTask: 17,
        },
    })
    // Make various tests
    it('EditIssue is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        let formId = '#editIssue' + wrapper.vm.issue.id
        expect(wrapper.find(formId).attributes().required).toBeTruthy()
        expect(wrapper.find(formId).attributes().type).toBe('text')
    })
    it('Default values', () => {
        expect(wrapper.vm.issue_desc).toBe(wrapper.vm.issue.description)
    })
})
