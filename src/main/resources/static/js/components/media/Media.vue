<template>
  <v-card class="mx-auto" max-width="560">
    <div v-if="type === 'href'">
      <v-img v-if="message.link_cover"
             class="align-end text-white"
             height="200"
             :src="message.link_cover"
             cover>
        <v-card-title>
          <a :href="message.link" class="text-blue">{{ message.link_title || message.link }}</a>
          <div v-if="message.link_description">{{ message.link_description }}</div>
        </v-card-title>
      </v-img>
    </div>

    <div v-if="type === 'image'">
      <a :href="message.link">
        <v-img v-if="message.link_cover"
               class="align-end"
               height="200"
               :src="message.link_cover"
               cover></v-img>
        {{ message.link }}
      </a>
    </div>

    <div v-if="type === 'youtube'">
      <you-tube :src="message.link"></you-tube>
    </div>

  </v-card>
</template>

<script>
import YouTube from "./YouTube.vue"

export default {
  props: ['message'],
  components: {
    'you-tube': YouTube
  },
  data() {
    return {
      type: 'href',
    }
  },
  beforeMount() {
    const link = this.message.link
    if (link.includes('youtu')) {
      this.type = 'youtube'
    } else if (/\.(jpeg|jpg|gif|png)$/.test(link)) {
      this.type = 'image'
    } else {
      this.type = 'href'
    }
  }
}

</script>

<style>

</style>