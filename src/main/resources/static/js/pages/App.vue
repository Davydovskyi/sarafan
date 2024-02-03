<template>
  <div>
    <div v-if="!profile">You should be logged in
      <a href="/login">Google</a>
    </div>
    <div v-else>
      <div>{{ profile.name }}&nbsp;
        <a href="/logout">Logout</a>
      </div>
      <messages-list :messages="messages"/>
    </div>
  </div>
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