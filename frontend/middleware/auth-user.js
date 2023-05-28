export default async function ({ $auth, store }) {
  if ($auth.loggedIn) {
    await store.dispatch("post/findAll");
  }

  store.commit("FINISH_LOADING");
}
