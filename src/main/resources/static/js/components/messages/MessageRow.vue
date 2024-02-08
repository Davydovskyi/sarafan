<template>
  <v-card class="my-2" variant="outlined">
    <v-card-text>
      <i>{{ message.id }}. </i> {{ message.text }}
    </v-card-text>
    <v-card-actions>
      <v-btn size="small" @click="update" rounded>Update</v-btn>
      <v-btn size="small" flat icon @click="del(message.id)">
        <v-icon icon="delete"/>
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>

export default {
  props: ['message', 'updateMessage', 'deleteMessage', 'messages'],
  methods: {
    update() {
      this.updateMessage(this.message)
    },
    del(id) {
      this.deleteMessage(id)
    }
  }
}
</script>

<style>
</style>