import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/views/Index'
import SignUp from '@/views/SignUp'
import SignIn from '@/views/SignIn'
import ProjectPageOverview from '@/views/ProjectPageOverview'
import NewSprint from '@/views/NewSprint'
import Logout from '@/views/Logout'
import Backlog from '@/views/Backlog/'
import OldSprints from '@/views/OldSprints'
import Profile from '@/views/Profile'
import EditProfile from '@/views/EditProfile'
import SprintBacklog from '@/views/SprintBacklog'
import PageNotFound from '@/views/PageNotFound'
import Unauthorized from '@/views/Unauthorized'
import ForgotPass from '@/views/ForgotPass'

Vue.use(Router)


export default new Router({
    mode: 'history',
    routes: [{
            path: '/',
            name: 'Index',
            component: Index
        },
        {
            path: '/signup',
            name: 'SignUp',
            component: SignUp
        },
        {
            path: '/signin',
            name: 'SignIn',
            component: SignIn
        },
        {
            path: '/logout',
            name: 'Logout',
            component: Logout
        },
        {
            path: '/forgotpass',
            name: 'ForgotPass',
            component: ForgotPass
        },
        {
            path: '/project/:id/overview',
            name: 'ProjectPageOverview',
            component: ProjectPageOverview,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem('auth_token') === 'null') {
                    console.log("hey router")
                    // redirect to 401 Unauthorized Access
                    next({
                        path: '/unauthorized'
                    });
                } else {
                    next(vm => {
                        // Go to the overview page
                    });
                }
            }
        },
        {
            path: '/project/:id/newsprint',
            name: 'NewSprint',
            component: NewSprint,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem('auth_token') === 'null') {
                    console.log("hey router")
                    // redirect to 401 Unauthorized Access
                    next({
                        path: '/unauthorized'
                    });
                } else {
                    next(vm => {
                        // Go to the overview page
                    });
                }
            }
        },
        {
            path: '/project/:id/backlog',
            name: 'Backlog',
            component: Backlog,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem('auth_token') === 'null') {
                    console.log("hey router")
                    // redirect to 401 Unauthorized Access
                    next({
                        path: '/unauthorized'
                    });
                } else {
                    next(vm => {
                        // Go to the overview page
                    });
                }
            }
        },
        {
            path: '/oldsprints/:id',
            name: 'OldSprints',
            component: OldSprints,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem('auth_token') === 'null') {
                    console.log("hey router")
                    // redirect to 401 Unauthorized Access
                    next({
                        path: '/unauthorized'
                    });
                } else {
                    next(vm => {
                        // Go to the overview page
                    });
                }
            }
        },
        {
            path: '/users/:id',
            name: 'Profile',
            component: Profile,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem('auth_token') === 'null') {
                    console.log("hey router")
                    // redirect to 401 Unauthorized Access
                    next({
                        path: '/unauthorized'
                    });
                } else {
                    next(vm => {
                        // Go to the overview page
                    });
                }
            }
        },
        {
            path: '/editprofile',
            name: 'EditProfile',
            component: EditProfile,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem('auth_token') === 'null') {
                    console.log("hey router")
                    // redirect to 401 Unauthorized Access
                    next({
                        path: '/unauthorized'
                    });
                } else {
                    next(vm => {
                        // Go to the overview page
                    });
                }
            }
        },
        {
            path: '/project/:id/sprintbacklog',
            name: 'SprintBacklog',
            component: SprintBacklog,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem('auth_token') === 'null') {
                    console.log("hey router")
                    // redirect to 401 Unauthorized Access
                    next({
                        path: '/unauthorized'
                    });
                } else {
                    next(vm => {
                        // Go to the overview page
                    });
                }
            }
        },
        {
            path: '*',
            name: 'PageNotFound',
            component: PageNotFound
        },
        {
            path: '/unauthorized',
            name: 'Unauthorized',
            component: Unauthorized
        }
    ]
})
