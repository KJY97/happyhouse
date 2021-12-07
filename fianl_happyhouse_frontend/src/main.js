import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from "axios";
import { MY_API_KEY } from "@/api/login";

// SDK를 초기화 합니다.
window.Kakao.init(MY_API_KEY);
// SDK 초기화 여부를 판단합니다.
// console.log(window.Kakao.isInitialized());

import BootstrapVue from "bootstrap-vue";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

Vue.use(BootstrapVue);
//Vue.use(axios);
Vue.prototype.$http = axios;
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
