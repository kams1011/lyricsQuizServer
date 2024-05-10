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
import VueVideoPlayer from 'vue-video-player'
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
    routes: [
        {
            path: '/',  // 루트를 제외한 나머지 path를
            name: 'Lobby',
            component: Lobby
        },
        {
            path: '/login',  // 구분을 위한 sub path로 앞을 맞춰줍니다.
            name: 'Login',
            component: Login
        },
        {
            path: '/login/callback',  // 구분을 위한 sub path로 앞을 맞춰줍니다.
            name: 'LoginCallback',
            component: LoginCallback
        },
        {
            path: '/user/register/:loginType/:id',  // 구분을 위한 sub path로 앞을 맞춰줍니다.
            name: 'UserRegister',
            component: UserRegister
        },
        {
            path: '/room/:roomSeq',  // 구분을 위한 sub path로 앞을 맞춰줍니다.
            name: 'Room',
            component: Room
        },
        {
            path: '/room/create',  // 구분을 위한 sub path로 앞을 맞춰줍니다.
            name: 'RoomCreate',
            component: RoomCreate
        },
        {
            path: '/quiz/register',  // 구분을 위한 sub path로 앞을 맞춰줍니다.
            name: 'QuizRegister',
            component: QuizRegister
        }
    ]
});


// Axios Interceptors 설정
axios.interceptors.response.use(
    // 정상적인 응답 처리
    response => {
        return response;
    },
    // 에러 응답 처리
    error => {
        console.log("ERROR::")
        console.log(error)
        if (error.response && error.response.status == 401) {
            alert('로그인이 필요합니다.');
            router.push('/login');
        } else if (error.response && error.response.status == 406 && error.response.data.errorCode.code =='AUTH-001') {
            return retryRequest(error);
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

function retryRequest(error) {
    // 기존 요청을 저장
    const originalRequest = error.config;

    // 새 Promise 반환
    return new Promise((resolve, reject) => {
        // 다시 요청
        axios.request(originalRequest)
            .then(response => {
                // 요청이 성공한 경우
                // 여기서 추가적인 동작을 수행할 수 있음
                resolve(response);
            })
            .catch(err => {
                // 다시 요청한 후에도 실패한 경우
                // 에러 처리 로직을 추가할 수 있음
                reject(err);
            });
    });
}

// Vue 인스턴스에 Axios 설정


const app = createApp(App).provide('$SERVER_URL', process.env.VUE_APP_SERVER_URL);

app.config.globalProperties.$http = axios;

app.use(router);
app.use(store); // Vuex 스토어 등록
app.use(ElementPlus);
app.use(VueVideoPlayer);

app.mount('#app');
