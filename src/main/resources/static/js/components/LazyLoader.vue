<template>
<span>
</span>
</template>

<script>
import {mapActions} from "vuex"

export default {
  methods: {
    ...mapActions([
      'loadPageAction'
    ])
  },
  mounted() {
    window.onscroll = () => {
      if ((window.innerHeight + window.scrollY + 0.5) >= document.body.offsetHeight) {
        this.loadPageAction()
      }
    }
  },
  beforeUnmount() {
    window.onscroll = null
  }
}
</script>

<style>

</style>