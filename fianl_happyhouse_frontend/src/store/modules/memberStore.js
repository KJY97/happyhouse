import jwt_decode from "jwt-decode";
import { login, findById } from "@/api/member.js";
// import { REDIRECT_URI } from "@/config/index";
import http from "@/util/http-common";
// import axios from "axios";

const memberStore = {
  namespaced: true, // 모듈을 독립적으로 재사용한다
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    bookmarks: [],
    conditions: [
      { value: null, text: "기본 설정" },
      { value: "rowPrice", text: "낮은 거래가 순" },
      { value: "highPrice", text: "높은 거래가 순" },
      { value: "recent", text: "최근 순" },
      { value: "old", text: "오래된 순" },
    ],
    houseAddressList: [],
    houseAddress: [],
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true; // 세션으로 로그인한 경우
      state.userInfo = userInfo;
    },
    SET_BOOKMARKS_LIST: (state, houses) => {
      state.bookmarks = houses;

      var addressInfoList = [];
      let i = 0;
      houses.forEach((house) => {
        // 도로명 건물번호(본번-부번)
        const road_name = house.roadName;
        const main_no = Number(house.roadMain);
        const sub_no = Number(house.roadSub);
        const aptName = house.aptName;

        addressInfoList[i] = {
          아파트명: aptName,
          도로명주소: `${road_name} ${main_no}-${sub_no}`,
        };
        i++;
      });

      state.houseAddressList = addressInfoList;
    },
    SET_FIND_HOUSE_ADDRESS(state, house) {
      const road_name = house.roadName;
      const main_no = Number(house.roadMain);
      const sub_no = Number(house.roadSub);
      const aptName = house.aptName;

      state.houseAddress = {
        아파트명: aptName,
        도로명주소: `${road_name} ${main_no}-${sub_no}`,
      };
      // console.log("address", state.houseAddress);
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    //${state.userinfo.userid}
    async getUserBookmarks({ commit, state }) {
      await http
        .get(`/bookmark/${state.userInfo.user_no}`)
        .then((response) => {
          commit("SET_BOOKMARKS_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      console.log(decode_token); // token 확인
      findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    sortListByCondition({ commit, state }, condition) {
      // console.log("sortListByCondition");
      var tempList = state.bookmarks;

      // 아파트 이름으로 정렬
      if (condition == null) {
        tempList = tempList.sort(function (a, b) {
          if (a.aptName > b.aptName) return 1;
          if (a.aptName < b.aptName) return -1;
          return 0;
        });
      } // 가격 오름차순
      else if (condition == "rowPrice") {
        tempList = tempList.sort(function (a, b) {
          if (Number(a.unitAmount) > Number(b.unitAmount)) return 1;
          if (Number(a.unitAmount) < Number(b.unitAmount)) return -1;
          return 0;
        });
      } // 가격 내림차순
      else if (condition == "highPrice") {
        tempList = tempList.sort(function (a, b) {
          if (Number(a.unitAmount) < Number(b.unitAmount)) return 1;
          if (Number(a.unitAmount) > Number(b.unitAmount)) return -1;
          return 0;
        });
      } else if (condition == "recent") {
        tempList = tempList.sort(function (a, b) {
          var aMonthTemp, bMonthTemp, aDayTemp, bDayTemp;
          aMonthTemp =
            a.dealMonth.length == 1 ? "0" + a.dealMonth : a.dealMonth;
          aDayTemp = a.dealDay.length == 1 ? "0" + a.dealDay : a.dealDay;
          bMonthTemp =
            b.dealMonth.length == 1 ? "0" + b.dealMonth : b.dealMonth;
          bDayTemp = b.dealDay.length == 1 ? "0" + b.dealDay : b.dealDay;

          var aDate = a.dealYear + aMonthTemp + aDayTemp;
          var bDate = b.dealYear + bMonthTemp + bDayTemp;

          if (Number(aDate) < Number(bDate)) return 1;
          if (Number(aDate) > Number(bDate)) return -1;
          return 0;
        });
      } else if (condition == "old") {
        tempList = tempList.sort(function (a, b) {
          var aMonthTemp, bMonthTemp, aDayTemp, bDayTemp;

          aMonthTemp =
            a.dealMonth.length == 1 ? "0" + a.dealMonth : a.dealMonth;
          aDayTemp = a.dealDay.length == 1 ? "0" + a.dealDay : a.dealDay;
          bMonthTemp =
            b.dealMonth.length == 1 ? "0" + b.dealMonth : b.dealMonth;
          bDayTemp = b.dealDay.length == 1 ? "0" + b.dealDay : b.dealDay;

          var aDate = a.dealYear + aMonthTemp + aDayTemp;
          var bDate = b.dealYear + bMonthTemp + bDayTemp;

          if (Number(aDate) > Number(bDate)) return 1;
          if (Number(aDate) < Number(bDate)) return -1;
          return 0;
        });
      }

      commit("SET_BOOKMARKS_LIST", tempList);
    },

    // ====== kakao login =======
    async getKakaoLoginToken({ commit }, AUTHORIZE_CODE) {
      console.log("getKakaoLogin! Hello");
      await http
        .get(`/login/auth?code=${AUTHORIZE_CODE}`)
        .then((response) => {
          console.log(response);
          if (response.data.message === "success") {
            let NewToken = response.data["access-token"]; // 서버에서 새로 발급한 토큰이 넘어온다
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", NewToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};

export default memberStore;
