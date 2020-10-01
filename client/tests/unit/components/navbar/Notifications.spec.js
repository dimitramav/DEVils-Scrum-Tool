import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
import VueRouter from 'vue-router'
import Notifications from '@/components/navbar/Notifications.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
const router = new VueRouter()
localVue.use(VueRouter)

describe('Notifications', () => {
    // Mount Notifications on wrapper
    const wrapper = shallowMount(Notifications, {
        localVue,
        router,
    })
    // Make various tests
    it('Navbar is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('Notification image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/notification-Off.png')
    })
    it('Notification data properties', () => {
        expect(wrapper.vm.notificationStruct.id).toBeNull()
        expect(wrapper.vm.notificationStruct.project.id).toBe(0)
        expect(wrapper.vm.notificationStruct.projectTitle).toBeNull()
        expect(wrapper.vm.notificationStruct.role).toBeNull()
        expect(wrapper.vm.notificationStruct.sender.username).toBeNull()
        expect(wrapper.vm.notificationStruct.receiver.email).toBe('')
        expect(wrapper.vm.notificationStruct.type).toBe('')
        expect(wrapper.vm.Notifications).toBeInstanceOf(Array)
    })
})
