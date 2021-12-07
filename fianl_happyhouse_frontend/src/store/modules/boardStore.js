import http from "@/util/http-common.js";

const BoardStore = {
  namespaced: true,
  state: {
    articles: [],
    article: {},
    comment: {},
  },
  mutations: {
    SET_ARTICLE_LIST(state, articles) {
      state.articles = [];
      // console.log(articles);
      articles.data.forEach((article) => {
        state.articles.push({
          question_no: article.question_no,
          user_no: article.user_no,
          username: article.username,
          title: article.title,
          content: article.content,
          answer_state: article.answer_state,
          regtime: article.regtime,
        });
      });
    },
    SET_ARTICLE(state, response) {
      console.log(response);

      state.article = {};
      state.article.question_no = response.data.question_no;
      state.article.user_no = response.data.user_no;
      state.article.username = response.data.username; // 질문 작성자
      state.article.title = response.data.title;
      state.article.content = response.data.content;
      state.article.answer_state = response.data.answer_state;
      state.article.regtime = response.data.regtime;

      state.article.answer_no = response.data.answer_no;
      state.article.answer = response.data.answer;
      state.article.answer_time = response.data.answer_time;
    },
    SET_ANSWER(state, data) {
      // console.log(data);
      state.comment = {};
      state.comment.answer = data.answer;
      state.comment.answer_no = data.answer_no;
      state.comment.answer_state = data.answer_state;
      state.comment.answer_time = data.answer_time;
      state.comment.question_no = data.question_no;
    },
  },
  actions: {
    getList({ commit }) {
      http
        .get(`/qna`)
        .then((response) => {
          // console.log(response);
          commit("SET_ARTICLE_LIST", response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getArticle({ commit }, question_no) {
      http
        .get(`/qna/${question_no}`)
        .then((response) => {
          // console.log(response);
          commit("SET_ARTICLE", response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getAnswer({ commit }, question_no) {
      http
        .get(`/qna/answer/${question_no}`)
        .then(({ data }) => {
          // console.log(data);
          commit("SET_ANSWER", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  //  plugins: [createPersistedState()],
};

export default BoardStore;
