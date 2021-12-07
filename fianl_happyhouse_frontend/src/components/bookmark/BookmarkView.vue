<template>
  <div class="container">
    <div class="row">
      <div class="col-lg-4 mt-2">
        <bookmark-info />
      </div>
      <kakao-map />
    </div>
  </div>
</template>

<script>
import BookmarkInfo from "@/components/bookmark/child/BookmarkInfo.vue";
import KakaoMap from "@/components/bookmark/child/KakaoMap.vue";

export default {
  components: {
    BookmarkInfo,
    KakaoMap,
  },
};
</script>

<style></style>
