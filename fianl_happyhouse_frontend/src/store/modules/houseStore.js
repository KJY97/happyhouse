import http from "@/util/http-common.js";
import axios from "axios";

// import createPersistedState from "vuex-persistedstate";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "도/광역시" }],
    guguns: [{ value: null, text: "시/군/구" }],
    dongs: [{ value: null, text: "동" }],
    conditions: [
      { value: null, text: "기본 설정" },
      { value: "rowPrice", text: "낮은 거래가 순" },
      { value: "highPrice", text: "높은 거래가 순" },
      { value: "recent", text: "최근 순" },
      { value: "old", text: "오래된 순" },
    ],
    years: [
      { value: "202110", text: "21년 10월" },
      { value: "202101", text: "21년 1월" },
      { value: "202102", text: "21년 2월" },
      { value: "202103", text: "21년 3월" },
      { value: "202104", text: "21년 4월" },
      { value: "202105", text: "21년 5월" },
      { value: "202106", text: "21년 6월" },
      { value: "202107", text: "21년 7월" },
      { value: "202108", text: "21년 8월" },
      { value: "202109", text: "21년 9월" },
    ],
    commerces: [],
    environment: null,
    nowGun: null,
    isHouse: null,
    ph: null,
    flag: false,
    houses: [],
    houseAddressList: [],
    houseAddress: {},
    nowSearchCondition: null,
  },
  mutations: {
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongName, text: dong.dongName });
      });
    },

    SET_HOUSE_LIST(state, houses) {
      // console.log(houses);

      houses.forEach((house) => {
        house.면적가격 = (
          Number(house.거래금액.replace(",", "").trim()) /
          Number(house.전용면적)
        ).toFixed(2);
      });
      state.houses = houses;
      // console.log("SET_HOUSE_LIST_1", state.houses);

      var addressInfoList = [];
      let i = 0;
      houses.forEach((house) => {
        // 도로명 건물번호(본번-부번)
        const road_name = house.도로명;
        const main_no = Number(house.도로명건물본번호코드);
        const sub_no = Number(house.도로명건물부번호코드);
        const aptName = house.아파트;

        addressInfoList[i] = {
          아파트명: aptName,
          도로명주소: `${road_name} ${main_no}-${sub_no}`,
        };
        i++;
      });

      state.houseAddressList = addressInfoList;
      //  console.log("SET_HOUSE_LIST_2", state.houseAddressList);
    },
    SET_FIND_HOUSE_ADDRESS(state, house) {
      const road_name = house.도로명;
      const main_no = Number(house.도로명건물본번호코드);
      const sub_no = Number(house.도로명건물부번호코드);
      const aptName = house.아파트;

      state.houseAddress = {
        아파트명: aptName,
        도로명주소: `${road_name} ${main_no}-${sub_no}`,
      };
      // console.log("address", state.houseAddress);
    },
    SET_COMMERCE_LIST(state, commerces) {
      console.log(commerces);
      state.commerces = commerces;
    },
    CLEAR_HOUSE_LIST: (state) => {
      state.houses = [];
    },
    CLEAR_SIDO_LIST: (state) => {
      state.environment = null;
      state.nowSearchCondition = null;
      state.nowGun = null;
      state.ph = null;
      state.isHouse = null;
      state.sidos = [{ value: null, text: "도/광역시" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.environment = null;
      state.nowSearchCondition = null;
      state.nowGun = null;
      state.ph = null;
      state.isHouse = null;
      state.guguns = [{ value: null, text: "시/군/구" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: null, text: "동" }];
    },
    CLEAR_COMMERCE_LIST: (state) => {
      state.commerces = [];
    },
  },
  actions: {
    getSido({ commit }) {
      http
        .get(`/house/sido`)
        .then((response) => {
          //console.log(response);
          commit("SET_SIDO_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getGugun({ commit }, sidoCode) {
      const params = {
        sido: sidoCode,
      };

      http
        .get(`/house/gugun`, { params: params })
        .then((response) => {
          //console.log(response);
          commit("SET_GUGUN_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getDong({ commit, state }, gugunCode) {
      const params = {
        gugun: gugunCode,
      };

      var gugun = "";
      var degree = null;

      state.guguns.forEach((gugunValue) => {
        if (gugunValue.value == gugunCode) gugun = gugunValue.text;
      });

      state.nowGun = gugun;
      axios
        .get(
          "http://openapi.seoul.go.kr:8088/6d4d776b466c656533356a4b4b5872/json/RealtimeCityAir/1/99"
        )
        .then((response) => {
          // console.log(response.data.RealtimeCityAir.row);

          response.data.RealtimeCityAir.row.forEach((airValue) => {
            if (gugun == airValue.MSRSTE_NM) {
              state.ph = airValue.PM10;
              degree = airValue.PM10;
            }
          });

          if (degree <= 15) state.environment = "좋음😀";
          else if (15 < degree && degree <= 50) state.environment = "보통😐";
          else if (51 < degree && degree <= 100) state.environment = "나쁨😥";
          else if (degree > 100) state.environment = "매우나쁨😱";
        })
        .catch((error) => {
          console.log(error);
        });

      http
        .get(`/house/dong`, { params: params })
        .then((response) => {
          // console.log(response);
          commit("SET_DONG_LIST", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getHouseList({ commit, state }, data) {
      state.nowSearchCondition = data;
      state.flag = true;
      var dong = data.dong;
      var gugun = null;
      var housesList = [];
      const SERVICE_KEY =
        "h8q7IInaaPB9ecxzyzhV35ZW87ZrErM%2BT09IPK%2B38YUUIL8F3XewhIsROYIxAbKRWg8cM8udg3HT3N9HCYAhCA%3D%3D";
      const APT_DEAL_URL =
        "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";

      const params = {
        LAWD_CD: data.gugun,
        DEAL_YMD: "202110",
        pageNo: 1,
        numOfRows: 30,
        serviceKey: decodeURIComponent(SERVICE_KEY),
      };
      // console.log(params);
      await http
        .get(APT_DEAL_URL, { params: params })
        .then((response) => {
          // console.log(response.data.response.body.items.item);
          response.data.response.body.items.item.forEach((houseValue) => {
            if (houseValue.법정동.trim() == dong) {
              housesList.push(houseValue);
            }
          });

          if (housesList.length < 1) {
            housesList = response.data.response.body.items.item;

            state.guguns.forEach((gugunValue) => {
              if (gugunValue.value == data.gugun) gugun = gugunValue.text;
            });

            state.isHouse = gugun;
          } else state.isHouse = "조회성공";
          commit("SET_HOUSE_LIST", housesList);
          // console.log(this.state.houses);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getCommerceList({ commit, state }, data) {
      const SERVICE_KEY =
        "h8q7IInaaPB9ecxzyzhV35ZW87ZrErM%2BT09IPK%2B38YUUIL8F3XewhIsROYIxAbKRWg8cM8udg3HT3N9HCYAhCA%3D%3D";
      const COMMERCE_URL =
        "http://apis.data.go.kr/B553077/api/open/sdsc2/storeZoneInAdmi";

      const params = {
        divId: data.divId,
        key: data.key,
        serviceKey: decodeURIComponent(SERVICE_KEY),
        type: "json",
      };

      state.guguns.forEach((gugunValue) => {
        if (gugunValue.value == data.key) state.nowGun = gugunValue.text;
      });

      await http
        .get(COMMERCE_URL, { params: params })
        .then((response) => {
          // console.log(response.data.body.items);
          commit("SET_COMMERCE_LIST", response.data.body.items);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    sortListByCondition({ commit, state }, condition) {
      // console.log("sortListByCondition");
      var tempList = state.houses;

      // 아파트 이름으로 정렬
      if (condition == null) {
        tempList = tempList.sort(function (a, b) {
          if (a.아파트 > b.아파트) return 1;
          if (a.아파트 < b.아파트) return -1;
          return 0;
        });
      } // 가격 오름차순
      else if (condition == "rowPrice") {
        tempList = tempList.sort(function (a, b) {
          if (Number(a.면적가격) > Number(b.면적가격)) return 1;
          if (Number(a.면적가격) < Number(b.면적가격)) return -1;
          return 0;
        });
      } // 가격 내림차순
      else if (condition == "highPrice") {
        tempList = tempList.sort(function (a, b) {
          if (Number(a.면적가격) < Number(b.면적가격)) return 1;
          if (Number(a.면적가격) > Number(b.면적가격)) return -1;
          return 0;
        });
      } else if (condition == "recent") {
        tempList = tempList.sort(function (a, b) {
          var aMonthTemp, bMonthTemp, aDayTemp, bDayTemp;
          aMonthTemp = a.월.length == 1 ? "0" + a.월 : a.월;
          aDayTemp = a.일.length == 1 ? "0" + a.일 : a.일;
          bMonthTemp = b.월.length == 1 ? "0" + b.월 : b.월;
          bDayTemp = b.일.length == 1 ? "0" + b.일 : b.일;

          var aDate = a.년 + aMonthTemp + aDayTemp;
          var bDate = b.년 + bMonthTemp + bDayTemp;

          if (Number(aDate) < Number(bDate)) return 1;
          if (Number(aDate) > Number(bDate)) return -1;
          return 0;
        });
      } else if (condition == "old") {
        tempList = tempList.sort(function (a, b) {
          var aMonthTemp, bMonthTemp, aDayTemp, bDayTemp;
          aMonthTemp = a.월.length == 1 ? "0" + a.월 : a.월;
          aDayTemp = a.일.length == 1 ? "0" + a.일 : a.일;
          bMonthTemp = b.월.length == 1 ? "0" + b.월 : b.월;
          bDayTemp = b.일.length == 1 ? "0" + b.일 : b.일;

          var aDate = a.년 + aMonthTemp + aDayTemp;
          var bDate = b.년 + bMonthTemp + bDayTemp;

          if (Number(aDate) > Number(bDate)) return 1;
          if (Number(aDate) < Number(bDate)) return -1;
          return 0;
        });
      }

      commit("SET_HOUSE_LIST", tempList);
    },
    async changeListByDate({ commit, state }, year) {
      // console.log("changeListByDate", state.nowSearchCondition);
      state.flag = false;
      var dong = state.nowSearchCondition.dong;
      var gugun = null;
      var housesList = [];
      const SERVICE_KEY =
        "h8q7IInaaPB9ecxzyzhV35ZW87ZrErM%2BT09IPK%2B38YUUIL8F3XewhIsROYIxAbKRWg8cM8udg3HT3N9HCYAhCA%3D%3D";
      const APT_DEAL_URL =
        "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";

      const params = {
        LAWD_CD: state.nowSearchCondition.gugun,
        DEAL_YMD: year,
        pageNo: 1,
        numOfRows: 30,
        serviceKey: decodeURIComponent(SERVICE_KEY),
      };
      // console.log(params);
      await http
        .get(APT_DEAL_URL, { params: params })
        .then((response) => {
          // console.log(response.data.response.body.items.item);
          response.data.response.body.items.item.forEach((houseValue) => {
            if (houseValue.법정동.trim() == dong) {
              housesList.push(houseValue);
            }
          });

          if (housesList.length < 1) {
            housesList = response.data.response.body.items.item;

            state.guguns.forEach((gugunValue) => {
              if (gugunValue.value == state.nowSearchCondition.gugun)
                gugun = gugunValue.text;
            });

            state.isHouse = gugun;
          } else state.isHouse = "조회성공";
          commit("SET_HOUSE_LIST", housesList);
          // console.log(this.state.houses);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },

  modules: {},
  //  plugins: [createPersistedState()],
};

export default houseStore;
