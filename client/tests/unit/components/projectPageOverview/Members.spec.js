import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import Members from '@/components/projectPageOverview/Members'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('Members', () => {
    // Mount Members on wrapper
    const wrapper = shallowMount(Members, {
        localVue,
        router,
        propsData: {
            projectOverview: {
                project: {
                    isDone: false,
                },
            },
        },
    })
    // Make various tests
    it('Members is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Default values', () => {
        expect(wrapper.vm.showAlert).toBeFalsy()
        expect(wrapper.vm.validEmail).toBeNull()
        expect(wrapper.vm.newMember.email).toBe('')
        expect(wrapper.vm.newMember.role).toBeNull()
        expect(wrapper.vm.invitation.id).toBeNull()
        expect(wrapper.vm.invitation.projectId).toBe(0)
        expect(wrapper.vm.invitation.projectTitle).toBeNull()
        expect(wrapper.vm.invitation.role).toBeNull()
        expect(wrapper.vm.invitation.fromUsername).toBeNull()
        expect(wrapper.vm.invitation.toUserEmail).toBe('')
        expect(wrapper.vm.invitation.type).toBe('')
    })
})
