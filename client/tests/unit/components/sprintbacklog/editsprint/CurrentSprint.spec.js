import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import CurrentSprint from '@/components/sprintbacklog/editsprint/CurrentSprint.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('CurrentSprint', () => {
    // Mount CurrentSprint on wrapper
    const wrapper = shallowMount(CurrentSprint, {
        localVue,
        router,
        propsData: {
            currentSprint: {
                id: 1,
                goal: 'someGoal',
                plan: 'somePlan',
                isCurrent: false,
            },
        },
    })
    // Make various tests
    it('CurrentSprint is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
})
