import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import AddMembers from '@/components/projectPageOverview/actions/AddMembers'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('AddMembers', () => {
    // Mount AddMembers on wrapper
    const wrapper = shallowMount(AddMembers, {
        localVue,
        router,
        propsData: {
            projectTitle: 'someTitle',
        },
    })
    // Make various tests
    it('AddMembers is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.showAlert).toBeFalsy()
        expect(wrapper.vm.validEmail).toBeNull()
        expect(wrapper.vm.newMember.email).toBe('')
        expect(wrapper.vm.newMember.role).toBeNull()
        expect(wrapper.vm.invitation.id).toBeNull()
        expect(wrapper.vm.invitation.project.id).toBe(0)
        expect(wrapper.vm.invitation.projectTitle).toBeNull()
        expect(wrapper.vm.invitation.role).toBeNull()
        expect(wrapper.vm.invitation.sender.username).toBeNull()
        expect(wrapper.vm.invitation.receiver.email).toBe('')
        expect(wrapper.vm.invitation.type).toBe('')
    })
})
