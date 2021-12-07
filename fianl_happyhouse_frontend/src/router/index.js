import Vue from "vue";
import VueRouter from "vue-router";
import Main from "@/views/Main.vue";
import Board from "@/views/Board.vue";
import Bookmark from "@/views/Bookmark.vue";

import BoardTable from "@/components/board/BoardTable.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import BoardView from "@/components/board/BoardView.vue";
import BoardUpdate from "@/components/board/BoardUpdate.vue";

import Join from "@/views/Join.vue";
import Login from "@/views/Login.vue";
import MyPage from "@/views/MyPage.vue";
import Commerce from "@/views/Commerce.vue";
import store from "@/store/index.js";

import KakaoLogin from "@/components/member/login/KakaoLogin.vue"

import Notice from "@/views/Notice.vue";
import NoticeList from "@/components/notice/NoticeList.vue";
import NoticeWrite from "@/components/notice/NoticeWrite.vue";
import NoticeUpdate from "@/components/notice/NoticeUpdate.vue";
import NoticeView from "@/components/notice/NoticeView.vue";

Vue.use(VueRouter);

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다 :)");
    router.push({ name: "Login" });
  } else {
    console.log("로그인했습니다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/bookmark",
    name: "Bookmark",
    beforeEnter: onlyAuthUser,
    component: Bookmark,
  },
  {
    path: "/commerce",
    name: "Commerce",
    component: Commerce,
  },
  {
    path: "/board",
    name: "Board",
    component: Board,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "BoardTable",
        component: BoardTable,
      },
      {
        path: "write",
        name: "BoardWrite",
        beforeEnter: onlyAuthUser,
        component: BoardWrite,
      },
      {
        path: "detail/:question_no",
        name: "BoardView",
        component: BoardView,
      },
      {
        path: "modify/:question_no",
        name: "BoardUpdate",
        beforeEnter: onlyAuthUser,
        component: BoardUpdate,
      },
    ],
  },
  {
    path: "/join",
    name: "Join",
    component: Join,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/login/auth",
    name: "KakaoLogin",
    component: KakaoLogin,
  },
  {
    path: "/mypage",
    name: "MyPage",
    beforeEnter: onlyAuthUser,
    component: MyPage,
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        name: "NoticeList",
        component: NoticeList,
      },
      {
        path: "write",
        name: "NoticeWrite",
        beforeEnter: onlyAuthUser,
        component: NoticeWrite,
      },
      {
        path: "detail/:notice_no",
        name: "NoticeView",
        component: NoticeView,
      },
      {
        path: "modify/:notice_no",
        name: "NoticeUpdate",
        beforeEnter: onlyAuthUser,
        component: NoticeUpdate,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
