<template>
    <header class="masthead" :style="{ backgroundImage: `url(` + require('~/assets/images/home-bg.jpg') + `)` }">
        <div class="container position-relative px-4 px-lg-5">
            <div class="row gx-4 gx-lg-5 justify-content-center">
                <div class="col-md-10 col-lg-8 col-xl-7">
                    <div class="site-heading" v-if="!isPost">
                        <h1>{{ title }}</h1>
                    </div>
                    <div class="post-heading" v-else>
                        <h1>{{ post?.title }}</h1>
                        <h2 class="subheading">{{ post?.short_description }}</h2>
                        <span class="meta">
                            Posted by
                            <a>{{ post?.author }}</a> on {{ post?.created_at | formatDate }}
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </header>
</template>

<script>

import { mapState } from 'vuex'

export default {
    name: 'Header',
    computed: {
        ...mapState({
            title: state => state.title,
            isPost: state => Object.keys(state.post.selectedPost).length > 0,
            post: state => state.post.selectedPost
        })
    }
}
</script>
<style scoped></style>