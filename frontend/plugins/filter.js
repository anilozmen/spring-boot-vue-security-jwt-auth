import Vue from "vue";
import moment from "moment";

Vue.filter("formatDate", (value) => {
  return moment(value).format("MMM DD, YYYY");
});

Vue.filter("formatDateFromNow", (value) => {
  return moment(value).fromNow();
});
