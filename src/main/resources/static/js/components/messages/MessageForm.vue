<template>
  <form @submit.prevent="onSubmit">
    <v-layout class="flex-row">
      <v-text-field class="my-2" clearable v-model="text"
                    label="Text"
                    variant="outlined"/>
      <v-btn type="submit" class="ml-2 my-4">Save</v-btn>
    </v-layout>
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
    }
  }
}
</script>

<style>
</style>