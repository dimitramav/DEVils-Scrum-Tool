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
Vue.use(Router)

export default new Router({
  routes: [
    {
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
      path: '/sidebar',
      name: 'Sidebar',
      component: Sidebar
    },
    {
      path: '/project/:id/overview',
      name: 'ProjectPageOverview',
      component: ProjectPageOverview
    },
    {
      path: '/project/:id/newsprint',
      name: 'NewSprint',
      component: NewSprint
    },
    {
      path: '/project/:id/backlog',
      name: 'Backlog',
      component: Backlog
    },
    {
      path: '/oldsprints/:id',
      name: 'OldSprints',
      component: OldSprints
    },
    {
      path:'/users/:id',
      name: 'Profile',
      component: Profile
    },
    {
      path:'/editprofile',
      name: 'EditProfile',
      component: EditProfile
    },
    {
      path:'/project/:id/sprintbacklog',
      name: 'SprintBacklog',
      component: SprintBacklog
    },
    {
      path:'*',
      name: 'PageNotFound',
      component: PageNotFound
    }
  ]
})
