<template>
  <div>
    <br />
    <hr class="my-0" />
    <h3 class="mt-4">미세먼지 농도</h3>
    <div class="card mb-4 p-3 mt-3" style="font-size: 25px">
      <div v-if="environment == null || ph == null">
        조회하실 지역을 선택해주세요 😶
        <h5>(서울 지역만 지원됩니다)</h5>
      </div>
      <div v-else>
        <span>현재</span>
        <span class="text-danger"> {{ nowGun }}</span
        >의 미세먼지 <br />
        지수는<span class="text-info"> {{ ph }} </span>(으)로<span
          class="text-warning"
        >
          {{ environment }}</span
        >입니다.
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";
export default {
  name: "EnvironmentInfo",
  data() {
    return {};
  },
  computed: {
    ...mapState(houseStore, ["environment", "nowGun", "ph"]),
  },
  methods: {},
};
</script>

<style></style>
