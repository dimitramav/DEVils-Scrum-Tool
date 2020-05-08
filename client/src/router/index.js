import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '@/views/Index.vue'
import SignUp from '@/views/SignUp'
import SignIn from '@/views/SignIn'
import ForgotPass from '@/views/ForgotPass'
import Unauthorized from '@/views/Unauthorized'
import PageNotFound from '@/views/PageNotFound'

Vue.use(VueRouter)

const routes = [
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
        path: '/unauthorized',
        name: 'Unauthorized',
        component: Unauthorized,
    },
    {
        path: '*',
        name: 'PageNotFound',
        component: PageNotFound,
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
})

export default router
