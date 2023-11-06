// main.js
import { createApp } from 'vue/dist/vue.esm-bundler.js';
import {createRouter, createWebHistory} from 'vue-router';
import App from './App.vue';

import Lobby from './views/Lobby.vue';
import Login from './views/Login.vue';
import Room from "@/views/Room";
import LoginCallback from "@/views/LoginCallback";

const routes = [
    { path: '/', component: Lobby },
    { path: '/login', component: Login },
    { path: '/login/callback', component: LoginCallback },
    { path: '/room', component: Room },
];

const router = createRouter({
    mode: 'history',
    history: createWebHistory(),
    routes, // short for `routes: routes`
});

// const app = createApp({
//     // Add a template or render function here to specify where to render the router view
//     template: '<router-view></router-view>',
// });
const app = createApp(App);


app.use(router);

app.mount('#app');

