<template>
  <!-- Page header with logo and tagline-->
  <header class="py-4 bg-light border-bottom mb-4">
    <div class="container">
      <div class="text-center my-1">
        <h1>주변 상권을 함께 찾아볼까요?</h1>
        <h3 class="lead mb-0">Find your hot place :)</h3>
        <h1 class="mt-3">🚋</h1>

        <div class="container text-center col-3 mb-2 mt-4">
          <b-row>
            <b-col class="sm-3">
              <b-form-select
                v-model="sidoCode"
                :options="sidos"
                @change="gugunList"
              ></b-form-select>
            </b-col>
            <b-col class="sm-3">
              <b-form-select
                v-model="gugunCode"
                :options="guguns"
                @change="searchCommerce"
              ></b-form-select>
            </b-col>

            <!-- <b-col class="sm-3">
              <b-form-select
                v-model="dongCode"
                :options="dongs"
                @change="searchApt"
              ></b-form-select>
            </b-col> -->
          </b-row>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const houseStore = "houseStore";
export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
    };
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_COMMERCE_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, ["getSido", "getGugun", "getCommerceList"]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_COMMERCE_LIST",
    ]),
    gugunList() {
      this.gugunCode = null;
      this.dongCode = null;
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_COMMERCE_LIST();
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    searchCommerce() {
      const params = {
        divId: "signguCd",
        key: this.gugunCode,
      };
      if (this.gugunCode) this.getCommerceList(params);
    },
  },
};
</script>

<style></style>
