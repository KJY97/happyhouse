<template>
  <div class="container">
    <div class="row">
      <div class="col-lg-4 mt-2">
        <house-info />
        <environment-info />
      </div>
      <kakao-map />
    </div>
  </div>
</template>

<script>
import HouseInfo from "@/components/main/child/HouseInfo.vue";
import EnvironmentInfo from "@/components/main/child/EnvironmentInfo.vue";
import KakaoMap from "@/components/main/child/KakaoMap.vue";

export default {
  components: {
    HouseInfo,
    EnvironmentInfo,
    KakaoMap,
  },
};
</script>

<style></style>
