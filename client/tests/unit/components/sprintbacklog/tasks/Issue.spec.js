import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import Issue from '@/components/sprintbacklog/tasks/Issue.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()

describe('Issue', () => {
    // Mount Issue on wrapper
    const getIssues = jest.fn()
    const wrapper = shallowMount(Issue, {
        localVue,
        router,
        propsData: {
            idTask: 11,
        },
        methods: {
            getIssues,
        },
    })
    // Make various tests
    it('Issue is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Issue image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/issue.png')
    })
    it('Components included', () => {
        //expect(wrapper.find({ name: 'EditIssue' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'AddIssue' }).exists()).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.Issues).toBeInstanceOf(Array)
        expect(wrapper.vm.modalShow).toBeFalsy()
    })
    it('Calls getIssues on mount', () => {
        expect(getIssues).toBeCalled()
    })
})
