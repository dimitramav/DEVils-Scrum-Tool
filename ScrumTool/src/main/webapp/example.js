const Foo = { template: '<div>foo</div>' };
const Bar = { template: 'v-html=login.html' };

const routes = [
    { path: '/foo', component: Foo },
    { path: '/bar', component: Bar }
];


const router = new VueRouter({
    routes // short for `routes: routes`
});

const app = new Vue({
    router
}).$mount('#app');