<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title>Sarafan</v-toolbar-title>
      <v-btn
          variant="text"
          v-if="profile"
          :disabled="$route.path === '/'"
          @click="showMessages">
        Messages
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn
          variant="text"
          v-if="profile"
          :disabled="$route.path === '/profile'"
          @click="showProfile">
        {{ profile.name }}
      </v-btn>
      <v-btn icon v-if="profile" href="/logout">
        <v-icon icon="logout"/>
      </v-btn>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
import {mapMutations, mapState} from 'vuex'
import {addHandler} from 'util/ws'

export default {
  computed: {
    ...mapState([
      'profile'
    ])
  },
  methods: {
    ...mapMutations([
      'addMessageMutation',
      'updateMessageMutation',
      'deleteMessageMutation',
      'addCommentMutation'
    ]),
    showProfile() {
      this.$router.push('/profile')
    },
    showMessages() {
      this.$router.push('/')
    }
  },
  created() {
    addHandler(message => {
      const {event, type, body} = message
      if (message.type === 'MESSAGE') {
        const messageMutations = {
          'CREATED': this.addMessageMutation,
          'UPDATED': this.updateMessageMutation,
          'DELETED': (body) => this.deleteMessageMutation(body.id),
        }
        if (messageMutations[event]) {
          messageMutations[event](body)
        } else {
          console.error(`Unknown event type for message: "${event}"`)
        }
      } else if (message.type === 'COMMENT') {
        const commentMutations = {
          'CREATED': this.addCommentMutation
        }
        if (commentMutations[event]) {
          commentMutations[event](body)
        } else {
          console.error(`Unknown event type for comment: "${event}"`)
        }
      } else {
        console.error(`Unknown message type: "${type}"`)
      }
    })
  },
  beforeMount() {
    if (!this.profile) {
      this.$router.replace('/auth')
    }
  }
}
</script>

<style>
</style>