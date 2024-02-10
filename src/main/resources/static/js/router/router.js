import {createRouter, createWebHistory} from 'vue-router'
import MessageList from 'pages/MessageList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'

const routes = [
    {path: '/', component: MessageList},
    {path: '/auth', component: Auth},
    {path: '/profile', component: Profile},
    {path: '/:pathMatch(.*)', component: MessageList}
]

export const router = createRouter({
    history: createWebHistory(),
    routes
})