import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import EditPBI from '@/components/backlog/modals/EditPBI.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('EditPBI', () => {
    // Mount EditPBI on wrapper
    const wrapper = shallowMount(EditPBI, {
        localVue,
        router,
    })
    // Make various tests
    it('EditPBI is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Edit image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/edit.png')
    })
    it('Default values', () => {
        expect(wrapper.vm.modalShow).toBeFalsy()
        expect(wrapper.vm.isEpic).toBeNull()
        expect(wrapper.vm.updateEpic_form.title).toBe('')
        expect(wrapper.vm.updateEpic_form.desc).toBe('')
        expect(wrapper.vm.updateEpic_form.selected).toBe(-1)
        expect(wrapper.vm.options).toBeInstanceOf(Array)
        expect(wrapper.vm.options[0].text).toBe('High')
        expect(wrapper.vm.options[0].value).toBe(1)
        expect(wrapper.vm.options[1].text).toBe('Medium')
        expect(wrapper.vm.options[1].value).toBe(2)
        expect(wrapper.vm.options[2].text).toBe('Low')
        expect(wrapper.vm.options[2].value).toBe(3)
    })
})
