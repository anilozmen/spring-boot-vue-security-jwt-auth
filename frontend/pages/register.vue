<template>
    <main class="mb-4">
        <div class="container px-4 px-lg-5">
            <div class="row gx-4 gx-lg-5 justify-content-center">
                <div class="col-md-10 col-lg-8 col-xl-7">
                    <div class="my-5">
                        <form @submit.prevent="userRegister">
                            <div class="form-floating">
                                <input class="form-control" id="first_name" type="text"
                                    placeholder="Enter your first name..." v-model="form.first_name" />
                                <label for="first_name">First Name</label>
                                <div class="invalid-feedback" data-sb-feedback="first_name:required">First name is required.
                                </div>
                            </div>
                            <div class="form-floating">
                                <input class="form-control" id="last" type="text" placeholder="Enter your last name..."
                                    v-model="form.last_name" />
                                <label for="last_name">Last Name</label>
                                <div class="invalid-feedback" data-sb-feedback="last:required">Last name is required.</div>
                            </div>
                            <div class="form-floating">
                                <input class="form-control" id="email" type="email" placeholder="Enter your email..."
                                    v-model="form.email" />
                                <label for="email">Email address</label>
                                <div class="invalid-feedback" data-sb-feedback="email:required">Email is required.</div>
                            </div>
                            <div class="form-floating">
                                <input class="form-control" id="password" type="password"
                                    placeholder="Enter your password..." v-model="form.password" />
                                <label for="password">Password</label>
                                <div class="invalid-feedback" data-sb-feedback="password:required">Password is required.
                                </div>
                            </div>
                            <br />
                            <div class="alert alert-danger" role="alert" v-if="hasErrors">
                                {{ errorMessage }}
                            </div>
                            <button class="btn btn-primary text-uppercase" type="submit">Register</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>
  
<script>

export default {
    name: 'RegisterPage',
    auth: 'guest',
    data() {
        return {
            form: {
                first_name: '',
                last_name: '',
                email: '',
                password: '',
            },
            hasErrors: false,
            errorMessage: '',
        }
    },
    methods: {
        async userRegister() {
            try {
                this.$axios.post('auth/register', this.form).then(resp => {

                    if (resp.status == 201) {

                        let accessToken = resp.data.access_token;
                        let refreshToken = resp.data.refresh_token;

                        this.$auth.setUserToken(accessToken, refreshToken);

                        this.$auth.setUser(resp.data);

                        this.hasErrors = false;
                        this.errorMessage = '';
                    } else {
                        this.hasErrors = true;
                        this.errorMessage = response.data.message;
                    }
                });
            } catch (error) {
                this.hasErrors = true;
                this.errorMessage = error?.response?.data?.message ?? error;
            }
        }
    },
    async asyncData({ store }) {
        await store.commit('post/SET_SELECTED_POST', {});
        await store.commit('SET_TITLE', 'Register');
    },
}
</script>
  