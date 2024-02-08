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
      const index = this.messages.findIndex(m => m.id === message.id)
      this.messages.splice(index, 1, message)
      console.log(message)
    })
  }
}
</script>

<style>
</style>