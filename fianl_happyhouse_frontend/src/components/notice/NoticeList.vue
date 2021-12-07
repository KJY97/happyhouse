<template>
  <div>
    <b-container class="bv-example-row mt-2">
      <!-- <b-row class="mb-1"> </b-row> -->
      <b-row>
        <b-col>
          <b-alert variant="dark" show><h4>공지사항 목록</h4></b-alert>
        </b-col>
      </b-row>

      <b-row>
        <b-col class="text-right">
          <b-button
            class="mb-3"
            v-if="checkUserInfo != null && checkUserInfo.user_no === 1"
            variant="dark"
            @click="moveWrite()"
            >작성하기</b-button
          >
        </b-col>
      </b-row>
      <b-row>
        <notice-row :noticeList="noticeList" />

        <h2 v-if="noticeList.length"></h2>
        <h2 v-else class="text-center mt-3">작성된 공지사항이 없습니다. :)</h2>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import NoticeRow from "@/components/notice/child/NoticeRow.vue";
import { mapState, mapActions, mapGetters } from "vuex";

const noticeStore = "noticeStore";
const memberStore = "memberStore";

export default {
  name: "NoticeTable",
  components: {
    NoticeRow,
  },
  data() {
    return {
      // user_no: 1 = admin
    };
  },
  created() {
    this.getNoticeList();
  },
  computed: {
    ...mapState(noticeStore, ["noticeList"]),
    ...mapGetters(memberStore, ["checkUserInfo"]),
  },
  methods: {
    ...mapActions(noticeStore, ["getNoticeList"]),
    moveWrite() {
      this.$router.push({ name: "NoticeWrite" });
    },
  },
};
</script>

<style></style>
