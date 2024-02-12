<template>
  <iframe
      width="560"
      height="315"
      :src="targetSrc"
      frameborder="0"
      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
      allowfullscreen>
  </iframe>
</template>
<script>
export default {
  props: ['src'],
  computed: {
    targetSrc() {
      const parts = this.src.split('/')
      return `https://www.youtube.com/embed/${parts[parts.length - 1]}`
    }
  }
}
</script>

<style>

</style>