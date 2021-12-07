<template>
  <div class="comment mt-2">
    <div class="head">관리자 ({{ article.answer_time }})</div>
    <div class="content" v-html="enterToBr(article.answer)"></div>
    <!-- 로그인 기능 구현 후 로그인한 자신의 글에만 보이게 한다. -->
    <div v-if="curLoginUser === 1" class="cbtn">
      <label @click="modifyCommentView()">수정</label> |
      <label @click="deleteComment()">답변 삭제</label>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapActions } from "vuex";
const boardStore = "boardStore";

export default {
  name: "comment",
  data() {
    return {};
  },
  props: {
    article: Object,
    curLoginUser: Number,
  },
  methods: {
    ...mapActions(boardStore, ["getAnswer"]),
    modifyCommentView() {
      this.$emit("modify-comment", {
        question_no: this.article.question_no,
        answer_no: this.article.answer_no,
        comment: this.article.answer,
      });
    },
    deleteComment() {
      // console.log(this.article);
      if (confirm("정말로 삭제하시겠습니까?")) {
        http
          .delete(`/qna/answer/${this.article.question_no}/${this.article.answer_no}`)
          .then(({ data }) => {
            let msg = "삭제 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "삭제가 완료되었습니다.";
            }
            alert(msg);
            this.getAnswer(this.article.question_no);
            // 새로고침
            this.$router.push({ name: "BoardTable" });
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
};
</script>

<style>
.comment {
  text-align: left;
  border-radius: 5px;
  background-color: #e3effa;
  padding: 10px 20px;
}

.content {
  padding: 5px;
}
.cbtn {
  text-align: right;
  color: steelblue;
  margin: 5px 0px;
}
</style>
