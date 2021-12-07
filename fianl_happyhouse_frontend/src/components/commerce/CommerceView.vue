<template>
  <div class="container">
    <div class="row">
      <div class="col-lg-4 mt-2">
        <commerce-info />
        <notice-info />
      </div>
      <commerce-detail />
    </div>
  </div>
</template>

<script>
import CommerceInfo from "@/components/commerce/child/CommerceInfo.vue";
import NoticeInfo from "@/components/commerce/child/NoticeInfo.vue";
import CommerceDetail from "@/components/commerce/child/CommerceDetail.vue";

export default {
  components: {
    CommerceInfo,
    NoticeInfo,
    CommerceDetail,
  },
};
</script>

<style></style>
