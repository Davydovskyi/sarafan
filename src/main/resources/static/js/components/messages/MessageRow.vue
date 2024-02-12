<template>
  <v-card class="my-2" variant="outlined">
    <v-card-text>
      <i>{{ message.id }}. </i> {{ message.text }}
    </v-card-text>
    <media v-if="message.link" :message="message"></media>
    <v-card-actions>
      <v-btn size="small" @click="update" rounded>Update</v-btn>
      <v-btn size="small" variant="flat" icon @click="del(message.id)">
        <v-icon icon="delete"/>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import {mapActions} from 'vuex'
import Media from "components/media/Media.vue"

export default {
  props: ['message', 'updateMessage'],
  components: {
    'media': Media
  },
  methods: {
    ...mapActions([
      'deleteMessageAction'
    ]),
    update() {
      this.updateMessage(this.message)
    },
    del(id) {
      this.deleteMessageAction(id)
    }
  }
}
</script>

<style>
</style>