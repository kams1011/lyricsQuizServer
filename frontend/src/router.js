import Vue from 'vue'
import Router from 'vue-router'
import Lobby from './views/Lobby'
import Room from './views/Room'

Vue.use(Router)


export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name : 'lobby',
      component : Lobby
    },
    {
      path: '/room',
      name : 'room',
      component : Room
    },
  ]
})