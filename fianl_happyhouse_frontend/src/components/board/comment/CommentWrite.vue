<template>
  <div>
    <div v-if="this.modifyComment != null" class="regist_form">
      <textarea
        id="comment"
        name="comment"
        v-model="modifyComment.comment"
        cols="35"
        rows="2"
      ></textarea>

      <b-button
        pill
        id="enter"
        class="mt-3"
        variant="secondary"
        @click="updateComment"
        >답변 수정</b-button
      >
    </div>
    <div v-else class="regist_form mt-2 mb-3">
      <textarea
        id="comment"
        name="comment"
        v-model="comment"
        cols="35"
        rows="2"
      ></textarea>
      <b-button
        pill
        id="enter"
        class="mt-3"
        variant="secondary"
        @click="registComment"
        >답변 등록</b-button
      >
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapActions } from "vuex";
const boardStore = "boardStore";

export default {
  name: "commentwrite",
  data() {
    return {
      // 차후 작성자 이름은 로그인 구현후 로그인한 사용자로 바꾼다.
      user_name: "admin",
      comment: "",
    };
  },
  props: {
    question_no: { type: Number },
    modifyComment: { type: Object },
  },
  methods: {
    ...mapActions(boardStore, ["getAnswer"]),
    registComment() {
      http
        .post("/qna/answer", {
          answer: this.comment,
          question_no: this.question_no,
        })
        .then(({ data }) => {
          console.log(data);
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          // 작성글 지우기
          this.comment = "";
          this.getAnswer(this.question_no);
          // 새로고침
          this.$router.go();
        });
    },
    updateComment() {
      http
        .put(`/qna/answer`, {
          answer_no: this.modifyComment.answer_no,
          answer: this.modifyComment.comment,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);

          console.log(this.modifyComment.question_no);
          this.getAnswer(this.modifyComment.question_no);
          // 새로고침
          this.$router.go();
        });
    },
  },
};
</script>

<style scoped>
.regist_form {
  text-align: left;
  border-radius: 5px;
  background-color: #ccc;
  padding: 20px;
}

textarea {
  width: 89%;
  padding: 10px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #787878;
  font-size: large;
}

#enter {
  float: right;
  padding: 18px;
}

button {
  float: right;
}

button.small {
  width: 60px;
  font-size: small;
  font-weight: bold;
}
</style>
