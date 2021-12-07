<template>
  <div id="app">
    <login-banner></login-banner>
    <login-view></login-view>
  </div>
</template>

<script>
import LoginBanner from "@/components/member/login/LoginBanner.vue";
import LoginView from "@/components/member/login/LoginView.vue";

export default {
  name: "Login",
  components: {
    LoginBanner,
    LoginView,
  },
};
</script>

<style></style>