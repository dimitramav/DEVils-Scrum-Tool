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
      path: '/projectpageoverview/:id',
      name: 'ProjectPageOverview',
      component: ProjectPageOverview
    },
    {
      path: '/newsprint/:id',
      name: 'NewSprint',
      component: NewSprint
    },
    {
      path: '/backlog/:id',
      name: 'Backlog',
      component: Backlog
    },
    {
      path: '/oldsprints/:id',
      name: 'OldSprints',
      component: OldSprints
    },
    {
      path:'/profile/:name',
      name: 'Profile',
      component: Profile
    }
  ]
})
