<template>
  <v-container class="spacing-playground pa-6">
    <v-layout class="flex-column align-content-space-around justify-start">
      <message-form :messageAttribute="message"/>
      <template :key="message.id" v-for="message in sortedMessages">
        <message-row :message="message"
                     :updateMessage="updateMessage"/>
      </template>
      <lazy-loader></lazy-loader>
    </v-layout>
  </v-container>
</template>

<script>
import {mapGetters} from 'vuex'
import MessageRow from '../components/messages/MessageRow.vue'
import MessageForm from '../components/messages/MessageForm.vue'
import LazyLoader from "../components/LazyLoader.vue";

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
  components: {LazyLoader, MessageRow, MessageForm}
}
</script>

<style>
</style>