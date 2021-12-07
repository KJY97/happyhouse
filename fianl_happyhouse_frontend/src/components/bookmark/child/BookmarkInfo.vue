<template>
  <!-- 거래-->
  <div>
    <div>
      <span>
        <h3>나의 즐겨찾기</h3>

        <div class="mb-2 mt-3" v-if="bookmarks.length != 0">
          <span class="ml-1">
            정렬 기준 :&nbsp;
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
      v-if="bookmarks.length == 0"
      class="card mb-4 p-3 mt-3"
      style="font-size: 25px"
    >
      즐겨찾기에 추가하신 내역이 없어요!<br />
      관심 매물을 추가해주세요 😀
    </div>

    <div v-else class="card mb-4 p-3 mt-3">
      <div v-for="p in paginatedData" :key="p.index">
        <div @click="findMarker(p)">
          <div>
            <h5 style="color: blue">
              {{ p.aptName }}

              <b-button
                class="bg-danger float-right mr-2"
                size="sm"
                @click="deleteBookMark(p)"
              >
                삭제</b-button
              >
            </h5>
          </div>
          <h5>거래 금액 : {{ p.dealAmount }}만원</h5>
          <h5
            v-if="
              sortingCondition == 'rowPrice' || sortingCondition == 'highPrice'
            "
          >
            단위 금액 : 1㎡당
            <span class="text-danger">{{ p.unitAmount }}</span
            >만원 👈
          </h5>
          <h5>전용 면적 : {{ p.area }}㎡</h5>
          <h5>법정동 : {{ p.dong }}</h5>
          <h5 v-if="sortingCondition == 'recent' || sortingCondition == 'old'">
            거래 날짜 :
            <span class="text-danger"
              >{{ p.dealYear }}.{{ p.dealMonth }}.{{ p.dealDay }}</span
            >
            👈
          </h5>
          <h5 v-else>
            거래 날짜 : {{ p.dealYear }}.{{ p.dealMonth }}.{{ p.dealDay }}
          </h5>
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

const memberStore = "memberStore";
export default {
  data() {
    return {
      sortingCondition: null,
      pageNum: 0,
      pageSize: 2,
    };
  },
  created() {
    this.getUserBookmarks();
  },
  methods: {
    ...mapActions(memberStore, ["sortListByCondition", "getUserBookmarks"]),
    ...mapMutations(memberStore, ["SET_FIND_HOUSE_ADDRESS"]),
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    initStatement() {
      this.pageNum = this.bookmarks.length * 0;
    },
    sortList() {
      this.sortListByCondition(this.sortingCondition);
    },
    findMarker(house) {
      this.SET_FIND_HOUSE_ADDRESS(house);
    },
    deleteBookMark(p) {
      if (confirm(`즐겨찾기에서 ${p.aptName}을(를) 삭제하시겠어요?`)) {
        http
          .delete(`/bookmark/${p.no}/${this.userInfo.user_no}`)
          .then(({ data }) => {
            if (data == "success")
              alert(`${p.aptName}가 즐겨찾기에서 삭제되었습니다 :)`);
            this.getUserBookmarks();
          });
      }
    },
  },
  computed: {
    ...mapState(memberStore, ["bookmarks", "userinfo", "conditions"]),
    ...mapState(memberStore, ["userInfo"]),
    pageCount() {
      // console.log("HouseInfo pageCount");
      this.initStatement();
      if (this.bookmarks.length != 0) this.sortList();
      let listLeng = this.bookmarks.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
        end = start + this.pageSize;
      return Object.values(this.bookmarks).slice(start, end);
    },
  },
};
</script>

<style></style>
