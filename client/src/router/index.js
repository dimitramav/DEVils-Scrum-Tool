import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '@/views/Index.vue'
import SignUp from '@/views/SignUp'
import SignIn from '@/views/SignIn'
import ForgotPass from '@/views/ForgotPass'
import Profile from '@/views/Profile'
import EditProfile from '@/views/EditProfile'
import ProjectPageOverview from '@/views/ProjectPageOverview'
import NewSprint from '@/views/NewSprint'
import OldSprints from '@/views/OldSprints'
import Backlog from '@/views/Backlog'
import SprintBacklog from '@/views/SprintBacklog'
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
        path: '/users/:username',
        name: 'Profile',
        component: Profile,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('auth_token') === 'null') {
                next({
                    path: '/unauthorized',
                })
            } else {
                next()
            }
        },
    },
    {
        path: '/editprofile',
        name: 'EditProfile',
        component: EditProfile,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('auth_token') === 'null') {
                next({
                    path: '/unauthorized',
                })
            } else {
                next()
            }
        },
    },
    {
        path: '/project/:id/overview',
        name: 'ProjectPageOverview',
        component: ProjectPageOverview,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('auth_token') === 'null') {
                next({
                    path: '/unauthorized',
                })
            } else {
                next()
            }
        },
    },
    {
        path: '/project/:id/newsprint',
        name: 'NewSprint',
        component: NewSprint,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('auth_token') === 'null') {
                next({
                    path: '/unauthorized',
                })
            } else {
                next()
            }
        },
    },
    {
        path: '/project/:id/sprints',
        name: 'OldSprints',
        component: OldSprints,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('auth_token') === 'null') {
                next({
                    path: '/unauthorized',
                })
            } else {
                next()
            }
        },
    },
    {
        path: '/project/:id/backlog',
        name: 'Backlog',
        component: Backlog,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('auth_token') === 'null') {
                next({
                    path: '/unauthorized',
                })
            } else {
                next()
            }
        },
    },
    {
        path: '/project/:id/sprintbacklog/:sprintId',
        name: 'SprintBacklog',
        component: SprintBacklog,
        beforeEnter: (to, from, next) => {
            if (localStorage.getItem('auth_token') === 'null') {
                next({
                    path: '/unauthorized',
                })
            } else {
                next()
            }
        },
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
