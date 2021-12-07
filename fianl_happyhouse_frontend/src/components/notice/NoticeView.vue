<template>
  <div>
    <b-container class="bv-example-row mt-3">
      <b-row>
        <b-col>
          <b-alert variant="dark" show><h4>공지사항 확인 😀</h4></b-alert>
        </b-col>
      </b-row>

      <b-row class="mb-1">
        <b-col>
          <b-card
            :header-html="`<h5>
              [공지] ${notice.title} </h5><h6 class='text-right'>${notice.username} (${notice.regtime})</h6>`"
            class="mb-2"
            border-variant="dark"
            no-body
          >
            <b-card-body class="text-left">
              <div>{{ notice.content }}</div>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>

      <div class="text-right">
        <b-button variant="primary" size="sm" @click="moveList" class="mr-2"
          >글목록
        </b-button>
        <span v-if="checkUserInfo != null && checkUserInfo.user_no == 1">
          <b-button
            variant="info"
            size="sm"
            @click="moveModifyArticle"
            class="mr-2"
            >글수정</b-button
          >
          <b-button variant="danger" size="sm" @click="deleteArticle"
            >글삭제</b-button
          >
        </span>
      </div>
    </b-container>
  </div>
</template>

<script>
import { deleteNotice } from "@/api/notice.js";
import { mapState, mapActions, mapGetters } from "vuex";

const noticeStore = "noticeStore";
const memberStore = "memberStore";

export default {
  name: "Noticeview",

  data() {
    return {};
  },
  created() {
    this.getNotice(this.$route.params.notice_no);
  },
  computed: {
    ...mapState(noticeStore, ["notice"]),
    ...mapGetters(memberStore, ["checkUserInfo"]),
  },
  methods: {
    ...mapActions(noticeStore, ["getNotice"]),

    deleteArticle() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        deleteNotice(
          this.notice.notice_no,
          ({ data }) => {
            let msg = "삭제를 실패하였습니다.";
            if (data === "success") {
              msg = "삭제가 완료되었습니다.";
            }
            alert(msg);
            this.$router.push({ name: "NoticeList" });
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    moveList() {
      this.$router.push({ name: "NoticeList" });
    },
    moveModifyArticle() {
      this.$router.push({ path: `/notice/modify/${this.notice.notice_no}` });
    },
  },
};
</script>
<style></style>
