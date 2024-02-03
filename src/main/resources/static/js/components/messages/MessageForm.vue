<template>
  <form @submit.prevent="onSubmit">
    <div class="form-group">
      <textarea class="form-control" placeholder="text" rows="3" v-model="text"></textarea>
    </div>
    <button class="btn btn-primary" type="submit">Save</button>
  </form>
</template>

<script>
import {sendMessage} from 'util/ws'

export default {
  props: ['messageAttribute', 'messages'],
  data() {
    return {
      text: '',
      editing: false
    }
  },
  watch: {
    messageAttribute(newVal) {
      this.text = newVal.text
      this.editing = true
    }
  },
  methods: {
    async onSubmit() {
      if (this.editing) {
        sendMessage({id: this.messageAttribute.id, text: this.text})
      } else {
        const response = await this.$axios.post('/messages', {text: this.text})
        this.messages.push(response.data)
        console.log(response)
      }
      this.editing = false
      this.text = ''

      // try {
      //   if (this.editing) {
      //     const response = await this.$axios.put(`/messages/${this.messageAttribute.id}`, {text: this.text})
      //     const index = this.messages.findIndex(m => m.id === response.data.id)
      //     this.messages.splice(index, 1, response.data)
      //     console.log(response)
      //   } else {
      //     const response = await this.$axios.post('/messages', {text: this.text})
      //     this.messages.push(response.data)
      //     console.log(response)
      //   }
      //   this.editing = false
      //   this.text = ''
      // } catch (e) {
      //   console.error('Error submitting message:', e)
      //   alert(e.response.data.message)
      // }
    }
  }
}
</script>

<style>
</style>