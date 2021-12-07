<template>
  <div>
    <commerce-banner />
    <commerce-view />
  </div>
</template>

<script>
import CommerceBanner from "@/components/commerce/CommerceBanner.vue";
import CommerceView from "@/components/commerce/CommerceView.vue";
export default {
  name: "Commerce",
  components: {
    CommerceBanner,
    CommerceView,
  },
};
</script>

<style></style>
