<template>
    <div class="container px-4 px-lg-5">
        <div class="row gx-4 gx-lg-5 justify-content-center">
            <div class="col-md-10 col-lg-8 col-xl-7">
                <div class="post-preview" v-for="post in posts">
                    <Post :post="post" />
                </div>
                <!-- Divider-->
                <hr class="my-4" />

                <!-- Pager-->
                <div class="d-flex justify-content-end mb-4">
                    <a class="btn btn-primary text-uppercase" @click="changePage(meta.current_page - 1)"
                        v-if="this.meta.current_page > 1">
                        ←Previous
                    </a>
                    <a class="btn btn-primary text-uppercase ms-3" @click="changePage(meta.current_page + 1)"
                        v-if="this.meta.current_page < this.meta.total_pages">
                        Next→
                    </a>
                </div>
            </div>
        </div>
    </div>
</template>
<script>

import { mapState } from 'vuex'

export default {
    name: 'Main',
    methods: {
        changePage(page) {
            if (page < 1) page = 1;
            if (page > this.meta.total_pages) page = this.meta.total_pages;
            this.$store.dispatch('post/findAll', { page: page })
        }
    },
    computed: {
        ...mapState({
            posts: state => state.post.posts,
            meta: state => state.post.metaData
        })
    }
}
</script>
<style scoped></style>