<template>
  <div>
    <div class="container">
      <b-alert show variant="dark" v-if="type == 'modify'"
        ><h3>글수정</h3></b-alert
      >
      <b-alert show variant="dark" v-else><h3>글작성</h3></b-alert>

      <b-row class="mb-1">
        <b-col style="text-align: left">
          <b-form @submit="onSubmit">
            <b-form-group
              id="checkYN-group"
              label="공지옵션"
              label-for="checkYN"
            >
              <b-form-checkbox
                id="checkbox-1"
                v-model="article.checkYN"
                name="checkYN"
                value="Y"
                unchecked-value="N"
              >
                공지
              </b-form-checkbox>
            </b-form-group>
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
                placeholder="내용을 입력하세요."
                rows="10"
                max-rows="15"
              ></b-form-textarea>
            </b-form-group>

            <b-col class="text-right">
              <b-button
                type="submit"
                variant="primary"
                class="mr-2"
                v-if="type == 'modify'"
                >글수정</b-button
              >
              <b-button type="submit" variant="primary" class="mr-2" v-else
                >글작성</b-button
              >
              <b-button variant="danger" @click="moveList">글목록 </b-button>
            </b-col>
          </b-form>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import { insertNotice, updateNotice } from "@/api/notice.js";
import { mapState } from "vuex";

const noticeStore = "noticeStore";
const memberStore = "memberStore";

export default {
  name: "NoticeWriteForm",
  data() {
    return {
      article: {
        notice_no: "",
        user_no: "",
        usrname: "",
        title: "",
        content: "",
        checkYN: "N",
      },
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      console.log(this.notice);
      this.article = this.notice;
      // this.article.user_no = "admin";
    }
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(noticeStore, ["notice"]),
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      let err = true;
      let msg = "";
      !this.article.title &&
        ((msg = "제목을 입력해주세요"),
        (err = false),
        this.$refs.title.focus());
      err &&
        !this.article.content &&
        ((msg = "내용을 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "modify" ? this.modifyArticle() : this.createArticle();
    },
    createArticle() {
      insertNotice(
        {
          user_no: this.userInfo.user_no,
          username: this.userInfo.username,
          title: this.article.title,
          content: this.article.content,
          checkYN: this.article.checkYN,
        },
        ({ data }) => {
          let msg = "작성을 실패하였습니다.";
          if (data === "success") {
            msg = "작성이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "NoticeList" });
        },
        (error) => {
          alert("작성 처리시 문제가 발생했습니다.");
          console.log(error);
          this.$router.push({ name: "NoticeList" });
        }
      );
    },
    modifyArticle() {
      updateNotice(
        {
          notice_no: this.article.notice_no,
          user_no: this.userInfo.user_no,
          username: this.userInfo.username,
          title: this.article.title,
          content: this.article.content,
          checkYN: this.article.checkYN,
        },
        ({ data }) => {
          let msg = "수정을 실패하였습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "NoticeList" });
        },
        (error) => {
          alert("수정 처리시 문제가 발생했습니다.");
          console.log(error);
          this.$router.push({ name: "NoticeList" });
        }
      );
    },
    moveList() {
      this.$router.push({ name: "NoticeList" });
    },
  },
};
</script>

<style lang="scss" scoped></style>
