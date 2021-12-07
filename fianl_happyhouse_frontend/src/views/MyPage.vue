<template>
  <div id="app">
    <my-page-banner></my-page-banner>
    <my-page-view></my-page-view>
  </div>
</template>

<script>
import MyPageBanner from "@/components/member/mypage/MyPageBanner.vue";
import MyPageView from "@/components/member/mypage/MyPageView.vue";

export default {
  name: "MyPage",
  components: {
    MyPageBanner,
    MyPageView,
  },
};
</script>

<style></style>