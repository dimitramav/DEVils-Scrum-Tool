import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import SprintInfo from '@/components/projectPageOverview/SprintInfo'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('SprintInfo', () => {
    // Mount SprintInfo on wrapper
    const wrapper = shallowMount(SprintInfo, {
        localVue,
        router,
        propsData: {
            projectOverview: {
                project: {
                    isDone: false,
                },
                currentSprintNum: 0,
                todo: 1,
                doing: 2,
                done: 1,
                issues: 3,
                currentSprintExpDate: '10-11-2022',
            },
            teamRole: 'Developer',
        },
    })
    // Make various tests
    it('SprintInfo is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values given a prop projectOverview object', () => {
        expect(wrapper.vm.max).toBe(100)
        expect(wrapper.vm.donePercentage).toBe(25)
        expect(wrapper.vm.sdeadline).toBe('10-11-2022')
        //expect(wrapper.vm.diffDays).toBe(0)
        expect(wrapper.vm.issues).toBe(3)
    })
    it('Check which div is going to be used', () => {
        expect(wrapper.findAll('#currentProject').length).toBe(1)
        expect(wrapper.findAll('#currentSprint').length).toBe(0)
    })
})
