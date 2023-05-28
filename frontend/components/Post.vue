<template>
    <div>
        <NuxtLink :to="'/posts/' + post.id">
            <h2 class="post-title">{{ post.title }}</h2>
            <h3 class="post-subtitle">{{ post.short_description }}</h3>
        </NuxtLink>
        <p class="post-meta">
            Posted by
            <i>{{ post.author }}</i>
            on {{ post.created_at | formatDate }}
        </p>
    </div>
</template>
<script>
export default {
    props: ['post']
}
</script>
<style scoped></style>