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
<<<<<<< Updated upstream
      path: '/sidebar',
      name: 'Sidebar',
      component: Sidebar
    },
    {
      path: '/projectpageoverview/:id',
=======
      path: '/projectpageoverview#:id',
>>>>>>> Stashed changes
      name: 'ProjectPageOverview',
      component: ProjectPageOverview
    },
    {
      path: '/newsprint',
      name: 'NewSprint',
      component: NewSprint
    },
    {
      path: '/backlog',
      name: 'Backlog',
      component: Backlog
    },
    {
      path: '/oldsprints',
      name: 'OldSprints',
      component: OldSprints  
    }
  ]
})
