import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '@/views/Home.vue'
import Index from '@/views/Index.vue'
import SignUp from '@/views/SignUp'
import SignIn from '@/views/SignIn'
import ForgotPass from '@/views/ForgotPass'
import PageNotFound from '@/views/PageNotFound'
import Unauthorized from '@/views/Unauthorized'

Vue.use(VueRouter)

const routes = [
    {
        path: '/home',
        name: 'Home',
        component: Home,
    },
    {
        path: '/',
        name: 'Index',
        component: Index,
    },
    {
        path: '/signup',
        name: 'SignUp',
        component: SignUp,
    },
    {
        path: '/signin',
        name: 'SignIn',
        component: SignIn,
    },
    {
        path: '/forgotpass',
        name: 'ForgotPass',
        component: ForgotPass,
    },
    {
        path: '*',
        name: 'PageNotFound',
        component: PageNotFound,
    },
    {
        path: '/unauthorized',
        name: 'Unauthorized',
        component: Unauthorized,
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
})

export default router
