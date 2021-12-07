<template>
  <!-- 거래-->
  <div>
    <div>
      <span>
        <h3 v-if="commerces.length == 0 && nowGun == null">상권 정보</h3>
        <h3 v-else>
          <span class="text-success"> {{ nowGun }} </span>상권정보
        </h3>
      </span>
      <span id="info"></span>
    </div>

    <div
      v-if="commerces.length == 0"
      class="card mb-4 p-3 mt-3"
      style="font-size: 25px"
    >
      <h4>
        검색한 내역이 없어요 😲<br />
        조회구의 <span class="text-success">상권 현황</span>을 알려드려요!
      </h4>
      <h6>* 전국 조회가 가능합니다.</h6>
    </div>

    <div v-else class="card mb-4 p-3 mt-3">
      <h5>
        총
        <span class="text-danger">{{ commerces.length }}개</span>의 상권이
        있어요!<br />
        세부내역은 <span class="text-primary">오른쪽</span>을 확인해주세요 😊
      </h5>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

const houseStore = "houseStore";
export default {
  name: "CommerceInfo",
  computed: {
    ...mapState(houseStore, ["commerces", "nowGun"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
  },
  methods: {
    ...mapMutations(houseStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST"]),
  },
};
</script>

<style></style>
