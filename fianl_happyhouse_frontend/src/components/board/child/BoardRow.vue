<template>
  <div>
    <b-table-simple hover responsive>
      <b-thead head-variant="dark">
        <b-tr class="text-center">
          <b-td>글번호</b-td>
          <b-td>제목</b-td>
          <b-td>작성자</b-td>
          <b-td>작성일</b-td>
          <b-td>답변상태</b-td>
        </b-tr>
      </b-thead>

      <tbody class="text-center" v-if="articles.length">
        <b-tr v-for="p in paginatedData" :key="p.index">
          <b-td>{{ p.question_no }}</b-td>
          <b-td class="text-left">
            <router-link
              style="text-decoration: none"
              class="text-dark"
              :to="{
                name: 'BoardView',
                params: { question_no: p.question_no },
              }"
              >{{ p.title }}</router-link
            >
          </b-td>
          <b-td>{{ p.username }}</b-td>
          <b-td>{{ p.regtime }}</b-td>
          <b-td class="text-dark" v-if="p.answer_state === 'N'">대기중</b-td>
          <b-td class="text-danger" v-else>답변완료</b-td>
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
  name: "BoardRow",
  data() {
    return {
      pageNum: 0,
    };
  },
  props: {
    articles: {
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
    initNum() {
      this.pageNum = this.articles.length * 0;
    },
  },
  computed: {
    pageCount() {
      this.initNum();
      let listLeng = this.articles.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
        end = start + this.pageSize;
      return this.articles.slice(start, end);
    },
  },
};
</script>

<style>
router-link {
  color: #42b983;
}
</style>
