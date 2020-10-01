import { createLocalVue, shallowMount } from '@vue/test-utils'
import { BootstrapVue } from 'bootstrap-vue'
//import VueRouter from 'vue-router'
import IndexDefault from '@/components/index/IndexDefault.vue'

// create an extended `Vue` constructor
const localVue = createLocalVue()
// install plugins as normal
localVue.use(BootstrapVue)

// Assign router plugin for mocking
//const router = new VueRouter()
/*if (!process || process.env.NODE_ENV !== 'test') {
    localVue.use(VueRouter)
}*/

describe('IndexDefault', () => {
    // Mount IndexDefault on wrapper
    const wrapper = shallowMount(IndexDefault, {
        localVue,
    })
    // Make various tests
    it('IndexDefault is Vue Instance', () => {
        expect(wrapper.isVueInstance).toBeTruthy()
    })
    it('All b-containers in place', () => {
        expect(wrapper.contains('#home')).toBeTruthy()
        expect(wrapper.contains('#features')).toBeTruthy()
        expect(wrapper.contains('#about')).toBeTruthy()
        expect(wrapper.contains('#footer')).toBeTruthy()
    })
    it('All css classes in place', () => {
        expect(wrapper.contains('.Home')).toBeTruthy()
        expect(wrapper.contains('.wordcloud')).toBeTruthy()
        expect(wrapper.contains('.About')).toBeTruthy()
        expect(wrapper.contains('.Features')).toBeTruthy()
        expect(wrapper.contains('.bottom')).toBeTruthy()
        expect(wrapper.contains('.description')).toBeTruthy()
    })
    it('Json objects loaded correctly', () => {
        expect(wrapper.vm.featuresData).toHaveProperty('item1')
        expect(wrapper.vm.teamData).toHaveProperty('member1.img')
        expect(wrapper.vm.teamData).toHaveProperty('member1.name')
        expect(wrapper.vm.teamData).toHaveProperty('member1.phrase')
    })
    it('Join button exists', () => {
        //wrapper.find('#join').trigger('click')
        expect(wrapper.find('#join').attributes().variant).toBe('primary')
    })
    it('ScrumWordCloud image', () => {
        let imgUrl = wrapper.find('img').attributes().src
        expect(imgUrl).toBe('@/assets/ScrumWordCloud.jpg')
        expect(wrapper.find('img').attributes().class).toBe('wordcloud')
    })
    it('Scrumtool video from Vaggos', () => {
        let iframeUrl = wrapper.find('iframe').attributes().src
        expect(iframeUrl).toBe('https://www.youtube.com/embed/9leSPphzYeI')
    })
    it('Repository source code link', () => {
        let repoLink = wrapper.vm.repoLink
        expect(repoLink).toBe('https://github.com/dimitramav/DEVils-Scrum-Tool')
    })
})
