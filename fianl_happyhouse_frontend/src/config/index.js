// 서버의 swagger 주소
const API_BASE_URL = "http://localhost:9999/happyhouse/";

const CLIENT_ID = "cfc07a759d05e67ff50b34db7e6f2776";
const REDIRECT_URI = "http://localhost:8080/login/auth";
const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;

export { API_BASE_URL, KAKAO_AUTH_URL, REDIRECT_URI };