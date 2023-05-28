<template>
    <div class="container px-4 px-lg-5">
        <div class="row gx-4 gx-lg-5 justify-content-center">
            <div class="col-md-10 col-lg-8 col-xl-7">
                <h1>Comments</h1>
                <hr>
                <div class="coment-bottom bg-white p-2">
                    <div class="d-flex flex-row add-comment-section mt-4 mb-4">
                        <input type="text" class="form-control mr-3" placeholder="Add comment" v-model="comment">
                        <button class="btn btn-primary" type="button" @click="submitComment">Comment</button>
                    </div>
                    <div class="d-flex flex-column">
                        <div class="mt-2 border p-2" v-for="comment in comments">
                            <div class="d-flex flex-row align-items-center commented-user">
                                <h5 class="mr-2">{{ comment.full_name }}</h5>
                                <span class="dot mb-1"></span>
                                <span class="mb-1 ml-2">{{ comment.created_at | formatDateFromNow }}</span>
                            </div>
                            <div class="comment-text-sm">
                                <span>{{ comment.comment }}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <hr>
            </div>
        </div>
    </div>
</template>

 
<script>
export default {
    name: 'Comment',
    props: ['comments', 'postId'],
    data() {
        return {
            comment: ''
        }
    },
    methods: {
        submitComment() {
            if (this.comment != '') {
                this.$store.dispatch('post/submitComment', { postId: this.postId, comment: this.comment });
            }
            this.comment = '';
        }
    },
}
</script>
<style scoped>
.dot {
    height: 7px;
    width: 7px;
    margin-top: 3px;
    margin-right: 10px;
    margin-left: 10px;
    background-color: #bbb;
    border-radius: 50%;
    display: inline-block;
}
</style>