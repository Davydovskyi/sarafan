import * as Vue from 'vue'
import App from 'pages/App.vue'
import {connect} from 'util/ws'
import {createVuetify} from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import {aliases, md} from 'vuetify/iconsets/md'
import 'vuetify/styles'
import 'core-js'
import {store} from 'store/store'

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

app.use(vuetify)
app.use(store)
app.mount('#app')