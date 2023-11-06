// main.js
import { createApp } from 'vue/dist/vue.esm-bundler.js';
import { createRouter, createWebHashHistory } from 'vue-router';
import App from './App.vue';

// 1. Define route components.
// These can be imported from other files
import Lobby from './views/Lobby.vue';
import Login from './views/Login.vue';
import Room from "@/views/Room";

// 2. Define some routes
// Each route should map to a component.
// We'll talk about nested routes later.
const routes = [
    { path: '/', component: Lobby },
    { path: '/login', component: Login },
    { path: '/room', component: Room },
];

// 3. Create the router instance and pass the `routes` option
// You can pass in additional options here, but let's
// keep it simple for now.
const router = createRouter({
    // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
    history: createWebHashHistory(),
    routes, // short for `routes: routes`
});

// 5. Create and mount the root instance.
// const app = createApp({
//     // Add a template or render function here to specify where to render the router view
//     template: '<router-view></router-view>',
// });
const app = createApp(App);


// Make sure to _use_ the router instance to make the
// whole app router-aware.
app.use(router);

// Mount the app to the element with id 'app'
app.mount('#app');

// Now the app has started!
