import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import StoryCard from '@/components/backlog/card/StoryCard.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
const $route = {
    params: {
        id: 2,
    },
}
// bug in vue-test-utils when mount mock object
if (!process || process.env.NODE_ENV !== 'test') {
    localVue.use(VueRouter)
}

describe('StoryCard', () => {
    // Mount StoryCard on wrapper
    const wrapper = shallowMount(StoryCard, {
        localVue,
        router,
        mocks: {
            $route,
        },
        propsData: {
            cur_us: {
                idPBI: 1,
            },
        },
    })
    // Make various tests
    it('StoryCard is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    /*it('Components included', () => {
        //expect(wrapper.find({ name: 'EditPBI' }).exists()).toBeTruthy()
        expect(wrapper.find({ name: 'PriorityFooter' }).exists()).toBeTruthy()
    })*/
    it('Default values', () => {
        expect(wrapper.vm.cur_us).toBeInstanceOf(Object)
        //expect(wrapper.vm.currentUserStories).toBeInstanceOf(Array)
        //expect(wrapper.vm.current_epic_idPBI).toBeInstanceOf(Number)
        expect(wrapper.vm.currentProject_id).toBe(wrapper.vm.$route.params.id)
    })
})
