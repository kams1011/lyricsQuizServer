// src/main.js
import { createApp } from 'vue/dist/vue.esm-bundler.js';
import { createRouter, createWebHistory } from 'vue-router';
import App from './App.vue';
import store from './store';


import Lobby from './views/Lobby.vue';
import Login from './views/Login.vue';
import Room from "@/views/Room";
import LoginCallback from "@/views/LoginCallback";
import UserRegister from "@/views/UserRegister";
import QuizRegister from "@/views/QuizRegister";
import RoomCreate from "@/views/RoomCreate";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const routes = [
    { path: '/', component: Lobby },
    { path: '/login', component: Login },
    { path: '/login/callback', component: LoginCallback },
    { path: '/user/register/:loginType/:id', component: UserRegister },
    { path: '/room/:roomSeq', component: Room, props: true },
    { path: '/room/create', component: RoomCreate },
    { path: '/quiz/register', component: QuizRegister },
];

const router = createRouter({
    mode: 'history',
    history: createWebHistory(),
    routes,
});

const app = createApp(App);

app.use(router);
app.use(store); // Vuex 스토어 등록
app.use(ElementPlus);

app.mount('#app');
