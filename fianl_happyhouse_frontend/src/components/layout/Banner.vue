<template>
  <header class="py-4 bg-light border-bottom mb-3">
    <div class="container">
      <div class="text-center my-1">
        <h1>질문게시판</h1>
        <h3 class="lead mb-0">궁금한 걸 물어보세요!</h3>
        <h1 class="mt-3 mb-3">👨‍👩‍👦‍👦</h1>
        <b-button variant="dark" @click="moveWrite()">문의하기</b-button>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "Banner",
  methods: {
    moveWrite() {
      this.$router.push({ name: "BoardWrite" });
    },
  },
};
</script>

<style></style>
