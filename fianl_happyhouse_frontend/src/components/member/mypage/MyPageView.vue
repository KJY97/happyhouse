<template>
  <div class="container">
    <!-- <b-alert show variant="dark"><h3>회원 정보 확인</h3></b-alert> -->
    <member-write-form />

  </div>
</template>

<script>
import MemberWriteForm from "@/components/member/child/MemberWriteForm.vue";

export default {
  name: "MyPageView",
  components: {
    MemberWriteForm,
  },
};
</script>

<style></style>