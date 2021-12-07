<template>
  <div>
    <main-banner />
    <main-view />
  </div>
</template>

<script>
import MainBanner from "@/components/main/MainBanner.vue";
import MainView from "@/components/main/MainView.vue";
export default {
  name: "Main",
  components: {
    MainBanner,
    MainView,
  },
};
</script>

<style></style>
