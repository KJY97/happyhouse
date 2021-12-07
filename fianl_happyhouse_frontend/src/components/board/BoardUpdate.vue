<template>
  <div>
    <board-banner />
    <board-write type="modify" />
  </div>
</template>

<script>
import BoardWrite from "./BoardWrite.vue";
import BoardBanner from "@/components/board/child/BoardBanner.vue";

export default {
  name: "BoardUpdate",
  components: {
    BoardWrite,
    BoardBanner,
  },
};
</script>

<style></style>
