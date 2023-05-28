<template>
  <div>
    <Main />
  </div>
</template>

<script>

export default {
  middleware: 'auth-user',
  async asyncData({isDev, route, store, env, params, query, req, res, redirect, error}) {
    await store.commit('post/SET_SELECTED_POST', {});
    await store.commit('SET_TITLE', 'Spring Boot JWT Blog');
  },
}
</script>
