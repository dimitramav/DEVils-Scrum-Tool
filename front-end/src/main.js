import Vue from 'vue'
import App from './App.vue'
import router from './router'

import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)

Vue.config.productionTip = false

Vue.prototype.$url = 'http://localhost:8765/api/'


new Vue({
    router,
    render: h => h(App)
}).$mount('#app')


/*new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})*/
