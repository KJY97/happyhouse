<template>
  <div>
  </div>
</template>

<script>
import { getKakaoToken, getKakaoUserInfo } from "@/api/login";
import { mapMutations } from 'vuex';
export default {
  name: "Auth",
  created() {
    if(this.$route.query.code) {
      this.setKakaoToken();
    }
  },
  methods: {
    ...mapMutations("memberStore", ["SET_USER_INFO"]),
    // 토큰 
    async setKakaoToken() {
      console.log("카카오 인증 코드", this.$route.query.code);
      const { data } = await getKakaoToken(this.$route.query.code);
      if (data.error) {
        alert("카카오톡 로그인 오류입니다.");
        this.$router.replace("/login");
        return;
      }
      window.Kakao.Auth.setAccessToken(data.access_token); // 토큰 할당
      sessionStorage.setItem("access-token", data.access_token);
      await this.setUserInfo();
      this.$router.replace("/");
    },
    async setUserInfo() {
      const res = await getKakaoUserInfo();
      // console.log(res);
      const userInfo = {
        userid: res.kakao_account.email,
        userpwd: null,
        username: res.kakao_account.profile.nickname,
        email: res.kakao_account.email,
        platform: "kakao",
      };
      // ("userConfirm", userInfo)
      // let token = sessionStorage.getItem("access-token");
      // this.$store.state.userInfo = userInfo;
      this.SET_USER_INFO(userInfo);
      // await this.getUserInfo(token);
      this.$router.push({ name: "Main" });
    },
  },
};
</script>

<style></style>
