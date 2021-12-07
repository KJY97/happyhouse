<template>
  <div>
    <bookmark-banner />
    <bookmark-view />
  </div>
</template>

<script>
import BookmarkBanner from "@/components/bookmark/BookmarkBanner.vue";
import BookmarkView from "@/components/bookmark/BookmarkView.vue";
export default {
  name: "Bookmark",
  components: {
    BookmarkBanner,
    BookmarkView,
  },
};
</script>

<style></style>
