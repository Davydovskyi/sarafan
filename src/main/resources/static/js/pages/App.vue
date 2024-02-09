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
        <messages-list :messages="messages"/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import MessageList from "../components/messages/MessageList.vue"
import {addHandler} from "../util/ws"

export default {
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  components: {
    'messages-list': MessageList
  },
  created() {
    addHandler(message => {
      if (message.type === 'MESSAGE') {
        const index = this.messages.findIndex(m => m.id === message.body.id)

        switch (message.event) {
          case 'CREATED':
          case 'UPDATED':
            if (index === -1) {
              this.messages.push(message.body)
            } else {
              this.messages.splice(index, 1, message.body)
            }
            break
          case 'DELETED':
            this.messages.splice(index, 1)
            break
          default:
            console.error(`Unknown event type: "${message.event}"`)
        }
      } else {
        console.error(`Unknown message type: "${message.type}"`)
      }
    })
  }
}
</script>

<style>
</style>