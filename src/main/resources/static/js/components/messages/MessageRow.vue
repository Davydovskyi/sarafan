<template>
  <v-card class="my-2" variant="outlined">
    <v-card-text>
      <div>
        <v-avatar v-if="message.user && message.user.user_pic" size="46px">
          <v-img
              :src="message.user.user_pic"
              :alt="message.user.name"
          ></v-img>
        </v-avatar>

        <v-avatar color="info" v-else size="46px">
          <v-icon icon="account_circle"></v-icon>
        </v-avatar>
        <span class="pl-4">{{ authorName }}</span>
      </div>
      <div class="pt-3">
        {{ message.text }}
      </div>
    </v-card-text>
    <media v-if="message.link" :message="message"></media>
    <v-card-actions>
      <v-btn size="small" @click="update" rounded>Update</v-btn>
      <v-btn size="small" variant="flat" icon @click="del(message.id)">
        <v-icon icon="delete"/>
      </v-btn>
    </v-card-actions>
    <comment-list :comments="message.comments" :message-id="message.id"></comment-list>
  </v-card>
</template>

<script>
import {mapActions} from 'vuex'
import Media from "components/media/Media.vue"
import CommentList from "../comment/CommentList.vue";

export default {
  props: ['message', 'updateMessage'],
  components: {Media, CommentList},
  computed: {
    authorName() {
      return this.message.user ? this.message.user.name : 'unknown'
    }
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