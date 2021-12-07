<template>
  <b-container>
    <b-row>
      <b-col></b-col>
      <b-col cols="5">
        <b-card class="text-center mt-3">
          <template #header>
            <h3 v-if="userInfo == null">Sign Up</h3>
            <div v-else>
              <h3>Profile</h3>
              <b-avatar size="72px" variant="info"></b-avatar>
            </div>
          </template>
          <b-alert show v-if="user.platform !== 'none'"
            ><h4>{{ user.platform }}로 간편로그인한 계정입니다</h4></b-alert
          >
          <b-form @submit="onSubmit" style="text-align: left">
            <b-form-group id="userid-group" label="아이디" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                type="text"
                required
                placeholder="Enter Id"
                :readonly="userInfo != null"
                @keyup="isUsableId"
              ></b-form-input>
              <div class="mt-1" v-if="user.userid && !userInfo">
                <span v-if="isAvailable" style="color: green"
                  >{{ user.userid }}는 사용가능합니다.</span
                >
                <span v-else style="color: red"
                  >{{ user.userid }}는 사용할 수 없습니다.</span
                >
              </div>
            </b-form-group>

            <b-form-group
              id="userpwd-group"
              label="비밀번호"
              label-for="userpwd"
              v-if="user.platform === 'none'"
            >
              <b-form-input
                id="userpwd"
                v-model="user.userpwd"
                type="password"
                required
                placeholder="Enter password"
              ></b-form-input>
            </b-form-group>

            <b-form-group id="username-group" label="이름" label-for="username">
              <b-form-input
                id="username"
                v-model="user.username"
                type="text"
                required
                placeholder="Enter name"
              ></b-form-input>
            </b-form-group>

            <b-form-group id="email-group" label="이메일" label-for="email">
              <b-form-input
                id="email"
                v-model="user.email"
                type="email"
                required
                placeholder="Enter email"
              ></b-form-input>
            </b-form-group>

            <b-col class="text-right">
              <div v-if="userInfo != null">
                <b-button
                  class="mr-2"
                  type="button"
                  variant="danger"
                  @click="removeMember"
                  >탈퇴</b-button
                >
                <b-button type="submit" class="mr-2" variant="primary"
                  >수정</b-button
                >
                <b-button variant="success" @click="movePage"
                  >메인으로</b-button
                >
              </div>

              <div v-else>
                <b-button type="submit" variant="primary">가입하기</b-button>
              </div>
            </b-col>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import {
  registerMember,
  modifyMember,
  deleteMember,
  checkId,
} from "@/api/member.js";
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberWriteForm",
  data() {
    return {
      user: {
        user_no: null,
        userid: "",
        userpwd: null,
        username: null,
        email: null,
        platform: "none",
      },
      isAvailable: false, // 중복체크
    };
  },
  created() {
    if (this.userInfo != null) {
      this.user = this.userInfo;
      this.user.userpwd = null;
      console.log(this.user);
    }
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isLogin"]),
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      !this.user.userid &&
        ((msg = "아이디를 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      if (this.user.platform === "none") {
        err &&
          !this.user.userpwd &&
          ((msg = "비밀번호를 입력해주세요"),
          (err = false),
          this.$refs.userpwd.focus());
      }
      err &&
        !this.user.username &&
        ((msg = "이름을 입력해주세요"),
        (err = false),
        this.$refs.username.focus());
      err &&
        !this.user.email &&
        ((msg = "이메일을 입력해주세요"),
        (err = false),
        this.$refs.email.focus());

      if (!err) alert(msg);
      else this.userInfo ? this.updateMember() : this.createMember();
    },
    createMember() {
      registerMember(
        {
          userid: this.user.userid,
          userpwd: this.user.userpwd,
          username: this.user.username,
          email: this.user.email,
          platform: this.user.platform,
        },
        ({ data }) => {
          let msg = "회원가입 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "가입이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "Login" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateMember() {
      if (confirm("수정하시겠습니까?")) {
        modifyMember(
          {
            userid: this.user.userid,
            userpwd: this.user.userpwd,
            username: this.user.username,
            email: this.user.email,
          },
          ({ data }) => {
            let msg = "회원 정보 수정 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "수정이 완료되었습니다.";
            }
            alert(msg);
            this.$router.push({ name: "Main" });
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    removeMember() {
      if (confirm("정말 삭제하시겠습니까?")) {
        deleteMember(
          this.user.user_no,
          ({ data }) => {
            let msg = "탈퇴 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "탈퇴가 완료되었습니다.";
            }
            alert(msg);
            this.SET_IS_LOGIN(false);
            this.SET_USER_INFO(null);
            sessionStorage.removeItem("access-token");
            if (this.$route.path != "/") this.$router.push({ name: "Main" });
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    movePage() {
      this.$router.push({ name: "Main" });
    },
    isUsableId() {
      checkId(
        this.user.userid,
        ({ data }) => {
          if (data === "success") this.isAvailable = true;
          else this.isAvailable = false;
        },
        () => {}
      );
    },
  },
};
</script>

<style></style>
