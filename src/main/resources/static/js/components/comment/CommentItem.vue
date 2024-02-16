<template>
  <v-list-item :prepend-avatar="comment.user.user_pic"
               :title="comment.text"
               :subtitle="comment.user.name"/>
  <v-divider v-if="showDivider"/>
</template>

<script>
export default {
  props: ['comment', 'showDivider'],
}
</script>

<style>

</style>