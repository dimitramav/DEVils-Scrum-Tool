import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import EditSprintForm from '@/components/sprintbacklog/EditSprintForm'

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

describe('EditSprintForm', () => {
    // Mount EditSprintForm on wrapper
    const getProjectInfo = jest.fn()
    const wrapper = shallowMount(EditSprintForm, {
        localVue,
        router,
        mocks: {
            $route,
        },
        propsData: {
            currentSprint: {
                goal: 'someGoal',
                plan: 'somePlan',
            },
        },
        methods: {
            getProjectInfo,
        },
    })
    // Make various tests
    it('EditSprintForm is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Form attributes', () => {
        expect(wrapper.find('#newGoal').attributes().required).toBeTruthy()
        expect(wrapper.find('#newGoal').attributes().type).toBe('text')
        expect(wrapper.find('#newPlan').attributes().required).toBeTruthy()
        expect(wrapper.find('#newPlan').attributes().type).toBe('text')
        expect(wrapper.find('#dateInput').attributes().required).toBeTruthy()
        expect(wrapper.find('#dateInput').attributes().type).toBe('date')
    })
    it('Default values', () => {
        expect(wrapper.vm.deadlineDate).toBe('')
    })
    it('Calls getProjectInfo on mount', () => {
        expect(getProjectInfo).toBeCalled()
    })
    it('Check dates valid input', () => {
        let targetDate = new Date()
        let dd = ('0' + targetDate.getDate()).slice(-2)
        let mm = ('0' + (targetDate.getMonth() + 2)).slice(-2) // future
        let mm2 = ('0' + (targetDate.getMonth() - 3)).slice(-2) // past
        let yyyy = targetDate.getFullYear()
        // Sprint's deadline assigned as a past date
        wrapper.vm.deadlineDate = yyyy + '-' + mm2 + '-' + dd
        expect(wrapper.vm.dateState).toBeFalsy()
        // Assume that project's deadline is defined by mm3 (past date)
        let mm3 = ('0' + (targetDate.getMonth() - 2)).slice(-2) // finished
        let projectDeadline = yyyy + '-' + mm3 + '-' + dd
        wrapper.vm.projectOverview.project.deadlineDate = projectDeadline
        wrapper.vm.deadlineDate = yyyy + '-' + mm + '-' + dd
        expect(wrapper.vm.dateState).toBeFalsy()
        // Assume that project's deadline is defined by mm4 (future date)
        let mm4 = ('0' + (targetDate.getMonth() + 3)).slice(-2) // future+
        projectDeadline = yyyy + '-' + mm4 + '-' + dd
        wrapper.vm.projectOverview.project.deadlineDate = projectDeadline
        wrapper.vm.deadlineDate = yyyy + '-' + mm + '-' + dd
        expect(wrapper.vm.dateState).toBeTruthy()
    })
})
