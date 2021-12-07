<template>
  <div id="app">
    <join-banner></join-banner>
    <join-view></join-view>
  </div>
</template>

<script>
import JoinBanner from "@/components/member/join/JoinBanner.vue";
import JoinView from "@/components/member/join/JoinView.vue";

export default {
  name: "Join",
  components: {
    JoinBanner,
    JoinView,
  },
};
</script>

<style></style>
