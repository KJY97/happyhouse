<template>
  <div>
    <board-banner />
    <b-container class="bv-example-row mt-3">
      <b-row>
        <b-col>
          <b-alert variant="dark" show><h4>작성 글 확인 😀</h4></b-alert>
        </b-col>
      </b-row>

      <b-row class="mb-1">
        <b-col>
          <b-card
            :header-html="`<h5>
          [문의 ${article.question_no}] ${article.title} </h5><h6 class='text-right'>${article.username} (${article.regtime})</h6>`"
            class="mb-2"
            border-variant="dark"
            no-body
          >
            <b-card-body class="text-left">
              <b-card-text>{{ article.content }}</b-card-text>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>

      <comment-write
        v-if="curLoginUser === 1 && article.answer == null"
        :question_no="article.question_no"
      />
      <comment-write
        v-if="isModifyShow && this.modifyComment != null"
        :modifyComment="this.modifyComment"
        @modify-comment-cancel="onModifyCommentCancel"
      />

      <b-row class="mt-2">
        <b-col class="text-right">
          <b-button
            variant="primary"
            size="sm"
            @click="listArticle"
            class="mr-2"
            >글목록
          </b-button>
          <b-button
            v-if="curLoginUser !== 1 && curLoginUser === article.user_no"
            variant="info"
            size="sm"
            @click="moveModifyArticle"
            class="mr-2"
            >글수정</b-button
          >
          <b-button
            v-if="curLoginUser === 1 || curLoginUser === article.user_no"
            variant="danger"
            size="sm"
            @click="deleteArticle"
            >글삭제</b-button
          >
        </b-col>
      </b-row>
      <comment
        v-if="article.answer != null"
        :article="article"
        :curLoginUser="curLoginUser"
        @modify-comment="onModifyComment"
      />
    </b-container>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState, mapActions } from "vuex";
import CommentWrite from "@/components/board/comment/CommentWrite.vue";
import Comment from "@/components/board/comment/Comment.vue";
import BoardBanner from "./child/BoardBanner.vue";

const boardStore = "boardStore";
const memberStore = "memberStore";
export default {
  name: "BoardView",
  data() {
    return {
      isModifyShow: false,
      modifyComment: Object,
      type: "modify",
      curLoginUser: null, // 1 = admin(관리자)
    };
  },
  computed: {
    ...mapState(boardStore, ["article"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  components: {
    CommentWrite,
    Comment,
    BoardBanner,
  },
  created() {
    this.getContent();
    if (this.userInfo != null) this.curLoginUser = this.userInfo.user_no;
  },
  methods: {
    ...mapActions(boardStore, ["getArticle"]),
    getContent() {
      this.getArticle(this.$route.params.question_no);
    },
    deleteArticle() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        http.delete(`/qna/${this.article.question_no}`).then(({ data }) => {
          let msg = "삭제를 실패하였습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          this.$router.push("/board/list");
        });
      }
    },
    listArticle() {
      this.$router.push({ name: "BoardTable" });
    },
    moveModifyArticle() {
      this.$router.push({ path: `/board/modify/${this.article.question_no}` });
    },
    onModifyComment(comment) {
      this.modifyComment = comment;
      this.isModifyShow = true;
    },
    onModifyCommentCancel(isShow) {
      this.isModifyShow = isShow;
    },
  },
};
</script>

<style></style>
