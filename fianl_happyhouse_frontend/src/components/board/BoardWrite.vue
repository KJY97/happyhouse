<template>
  <div>
    <board-banner v-if="this.type != 'modify'" />
    <div class="container">
      <b-alert show variant="dark" v-if="this.type == 'modify'"
        ><h3>글수정</h3></b-alert
      >
      <b-alert show variant="dark" v-else><h3>글작성</h3></b-alert>

      <b-row class="mb-1">
        <b-col style="text-align: left">
          <b-form @submit="onSubmit">
            <b-form-group id="title-group" label="제목" label-for="title">
              <b-form-input
                id="title"
                v-model="article.title"
                type="text"
                required
                placeholder="제목을 입력하세요."
              ></b-form-input>
            </b-form-group>

            <b-form-group id="content-group" label="내용" label-for="content">
              <b-form-textarea
                id="content"
                v-model="article.content"
                placeholder="문의 내용을 입력하세요."
                rows="10"
                max-rows="15"
              ></b-form-textarea>
            </b-form-group>

            <b-col class="text-right">
              <b-button
                type="submit"
                variant="primary"
                class="mr-2"
                v-if="this.type == 'modify'"
                >글수정</b-button
              >
              <b-button type="submit" variant="primary" class="mr-2" v-else
                >글작성</b-button
              >
              <b-button variant="danger" @click="listArticle">글목록 </b-button>
            </b-col>
          </b-form>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import { mapState } from "vuex";
import BoardBanner from "@/components/board/child/BoardBanner.vue";

const memberStore = "memberStore";

export default {
  name: "BoardWriteForm",
  data() {
    return {
      article: {
        question_no: 0,
        user_no: 0,
        username: "",
        title: "",
        content: "",
      },
    };
  },
  components: {
    BoardBanner,
  },
  props: {
    type: { type: String },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    if (this.type === "modify") {
      http.get(`/qna/${this.$route.params.question_no}`).then(({ data }) => {
        // console.log(data);
        this.article.question_no = data.question_no;
        this.article.user_no = data.user_no;
        this.article.username = data.username;
        this.article.title = data.title;
        this.article.content = data.content;
      });
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.title &&
        ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "modify" ? this.modifyArticle() : this.registArticle();
    },
    registArticle() {
      http
        .post(`/qna`, {
          user_no: this.userInfo.user_no,
          username: this.userInfo.username,
          title: this.article.title,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "작성을 실패하였습니다.";
          if (data === "success") {
            msg = "작성이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "BoardTable" });
        })
        .catch((error) => {
          alert("작성시 문제가 발생하였습니다.");
          console.log(error);
          this.$router.push({ name: "BoardTable" });
        });
    },
    modifyArticle() {
      // console.log(this.article);
      http
        .put(`/qna`, {
          question_no: this.article.question_no,
          user_no: this.article.user_no,
          title: this.article.title,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "수정을 실패하였습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "BoardTable" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    listArticle() {
      this.$router.push({ name: "BoardTable" });
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
  },
};
</script>

<style></style>
