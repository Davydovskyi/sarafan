import * as Vue from 'vue'
import App from "./pages/App.vue"
import axiosInstance from './axios-instance'
import {connect} from './util/ws';

connect()

const app = Vue.createApp({
        render() {
            return Vue.h(App)
        }
    }
)

app.config.globalProperties.$axios = axiosInstance

app.mount('#app')