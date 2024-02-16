<template>
  <form @submit.prevent="onSubmit">
    <v-layout class="flex-row px-3">
      <v-text-field class="my-2" clearable v-model="text"
                    label="Add comment"
                    variant="outlined"/>
      <v-btn type="submit" class="ml-2 my-4">Add</v-btn>
    </v-layout>
  </form>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  computed: {
    ...mapState([
      'profile'
    ])
  },
  props: ['messageId'],
  data() {
    return {
      text: ''
    }
  },
  methods: {
    ...mapActions([
      'addCommentAction'
    ]),
    onSubmit() {
      this.addCommentAction({
        text: this.text,
        message: {id: this.messageId},
        user: this.profile
      })
      this.text = ''
    }
  }
}
</script>

<style>

</style>