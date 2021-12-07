<template>
  <!-- Side widgets-->
  <div class="col-lg-8">
    <!-- Search widget-->
    <div class="row">
      <div style="font-size: 30px" v-if="nowGun == null">검색 결과</div>
      <div style="font-size: 30px" v-else>{{ nowGun }}의 주변 상권</div>
    </div>

    <!-- 상권정보 결과 -->
    <b-table-simple hover responsive>
      <b-thead head-variant="dark">
        <b-tr>
          <b-td>시도명</b-td>
          <b-td>시군구명</b-td>
          <b-td>상권명</b-td>
          <b-td>면적</b-td>
          <b-td>데이터기준일자</b-td>
        </b-tr>
      </b-thead>

      <tbody v-if="commerces.length">
        <b-tr v-for="p in paginatedData" :key="p.index">
          <b-td>{{ p.ctprvnNm }}</b-td>
          <b-td>{{ p.signguNm }}</b-td>
          <b-td class="text-success">{{ p.mainTrarNm }}</b-td>
          <b-td>{{ p.trarArea }} ㎡</b-td>
          <b-td>{{ p.stdrDt }}</b-td>
        </b-tr>
      </tbody>
    </b-table-simple>
    <h4 class="text-center" v-if="commerces.length < 1 && nowGun == null">
      이곳에 세부 내용을 안내해드릴게요 :)
    </h4>
    <h4 class="text-center" v-else-if="commerces.length < 1 && nowGun != null">
      조회 결과가 없어요 😥
    </h4>
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
import { mapState } from "vuex";

const houseStore = "houseStore";
export default {
  name: "CommerceDetail",
  data() {
    return {
      pageNum: 0,
      pageSize: 8,
    };
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    initNum() {
      this.pageNum = this.commerces.length * 0;
    },
  },
  computed: {
    ...mapState(houseStore, ["commerces", "nowGun"]), // commerces가 갱신될 때 pageNum 초기화
    pageCount() {
      this.initNum();
      let listLeng = this.commerces.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      // 상업시설 배열에서 몇개를 자를지
      const start = this.pageNum * this.pageSize, // 2 * 8 = 16부터 ~ 24까지
        end = start + this.pageSize;
      return Object.values(this.commerces).slice(start, end);
    },
  },
};
</script>

<style></style>
