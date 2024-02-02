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

export default {
  data() {
    return {
      messages: frontendData.messages,
      profile: frontendData.profile
    }
  },
  components: {
    'messages-list': MessageList
  }
}
</script>

<style>
</style>