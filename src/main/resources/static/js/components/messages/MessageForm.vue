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
import {mapActions} from 'vuex'

export default {
  props: ['messageAttribute'],
  data() {
    return {
      text: '',
      id: '',
      editing: false
    }
  },
  watch: {
    messageAttribute(newVal) {
      this.text = newVal.text
      this.id = newVal.id
      this.editing = true
    }
  },
  methods: {
    ...mapActions([
      'addMessageAction',
      'updateMessageAction',
    ]),
    onSubmit() {
      const {id, text, editing} = this
      const message = {id, text}

      if (editing) {
        this.updateMessageAction(message)
      } else {
        this.addMessageAction(message)
      }

      this.text = ''
      this.id = ''

      if (editing) {
        this.editing = false
      }
    }
  }
}
</script>

<style>
</style>