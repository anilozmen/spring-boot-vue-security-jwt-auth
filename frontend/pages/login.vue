<template>
    <main class="mb-4">
        <div class="container px-4 px-lg-5">
            <div class="row gx-4 gx-lg-5 justify-content-center">
                <div class="col-md-10 col-lg-8 col-xl-7">
                    <div class="my-5">
                        <form id="contactForm" @submit.prevent="userLogin">
                            <div class="form-floating">
                                <input class="form-control" id="email" type="email" placeholder="Enter your email..."
                                    v-model="login.email" />
                                <label for="email">Email address</label>
                            </div>
                            <div class="form-floating">
                                <input class="form-control" id="password" type="password"
                                    placeholder="Enter your password..." v-model="login.password" />
                                <label for="password">Password</label>
                            </div>
                            <div class="alert alert-danger" role="alert" v-if="hasErrors">
                                {{ errorMessage }}
                            </div>
                            <br />
                            <button class="btn btn-primary text-uppercase" type="submit">Login</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>
  
<script>

export default {
    name: 'LoginPage',
    auth: 'guest',
    data() {
        return {
            login: {
                email: '',
                password: ''
            },
            errorMessage: '',
            hasErrors: false,
        }
    },
    methods: {
        async userLogin() {
            try {
                let response = await this.$auth.loginWith('local', { data: this.login });
                if (response.status == 200) {

                    let accessToken = response.data.access_token;
                    let refreshToken = response.data.refresh_token;


                    this.$auth.setUserToken(accessToken, refreshToken);
                    this.$auth.setUser(response.data);

                    this.hasErrors = false;
                    this.errorMessage = '';
                } else {
                    this.hasErrors = true;
                    this.errorMessage = response.data.message;
                }
            } catch (error) {
                this.hasErrors = true;
                this.errorMessage = error?.response?.data?.message ?? error;
            }
        },
    },
    async asyncData({ store }) {
        await store.commit('post/SET_SELECTED_POST', {});
        await store.commit('SET_TITLE', 'Login');
    }
}
</script>
  