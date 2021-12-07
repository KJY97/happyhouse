<template>
  <b-container>
    <b-row>
      <b-col></b-col>
      <b-col cols="5">
        <b-card class="text-center mt-3">
          <template #header>
            <h3>Login</h3>
          </template>
          <b-form>
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                required
                placeholder="Enter Id"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>

            <b-form-group label="" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="Enter password"
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
          </b-form>
          <b-card-text class="mt-3"> 해피하우스 계정으로 로그인 </b-card-text>
          <b-button block type="button" variant="primary" @click="confirm"
            >로그인</b-button
          >
          <!-- <b-card-text class="mt-3"> Forget your Password? </b-card-text> -->

          <b-row class="mt-3">
            <b-col><hr /></b-col>
            <b-col cols="1">or</b-col>
            <b-col><hr /></b-col>
          </b-row>

          <b-button block class="mt-2" variant="success" @click="movePage"
            >회원가입</b-button
          >
          <div>
            <b-button
              block
              class="mt-2"
              variant="warning"
              @click="loginKakao()"
            >
              <img
                src="@/assets/kakao_icon.png"
                style="max-width: 5%; height: auto"
              />&nbsp;&nbsp;카카오 로그인</b-button
            >
          </div>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { KAKAO_AUTH_URL } from "@/config/index";
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "LoginView",
  data() {
    return {
      user: {
        userid: null,
        userpwd: null,
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    // 비동기 처리
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      // 정상적으로 로그인 되면 여기서 걸린다
      // 토큰으로 인증 처리
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "Main" });
      }
    },
    movePage() {
      this.$router.push({ name: "Join" });
    },
    loginKakao() {
      window.location.replace(KAKAO_AUTH_URL);
      // window.open(KAKAO_AUTH_URL, 'PopupWin', 'width=500,height=600');
    },
  },
};
</script>

<style scoped>
.kakao_btn {
  cursor: pointer;
}
</style>
