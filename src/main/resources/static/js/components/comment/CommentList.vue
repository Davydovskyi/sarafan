<template>
  <v-list lines="two">
    <v-list-subheader>
      Comments
    </v-list-subheader>
    <comment-item v-for="(comment, index) in comments" :key="comment.id" :comment="comment">
      <v-divider v-if="index < comments.length - 1"/>
    </comment-item>
    <comment-form :message-id="messageId"/>
  </v-list>
</template>

<script>
import CommentItem from "./CommentItem.vue";
import CommentForm from "./CommentForm.vue";

export default {
  components: {CommentForm, CommentItem},
  props: ['comments', 'messageId'],
}
</script>

<style>

</style>