import { listNotice, getNoticeInfo } from "@/api/notice.js";

const noticeStore = {
  namespaced: true,
  state: {
    noticeList: [],
    notice: {},
  },
  mutations: {
    SET_NOTICE_LIST(state, noticeList) {
      // console.log(noticeList);
      state.noticeList = noticeList;
    },
    SET_NOTICE(state, notice) {
      state.notice = notice;
    }
  },
  actions: {
    getNoticeList({ commit }) {
      listNotice(
        ({ data }) => {
          commit("SET_NOTICE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      )
    },
    getNotice({ commit }, notice_no) {
      getNoticeInfo(
        notice_no,
        ({ data }) => {
          commit("SET_NOTICE", data);
        },
        (error) => {
          console.log(error);
        }
      )
    }
  },
}

export default noticeStore;