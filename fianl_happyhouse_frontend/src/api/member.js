import { apiInstance } from "./index.js";

const api = apiInstance();

// 비동기
async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  // http header에 token 정보 담기
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/${userid}`).then(success).catch(fail);
}

function registerMember(user, success, fail) {
  api.post(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

function modifyMember(user, success, fail) {
  api.put(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

function deleteMember(userid, success, fail) {
  api.delete(`/user/${userid}`).then(success).catch(fail);
}

function checkId(userid, success, fail) {
  api.get(`/user/ckid/${userid}`).then(success).catch(fail);
}

export { login, findById, registerMember, modifyMember, deleteMember, checkId};
