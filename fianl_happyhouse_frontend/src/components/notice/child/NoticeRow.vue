<template>
  <div>
    <b-table-simple hover responsive>
      <b-thead head-variant="dark">
        <b-tr class="text-center">
          <b-td>글번호</b-td>
          <b-td>제목</b-td>
          <b-td>작성자</b-td>
          <b-td>작성일</b-td>
        </b-tr>
      </b-thead>

      <tbody v-if="noticeList.length > 0" class="text-center">
        <b-tr v-for="check in YNcheckData" :key="check.index">
          <b-td class="text-danger">공지</b-td>
          <b-td class="text-left">
            <router-link
            style=text-decoration:none;
              class="text-success"
              :to="{
                name: 'NoticeView',
                params: { notice_no: check.notice_no },
              }"
              >{{ check.title }}</router-link
            >
          </b-td>
          <b-td>{{ check.username }}</b-td>
          <b-td>{{ check.regtime }}</b-td>
        </b-tr>

        <b-tr v-for="p in paginatedData" :key="p.index">
          <b-td>{{ p.notice_no }}</b-td>
          <b-td class="text-left">
            <router-link
            style=text-decoration:none;
              class="text-dark"
              :to="{
                name: 'NoticeView',
                params: { notice_no: p.notice_no },
              }"
              >{{ p.title }}</router-link
            >
          </b-td>
          <b-td>{{ p.username }}</b-td>
          <b-td>{{ p.regtime }}</b-td>
        </b-tr>
      </tbody>
    </b-table-simple>

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
export default {
  name: "NoticeRow",
  data() {
    return {
      pageNum: 0,
    };
  },
  props: {
    noticeList: {
      type: Array,
      required: true,
    },
    pageSize: {
      type: Number,
      required: false,
      default: 10,
    },
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
  },
  computed: {
    pageCount() {
      let listLeng = this.noticeList.length - this.YNcheckData.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      /*
      아니면 page = Math.floor((listLeng - 1) / listSize) + 1;
      이런식으로 if 문 없이 고칠 수도 있다!
      */
      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
        end = start + this.pageSize;

      return this.noticeList
        .filter((notice) => notice.checkYN == "N")
        .slice(start, end);
    },
    YNcheckData() {
      return this.noticeList.filter((notice) => notice.checkYN == "Y");
    },
  },
};
</script>

<style></style>
