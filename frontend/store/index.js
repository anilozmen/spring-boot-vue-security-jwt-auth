export const strict = true;

export const state = () => ({
  isLoaded: false,
  title: ''
});

export const mutations = {
  SET_TITLE(state, title) {
    state.title = title;
  },
  FINISH_LOADING(state) {
    state.isLoaded = true;
  }
};

export const actions = {
  async changeTitle({commit}, payload) {
    commit('SET_TITLE', payload);
  }
}