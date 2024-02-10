import * as Vue from 'vue'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import {createVuetify} from 'vuetify'
import {aliases, md} from 'vuetify/iconsets/md'
import 'vuetify/styles'
import 'core-js'
import App from 'pages/App.vue'
import {connect} from 'util/ws'
import {store} from 'store/store'
import {router} from 'router/router'

if (frontendData.profile) {
    connect()
}

const app = Vue.createApp({
        render() {
            return Vue.h(App)
        }
    }
)

const vuetify = createVuetify({
    components,
    directives,
    icons: {
        defaultSet: 'md',
        aliases,
        sets: {
            md,
        },
    },
})

app
    .use(vuetify)
    .use(store)
    .use(router)
    .mount('#app')