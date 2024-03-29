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
import axios from 'axios';
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


// Axios Interceptors 설정
axios.interceptors.response.use(
    // 정상적인 응답 처리
    response => {
        return response;
    },
    // 에러 응답 처리
    error => {
        if (error.response.data.status = 401) {
            router.push('/login');
        } else if (error.response) {
            // HTTP 응답 코드가 있는 경우
            console.error("HTTP Error:", error.response.status);
        } else if (error.request) {
            // 요청은 보냈지만 응답이 없는 경우
            console.error("No response received:", error.request);
        } else {
            // 요청을 보내기 전에 에러가 발생한 경우
            console.error("Error during request setup:", error.message);
        }
        // Promise를 사용하여 에러 전파
        return Promise.reject(error);
    }
);

// Vue 인스턴스에 Axios 설정


const app = createApp(App);

app.config.globalProperties.$http = axios;
app.use(router);
app.use(store); // Vuex 스토어 등록
app.use(ElementPlus);

app.mount('#app');
