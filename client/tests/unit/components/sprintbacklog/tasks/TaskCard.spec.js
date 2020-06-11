import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import TaskCard from '@/components/sprintbacklog/tasks/TaskCard.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()

describe('TaskCard', () => {
    // Mount TaskCard on wrapper
    const wrapper = shallowMount(TaskCard, {
        localVue,
        router,
        propsData: {
            cur_task: {
                description: 'blabla',
                PBI_id: 7,
                state: 3,
                idTask: 11,
            },
            tasks: [],
            typeofTasks: 3,
        },
    })
    // Make various tests
    it('TaskCard is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Components included', () => {
        expect(wrapper.find({ name: 'EditTask' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'Issue' }).exists()).toBeTruthy()
    })
})
