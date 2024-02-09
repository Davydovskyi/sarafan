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
      try {
        if (this.editing) {
          const response = await this.$axios.update(this.messageAttribute.id, {text: this.text})
          const index = this.messages.findIndex(m => m.id === response.data.id)
          this.messages.splice(index, 1, response.data)
          console.log(response)
        } else {
          const response = await this.$axios.add({text: this.text})
          const index = this.messages.findIndex(m => m.id === response.data.id)
          if (index !== -1) {
            this.messages.splice(index, 1, response.data)
          } else {
            this.messages.push(response.data)
          }
          console.log(response)
        }
      } catch (e) {
        console.error('Error submitting message:', e)
        alert(e.response.data.message)
      }
      this.editing = false
      this.text = ''
    }
  }
}
</script>

<style>
</style>