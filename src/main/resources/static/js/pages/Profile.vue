<template>
  <v-container>
    <v-layout class="d-flex justify-space-around flex-column">
      <div class="v-toolbar-title mb-2">User Profile</div>
      <v-layout class="d-flex flex-row justify-space-between px-1">
        <v-img :src="profile.user_pic"/>
        <v-layout class="d-flex flex-column px-1">
          <div>Name: {{ profile.name }}</div>
          <div>Locale: {{ profile.locale }}</div>
          <div>Gender: {{ profile.gender }}</div>
          <div>Email: {{ profile.email }}</div>
          <div>Last visit: {{ profile.last_visit }}</div>
        </v-layout>
      </v-layout>
    </v-layout>
  </v-container>

</template>

<script>
import {mapState} from 'vuex'

export default {
  computed: {
    ...mapState([
      'profile'
    ])
  }
}

</script>

<style>
img {
  max-width: 100%;
  height: auto;
}
</style>