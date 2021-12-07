<template>
  <!-- Page header with logo and tagline-->
  <header class="py-4 bg-light border-bottom mb-4">
    <div class="container">
      <div class="text-center my-5">
        <h1>당신의 집을 찾아보세요!</h1>
        <h3 class="lead mb-0">Find your home with us</h3>
        <h1 class="mt-3">🏠</h1>

        <div class="container text-center col-5 mb-2 mt-4">
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
                @change="dongList"
              ></b-form-select>
            </b-col>

            <b-col class="sm-3">
              <b-form-select
                v-model="dongCode"
                :options="dongs"
                @change="searchApt"
              ></b-form-select>
            </b-col>
            <!-- <b-col class="sm-3">
              <input type="button" onclick="add()" value="❤" />
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
    ...mapState(houseStore, ["sidos", "guguns", "dongs"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "CLEAR_HOUSE_LIST",
    ]),

    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.CLEAR_HOUSE_LIST();
      this.gugunCode = null;
      this.dongCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      this.CLEAR_DONG_LIST();
      this.CLEAR_HOUSE_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt() {
      const params = {
        sido: this.sidoCode,
        gugun: this.gugunCode,
        dong: this.dongCode,
      };
      if (this.gugunCode) this.getHouseList(params);
    },
  },
};
</script>

<style></style>
