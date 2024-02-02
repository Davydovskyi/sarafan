<template>
  <div>
    <i>{{ message.id }}. </i> {{ message.text }}
    <span style="position: absolute; right: 0">
      <button @click='update' class="btn btn-warning btn-sm">Update</button>
      <button @click='del(message.id)' class="btn btn-danger btn-sm">Delete</button>
    </span>
  </div>
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