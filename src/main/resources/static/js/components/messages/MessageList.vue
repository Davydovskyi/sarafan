<template>
  <div style="position: relative; width: 300px;">
    <message-form :messageAttribute="message" :messages="messages"/>
    <template :key="message.id" v-for="message in messages">
      <message-row :message="message"
                   :updateMessage="updateMessage"
                   :deleteMessage="deleteMessage"
                   :messages="messages"/>
    </template>
  </div>
</template>

<script>
import MessageRow from "./MessageRow.vue"
import MessageForm from "./MessageForm.vue"

export default {
  props: ['messages'],
  data() {
    return {
      message: null
    }
  },
  methods: {
    updateMessage(message) {
      this.message = message
    },
    async deleteMessage(id) {
      try {
        const response = await this.$axios.delete(`/messages/${id}`)
        console.log(response);
        if (response.status === 204) {
          const index = this.messages.findIndex(m => m.id === id)
          this.messages.splice(index, 1)
        }
      } catch (e) {
        console.error('Error deleting message:', e)
        alert(e.response.data.message)
      }
    }
  },
  components: {
    'message-row': MessageRow,
    'message-form': MessageForm
  }
}
</script>

<style>
</style>