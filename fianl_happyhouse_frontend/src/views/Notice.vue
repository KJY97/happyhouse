<template>
  <div>
    <notice-banner></notice-banner>
    <router-view></router-view>
  </div>
</template>

<script>
import NoticeBanner from "@/components/notice/NoticeBanner.vue";

export default {
  name: 'Notice',
  components: {
    NoticeBanner,
  },
};
</script>
<style></style>