import { apiInstance } from "./index.js";

const api = apiInstance();

function listNotice(success, fail) {
  api.get(`/notice`).then(success).catch(fail);
}

function getNoticeInfo(notice_no, success, fail) {
  api.get(`/notice/${notice_no}`).then(success).catch(fail);
}

function insertNotice(notice, success, fail) {
  api.post(`/notice`, JSON.stringify(notice)).then(success).catch(fail);
}

function updateNotice(notice, success, fail) {
  api.put(`/notice`, JSON.stringify(notice)).then(success).catch(fail);
}

function deleteNotice(notice_no, success, fail) {
  api.delete(`/notice/${notice_no}`).then(success).catch(fail);
}

export { listNotice, getNoticeInfo, insertNotice, updateNotice, deleteNotice };
