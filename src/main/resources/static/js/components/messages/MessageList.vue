<template>
  <v-layout class="flex-column align-content-space-around justify-start">
    <message-form :messageAttribute="message"/>
    <template :key="message.id" v-for="message in sortedMessages">
      <message-row :message="message"
                   :updateMessage="updateMessage"/>
    </template>
  </v-layout>
</template>

<script>
import {mapGetters} from 'vuex'
import MessageRow from './MessageRow.vue'
import MessageForm from './MessageForm.vue'

export default {
  data() {
    return {
      message: null
    }
  },
  computed: {
    ...mapGetters([
      'sortedMessages'
    ])
  },
  methods: {
    updateMessage(message) {
      this.message = message
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