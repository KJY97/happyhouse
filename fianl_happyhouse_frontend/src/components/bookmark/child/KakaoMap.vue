<template>
  <!-- Side widgets-->
  <div class="col-lg-8">
    <!-- Search widget-->
    <div class="row mb-2">
      <div class="col-lg-4" style="font-size: 30px">지도로 보기</div>
      <div class="col-lg-8 text-right">
        <b-button
          class="ml-2 bg-primary"
          v-if="bounds != null"
          @click="totalShow()"
        >
          🌏 지도 전체 보기&nbsp;</b-button
        >
      </div>
    </div>

    <!-- map -->
    <div class="card mb-2">
      <div
        id="map"
        style="width: 100%; height: 428px; margin: auto"
        class="mt-3 mb-3"
      ></div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
// import http from "@/util/http-common";

const memberStore = "memberStore";

export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      markers: [],
      bounds: null,
      my_api_key: "2137d1ca2953817e9066591941db99ca",
      modalShow: false,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      // /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${this.my_api_key}&libraries=services`;
      document.head.appendChild(script);
    }
  },
  computed: {
    ...mapState(memberStore, ["houseAddress", "houseAddressList"]),
    ...mapState(memberStore, ["bookmarks"]),
  },
  watch: {
    // houseAddressList값이 변할 때마다 함수 실행
    houseAddressList: function () {
      this.displayMarker(this.map);
      // console.log(this.houseAddress)
    },
    houseAddress: function () {
      this.panTo(this.houseAddress);
    },
  },
  methods: {
    ...mapActions(memberStore, ["getUserBookmarks"]),
    // getBookMarkList() {
    //   this.getUserBookmarks();
    // },
    initMap() {
      const container = document.getElementById("map"); // 지도를 담을 영역의 DOM 레퍼런스
      const options = {
        // 지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(37.5012743, 127.039585), // 지도의 중심좌표. (멀티캠퍼스)
        level: 3, // 지도의 레벨(확대, 축소 정도)
      };
      // 지도 생성
      this.map = new kakao.maps.Map(container, options);
    },
    displayMarker(map) {
      // 기존의 makers 제거
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      // 주소-좌표 변환 객체를 생성합니다
      const geocoder = new kakao.maps.services.Geocoder();
      // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
      this.bounds = new kakao.maps.LatLngBounds();

      // 주소로 좌표를 검색합니다
      let i = 0;
      this.houseAddressList.forEach((house) => {
        geocoder.addressSearch(house.도로명주소, (result, status) => {
          // 정상적으로 검색이 완료됐으면
          if (status === kakao.maps.services.Status.OK) {
            const coords = new kakao.maps.LatLng(result[0].y, result[0].x); // 위도, 경도
            const marker = new kakao.maps.Marker({
              map: map,
              position: coords,
              title: house.아파트명,
            });

            // 인포윈도우 생성
            var infowindow = new kakao.maps.InfoWindow({
              content: `<div style="padding:10px;">${house.아파트명}</div>`,
            });

            kakao.maps.event.addListener(marker, "mouseover", function () {
              infowindow.open(map, marker);
            });

            kakao.maps.event.addListener(marker, "mouseout", function () {
              infowindow.close();
            });

            this.bounds.extend(coords); // LatLngBounds 객체에 좌표를 추가합니다
            this.markers.push(marker); // 마커들 저장

            if (i == 0) map.panTo(coords); // 부드럽게 지도 이동
            i++;
          }
        });
      });
    },
    totalShow() {
      this.map.setBounds(this.bounds);
    },
    panTo(house) {
      console.log(house);
      // 주소-좌표 변환 객체를 생성합니다
      const geocoder = new kakao.maps.services.Geocoder();
      geocoder.addressSearch(house.도로명주소, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          // 이동할 위도 경도 위치를 생성합니다
          var moveLatLon = new kakao.maps.LatLng(result[0].y, result[0].x);

          this.map.setLevel(3);
          this.map.panTo(moveLatLon); // 지도 중심을 부드럽게 이동시킵니다
          // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        }
      });
    },
    bookmark() {},
  },
};
</script>

<style></style>
