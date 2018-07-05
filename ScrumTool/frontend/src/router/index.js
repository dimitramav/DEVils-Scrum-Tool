import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import SignUp from '@/components/SignUp'
import SignIn from '@/components/SignIn'
import Sidebar from '@/components/Sidebar'
import ProjectPageOverview from '@/components/ProjectPageOverview'
import NewSprint from '@/components/NewSprint'
import Logout from '@/components/Logout'
import Backlog from '@/components/Backlog/'
import OldSprints from '@/components/OldSprints'
import Profile from '@/components/Profile'
import EditProfile from '@/components/EditProfile'
import SprintBacklog from '@/components/SprintBacklog'
import PageNotFound from '@/components/PageNotFound'
import Unauthorized from '@/components/Unauthorized'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index,

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
      path: '/sidebar',
      name: 'Sidebar',
      component: Sidebar
    },
    {
      path: '/project/:id/overview',
      name: 'ProjectPageOverview',
      component: ProjectPageOverview,
      beforeEnter: (to, from, next) => {
        if(localStorage.getItem('auth_token')==='null') {
          console.log("hey router")
          // redirect to 401 Unauthorized Access
          next({
            path: '/unauthorized'
          })
        }
        else {
          next(vm => {
            // Go to the overview page
          })
        }
      }
    },
    {
      path: '/project/:id/newsprint',
      name: 'NewSprint',
      component: NewSprint,
      beforeEnter: (to, from, next) => {
        if(localStorage.getItem('auth_token')==='null') {
          console.log("hey router")
          // redirect to 401 Unauthorized Access
          next({
            path: '/unauthorized'
          })
        }
        else {
          next(vm => {
            // Go to the overview page
          })
        }
      }
    },
    {
      path: '/project/:id/backlog',
      name: 'Backlog',
      component: Backlog,
      beforeEnter: (to, from, next) => {
        if(localStorage.getItem('auth_token')==='null') {
          console.log("hey router")
          // redirect to 401 Unauthorized Access
          next({
            path: '/unauthorized'
          })
        }
        else {
          next(vm => {
            // Go to the overview page
          })
        }
      }
    },
    {
      path: '/oldsprints/:id',
      name: 'OldSprints',
      component: OldSprints,
      beforeEnter: (to, from, next) => {
        if(localStorage.getItem('auth_token')==='null') {
          console.log("hey router")
          // redirect to 401 Unauthorized Access
          next({
            path: '/unauthorized'
          })
        }
        else {
          next(vm => {
            // Go to the overview page
          })
        }
      }
    },
    {
      path:'/users/:id',
      name: 'Profile',
      component: Profile,
      beforeEnter: (to, from, next) => {
        if(localStorage.getItem('auth_token')==='null') {
          console.log("hey router")
          // redirect to 401 Unauthorized Access
          next({
            path: '/unauthorized'
          })
        }
        else {
          next(vm => {
            // Go to the overview page
          })
        }
      }
    },
    {
      path:'/editprofile',
      name: 'EditProfile',
      component: EditProfile,
      beforeEnter: (to, from, next) => {
        if(localStorage.getItem('auth_token')==='null') {
          console.log("hey router")
          // redirect to 401 Unauthorized Access
          next({
            path: '/unauthorized'
          })
        }
        else {
          next(vm => {
            // Go to the overview page
          })
        }
      }
    },
    {
      path:'/project/:id/sprintbacklog',
      name: 'SprintBacklog',
      component: SprintBacklog,
      beforeEnter: (to, from, next) => {
        if(localStorage.getItem('auth_token')==='null') {
          console.log("hey router")
          // redirect to 401 Unauthorized Access
          next({
            path: '/unauthorized'
          })
        }
        else {
          next(vm => {
            // Go to the overview page
          })
        }
      }
    },
    {
      path:'*',
      name: 'PageNotFound',
      component: PageNotFound
    },
    {
      path:'/unauthorized',
      name: 'Unauthorized',
      component: Unauthorized
    }
  ]
})
