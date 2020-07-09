import Vue from 'vue'
import App from './App.vue'
import router from './router'

import { BootstrapVue } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)

Vue.config.productionTip = false

// Server's url for REST endpoints
Vue.prototype.$url = 'http://localhost:8765/api'
// Project Page Overview (must be the same with backend's DEFAULT_COUNT in java/scrumtool/data/Limits.java)
Vue.prototype.$recordsPerPage = 4

new Vue({
    router,
    render: (h) => h(App),
}).$mount('#app')
