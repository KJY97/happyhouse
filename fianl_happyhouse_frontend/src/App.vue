<template>
  <div id="app">
    <bar></bar>
    <router-view></router-view>
  </div>
</template>

<script>
import Bar from "@/components/layout/Bar.vue";

export default {
  components: {
    Bar,
  },
};
</script>

<style>
@import url(//fonts.googleapis.com/earlyaccess/hanna.css);
* {
  font-family: "Hanna";
}
</style>
