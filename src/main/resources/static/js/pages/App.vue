<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Sarafan</v-toolbar-title>
      <v-spacer></v-spacer>
      <span v-if="profile">{{ profile.name }}</span>
      <v-btn icon v-if="profile" href="/logout">
        <v-icon icon="logout"/>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container v-if="!profile">
        You should be logged in <a href="/login">Google</a>
      </v-container>
      <v-container class="spacing-playground pa-6" v-if="profile">
        <messages-list/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import {mapMutations, mapState} from 'vuex'
import MessageList from 'components/messages/MessageList.vue'
import {addHandler} from 'util/ws'

export default {
  components: {
    'messages-list': MessageList
  },
  computed: {
    ...mapState([
      'profile'
    ])
  },
  methods: {
    ...mapMutations([
      'addMessageMutation',
      'updateMessageMutation',
      'deleteMessageMutation'
    ])
  },
  created() {
    addHandler(message => {
      const {event, type, body} = message
      if (message.type === 'MESSAGE') {
        const mutations = {
          'CREATED': this.addMessageMutation,
          'UPDATED': this.updateMessageMutation,
          'DELETED': (body) => this.deleteMessageMutation(body.id),
        }
        if (mutations[event]) {
          mutations[event](body)
        } else {
          console.error(`Unknown event type: "${event}"`)
        }
      } else {
        console.error(`Unknown message type: "${type}"`)
      }
    })
  }
}
</script>

<style>
</style>