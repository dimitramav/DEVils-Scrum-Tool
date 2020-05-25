import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import SelectStories from '@/components/newsprint/SelectStories.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

describe('SelectStories', () => {
    // Mount SelectStories on wrapper
    const wrapper = shallowMount(SelectStories, {
        localVue,
        propsData: {
            stories_options: [{ value: 1, text: 'title!' }],
        },
    })
    // Make various tests
    it('SelectStories is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.selected).toBeNull()
        expect(wrapper.vm.toremove).toBeNull()
        expect(wrapper.vm.selected_stories).toBeInstanceOf(Array)
    })
    it('Select a story for new sprint and then remove it', () => {
        // Select only 1 story to add with idPBI value == 1
        wrapper.vm.selectStory(wrapper.vm.stories_options[0].value)
        expect(wrapper.vm.selected_stories[0].value).toBe(1)
        // Select the same story, it should not be added again
        wrapper.vm.selectStory(1)
        expect(wrapper.vm.selected_stories).toHaveLength(1)
        // Remove this story from selected items, now there is none selected
        wrapper.vm.removeStory(1)
        expect(wrapper.vm.selected_stories).toHaveLength(0)
    })
})
