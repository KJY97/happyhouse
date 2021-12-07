<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href="/">HAPPY HOUSE</a>

        <!-- 메뉴 -->
        <div
          class="collapse navbar-collapse justify-content-end"
          id="navbarSupportedContent"
        >
          <!-- 로그인 전 메뉴 시작 -->
          <ul v-if="!userInfo" class="navbar-nav ms-auto mb-2 mb-lg-0">
            <!-- 로그인 -->
            <li class="nav-item">
              <router-link
                :to="{ name: 'Login' }"
                data-toggle="dropdown"
                class="nav-link active"
                style="border: 0; background-color: transparent"
              >
                로그인
              </router-link>
            </li>

            <!-- 비밀번호 찾기
            <button
              type="button"
              data-toggle="dropdown"
              class="nav-link active"
              style="border: 0; background-color: transparent"
            >
              비밀번호 찾기
            </button> -->

            <!-- 회원가입-->
            <li class="nav-item">
              <router-link
                :to="{ name: 'Join' }"
                data-toggle="dropdown"
                class="nav-link active"
                style="border: 0; background-color: transparent"
              >
                회원가입
              </router-link>
            </li>
          </ul>
          <!-- 로그인 전 메뉴 끝 -->

          <!-- 로그인 후 메뉴 시작 -->
          <ul v-else class="navbar-nav ms-auto mb-2 mb-lg-0">
            <!-- 로그아웃 -->
            <button
              type="button"
              data-toggle="dropdown"
              class="nav-link active"
              style="border: 0; background-color: transparent"
              @click.prevent="onLogout"
            >
              로그아웃
            </button>

            <!-- 마이페이지 -->
            <li class="nav-item">
              <router-link
                :to="{ name: 'MyPage' }"
                data-toggle="dropdown"
                class="nav-link active"
                style="border: 0; background-color: transparent"
                :user="userInfo"
              >
                마이페이지
              </router-link>
            </li>
          </ul>
          <!-- 로그인 후 메뉴 끝 -->
        </div>
        <!-- 아래는 필수 -->
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
          <!-- QnA게시판-->
          <li class="nav-item">
            <router-link
              :to="{ name: 'Board' }"
              data-toggle="dropdown"
              class="nav-link active"
              style="border: 0; background-color: transparent"
            >
              QnA게시판
            </router-link>
          </li>
          <!-- 공지사항-->
          <li class="nav-item">
            <router-link
              :to="{ name: 'Notice' }"
              data-toggle="dropdown"
              class="nav-link active"
              style="border: 0; background-color: transparent"
            >
              공지사항
            </router-link>
          </li>
          <!-- 상권 탐색-->
          <li class="nav-item">
            <router-link
              style="text-decoration: none"
              :to="{ name: 'Commerce' }"
            >
              <button
                type="button"
                data-toggle="dropdown"
                class="nav-link active"
                style="border: 0; background-color: transparent"
              >
                상권 탐색
              </button>
            </router-link>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "bar",
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "Main" });
    },
  },
};
</script>

<style></style>
