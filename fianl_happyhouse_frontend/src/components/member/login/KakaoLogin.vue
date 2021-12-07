<template>
  <div class="wrapper text-center">
    <p>Loading...</p>
    <b-spinner
      style="width: 3rem; height: 3rem"
      label="Large Spinner"
    ></b-spinner>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "KakaoLogin",
  data() {
    return {
      AUTHORIZE_CODE: "",
    };
  },
  created() {
    this.AUTHORIZE_CODE = new URL(window.location.href).searchParams.get(
      "code"
    ); // 인가코드
    this.getToken();
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["getKakaoLoginToken", "getUserInfo"]),

    async getToken() {
      await this.getKakaoLoginToken(this.AUTHORIZE_CODE);
      let token = sessionStorage.getItem("access-token");
      // console.log(token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "Main" });
      }
    },
  },
};
</script>

<style scoped>
.wrapper {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -50px;
  margin-left: -50px;
  width: 100px;
  height: 100px;
  font-family: system-ui, serif;
  font-size: 2rem;
}
</style>
