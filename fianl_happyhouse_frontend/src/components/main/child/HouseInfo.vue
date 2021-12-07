<template>
  <!-- 거래-->
  <div>
    <div>
      <span>
        <h3 v-if="houses.length == 0 && isHouse == null">거래 정보</h3>
        <h3 v-else-if="isHouse == '조회성공'">
          <span class="text-primary"> {{ houses[0].법정동 }} </span>거래정보
        </h3>
        <h5 v-else>
          <span class="text-danger">!! </span>조회하신 동의
          <span class="text-danger">거래정보</span>가 없어요 😂
          <br />
          &nbsp;&nbsp;&nbsp; 대신 조회하셨던
          <span class="text-success">{{ isHouse }}</span> 정보 보여드릴게요 :)
          <span class="text-danger">!! </span>
        </h5>
        <div class="mb-2 mt-3" v-if="houses.length != 0">
          <span class="text-left ml-4">
            날짜 :
            <b-form-select
              class="col-3 r-5 mr-5"
              v-model="year"
              :options="years"
              @change="listByDate"
            ></b-form-select>
          </span>
          <span>
            기준 :&nbsp;
            <b-form-select
              class="col-4 r-5"
              v-model="sortingCondition"
              :options="conditions"
              @change="sortList"
            ></b-form-select>
          </span>
        </div>
      </span>
      <span id="info"></span>
    </div>

    <div
      v-if="houses.length == 0"
      class="card mb-4 p-3 mt-3"
      style="font-size: 25px"
    >
      검색한 내역이 없어요 😢<br />
      조회하실 지역을 선택해주세요!
    </div>

    <div v-else class="card mb-4 p-3 mt-3">
      <div v-for="p in paginatedData" :key="p.index">
        <div @click="findMarker(p)">
          <div>
            <h5 style="color: blue">
              {{ p.아파트 }}

              <b-button
                class="bg-dark float-right mr-2"
                size="sm"
                @click="addBookmark(p)"
                >🤍</b-button
              >
            </h5>
          </div>
          <h5>거래 금액 : {{ p.거래금액 }}만원</h5>
          <h5
            v-if="
              sortingCondition == 'rowPrice' || sortingCondition == 'highPrice'
            "
          >
            단위 금액 : 1㎡당
            <span class="text-danger">{{ p.면적가격 }}</span
            >만원 👈
          </h5>
          <h5>전용 면적 : {{ p.전용면적 }}㎡</h5>
          <h5>법정동 : {{ p.법정동 }}</h5>
          <h5 v-if="sortingCondition == 'recent' || sortingCondition == 'old'">
            거래 날짜 :
            <span class="text-danger">{{ p.년 }}.{{ p.월 }}.{{ p.일 }}</span>
            👈
          </h5>
          <h5 v-else>거래 날짜 : {{ p.년 }}.{{ p.월 }}.{{ p.일 }}</h5>
          <h6>{{ p.dong }}</h6>
        </div>
        <hr v-if="paginatedData.length > 1" />
      </div>
    </div>

    <div class="btn-cover text-center mt-1">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">
        이전
      </button>
      <span class="page-count"
        >&nbsp;&nbsp;{{ pageNum + 1 }} /
        {{ pageCount }} 페이지&nbsp;&nbsp;</span
      >
      <button
        :disabled="pageNum >= pageCount - 1"
        @click="nextPage"
        class="page-btn"
      >
        다음
      </button>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import http from "@/util/http-common";

const houseStore = "houseStore";
const memberStore = "memberStore";
export default {
  name: "HouseInfo",
  data() {
    return {
      sortingCondition: null,
      year: "202110",
      pageNum: 0,
      pageSize: 2,
    };
  },
  created() {},
  methods: {
    ...mapActions(houseStore, [
      "sortListByCondition",
      "changeListByDate",
      "findMyBookMark",
    ]),
    ...mapMutations(houseStore, ["SET_FIND_HOUSE_ADDRESS"]),
    ...mapActions(memberStore, ["getUserBookmarks"]),
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    initStatement() {
      this.pageNum = this.houses.length * 0;
    },
    sortList() {
      this.sortListByCondition(this.sortingCondition);
    },
    listByDate() {
      // console.log("HouseInfo listByDate");
      this.changeListByDate(this.year);
    },
    setYear() {
      if (this.flag) this.year = "202110";
    },
    findMarker(house) {
      this.SET_FIND_HOUSE_ADDRESS(house);
    },
    async addBookmark(p) {
      if (this.userInfo == null) alert("로그인이 필요합니다.");
      else {
        // console.log(this.userInfo);
        await this.getUserBookmarks();

        if (confirm("즐겨찾기에 추가하시겠어요?")) {
          var isPossible = true;

          this.bookmarks.forEach((bookmark) => {
            if (
              bookmark.dealAmount == p.거래금액 &&
              bookmark.aptName == p.아파트 &&
              bookmark.area == p.전용면적 &&
              bookmark.dealMonth == p.월 &&
              bookmark.dealYear == p.년 &&
              bookmark.dealDay == p.일
            ) {
              isPossible = false;
              return false;
            }
          });

          // 중복되지 않는다면 추가
          if (isPossible) {
            var bookmarkValue = {
              aptName: p.아파트,
              area: p.전용면적,
              code: p.지역코드,
              dealAmount: p.거래금액,
              unitAmount: (
                Number(p.거래금액.replace(",", "").trim()) / Number(p.전용면적)
              ).toFixed(2),
              dealDay: p.일,
              dealMonth: p.월,
              dealYear: p.년,
              dong: p.법정동,
              roadName: p.도로명,
              roadMain: p.도로명건물본번호코드,
              roadSub: p.도로명건물부번호코드,
              user_no: this.userInfo.user_no,
            };

            http
              .post(`/bookmark`, JSON.stringify(bookmarkValue))
              .then(({ data }) => {
                if (data == "success") {
                  alert(`${p.아파트}(이)가 즐겨찾기에 추가되었습니다 :)`);
                  this.getUserBookmarks();
                }
              })
              .catch((error) => {
                console.log(error);
                alert("즐겨찾기 추가에 실패하였습니다.");
              });
          } else {
            alert(`${p.아파트}(은)는 이미 즐겨찾기에 존재합니다.`);
          }
        }
      }
    },
  },
  computed: {
    ...mapState(houseStore, [
      "houses",
      "isHouse",
      "conditions",
      "years",
      "flag",
    ]),
    ...mapState(memberStore, ["userInfo", "bookmarks"]),
    pageCount() {
      // console.log("HouseInfo pageCount");
      this.initStatement();
      if (this.houses.length != 0 && this.isHouse != null) this.sortList();
      this.setYear();
      let listLeng = this.houses.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
        end = start + this.pageSize;
      return Object.values(this.houses).slice(start, end);
    },
  },
};
</script>

<style></style>
