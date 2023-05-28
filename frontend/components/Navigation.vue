<!-- Please remove this file from your project -->
<template>
    <nav class="navbar navbar-expand-lg navbar-light" id="mainNav">
        <div class="container px-4 px-lg-5">
            <NuxtLink to="/" class="navbar-brand">Blog</NuxtLink>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                Menu
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ms-auto py-4 py-lg-0">
                    <li class="nav-item">
                        <NuxtLink to="/" class="nav-link px-lg-3 py-3 py-lg-4">Home page</NuxtLink>
                    </li>
                    <li class="nav-item" v-if="!$auth.loggedIn">
                        <NuxtLink to="/login" class="nav-link px-lg-3 py-3 py-lg-4">Login</NuxtLink>
                    </li>
                    <li class="nav-item" v-if="!$auth.loggedIn">
                        <NuxtLink to="/register" class="nav-link px-lg-3 py-3 py-lg-4">Register</NuxtLink>
                    </li>
                    <li class="nav-item" v-else><a class="nav-link px-lg-3 py-3 py-lg-4" @click="logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</template>
  
<script>
export default {
    name: 'Navigation',
    methods: {
        async logout() {
            await this.$auth.logout()
        },
    }
}
</script>

<style scoped></style>