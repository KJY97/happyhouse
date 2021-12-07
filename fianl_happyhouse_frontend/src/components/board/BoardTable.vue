<template>
  <div>
    <board-banner />
    <b-container class="bv-example-row mt-3">
      <!-- <b-row class="mb-1"> </b-row> -->
      <b-row>
        <b-col>
          <b-alert variant="dark" show><h4>작성 글</h4></b-alert>
        </b-col>
      </b-row>

      <b-row>
        <board-row :articles="articles" />
        <h2 v-if="articles.length"></h2>
        <h2 v-else class="text-center mt-3">문의게시글이 없습니다 :)</h2>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import BoardRow from "@/components/board/child/BoardRow.vue";
import BoardBanner from "@/components/board/child/BoardBanner.vue";
import { mapState, mapActions } from "vuex";

const boardStore = "boardStore";
export default {
  name: "BoardTable",
  components: {
    BoardRow,
    BoardBanner,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(boardStore, ["articles"]),
    ...mapActions(boardStore, ["getList"]),
  },
  created() {
    this.getList;
  },
};
</script>

<style></style>
