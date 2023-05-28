<template>
    <article class="mb-4">
        <div class="container px-4 px-lg-5">
            <div class="row gx-4 gx-lg-5 justify-content-center">
                <div class="col-md-10 col-lg-8 col-xl-7" v-html="post?.content">
                </div>
            </div>
        </div>
        <Comment :comments="post.comments" :postId="post?.id" />
    </article>
</template>
<script>

import { mapActions, mapState } from 'vuex'

export default {
    middleware: 'auth-user',
    methods: {
        ...mapActions({
            selectedPost: 'post/findById'
        }),
    },
    computed: {
        ...mapState({
            post: state => state.post.selectedPost
        })
    },
    async asyncData({ isDev, route, store, env, params, query, req, res, redirect, error }) {
        await store.dispatch('post/findById', params);
    }
}
</script>
<style scoped></style>