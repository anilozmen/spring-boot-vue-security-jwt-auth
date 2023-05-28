export const state = () => ({
  posts: [],
  metaData: {},
  selectedPost: {},
});

export const getters = {
  get: (state) => () => {
    return state.posts;
  },
  getSelectedPost: (state) => () => {
    return state.selectedPost;
  },
};

export const mutations = {
  SET_POSTS(state, posts) {
    state.posts = posts;
  },
  SET_METADATA(state, meta) {
    state.metaData = meta;
  },
  SET_SELECTED_POST(state, post) {
    state.selectedPost = post;
  },
  SET_COMMENT_POST(state, comment) {
    state.selectedPost.comments.unshift(comment);
  },
};

export const actions = {
  async findAll({ commit }, payload) {
    let pageParam = payload?.page != undefined ? { page: payload.page } : {};
    let response = await this.$axios.get("posts", { params: pageParam });
    commit("SET_POSTS", response.data.data);
    commit("SET_METADATA", response.data.meta);
  },
  async findById({ commit }, payload) {
    let post = await this.$axios.get(
      `posts/${payload.post}`,
      payload?.params ?? {}
    );
    commit("SET_SELECTED_POST", post.data);
  },
  async submitComment({ commit }, payload) {
    let comment = await this.$axios.post(`posts/${payload.postId}/comments`, {
      comment: payload?.comment,
    });
    commit("SET_COMMENT_POST", comment.data);
  },
};
