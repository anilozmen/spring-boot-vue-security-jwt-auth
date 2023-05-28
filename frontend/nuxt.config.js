export default {
  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: "blog-app",
    htmlAttrs: {
      lang: "en",
    },
    meta: [
      { charset: "utf-8" },
      { name: "viewport", content: "width=device-width, initial-scale=1" },
      { hid: "description", name: "description", content: "" },
      { name: "format-detection", content: "telephone=no" },
    ],
    link: [{ rel: "icon", type: "image/x-icon", href: "/favicon.ico" }],
    script: [
      {
        src: "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js",
      },
    ],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: ["~/assets/css/style.css"],

  publicRuntimeConfig: {
    axios: {
      browserBaseURL: process.env.API_BASE_URL,
    },
  },

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [{ src: "~/plugins/scroll.js" }, { src: "~/plugins/filter.js" }],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [],
  // Modules: https://go.nuxtjs.dev/config-modules
  modules: ["@nuxtjs/axios", "@nuxtjs/auth-next"],
  auth: {
    localStorage: false,
    cookie: {
      options: {
        expires: 7,
      },
    },
    strategies: {
      local: {
        scheme: "refresh",
        token: {
          property: "access_token",
          maxAge: 86400000,
          required: true,
        },
        refreshToken: {
          property: "refresh_token",
          maxAge: 604800000,
        },
        user: {
          property: "",
        },
        endpoints: {
          login: { url: "/auth/login", method: "post" },
          logout: { url: "/auth/logout", method: "post" },
          refresh: { url: "/auth/refresh-token", method: "post" },
          user: false,
        },
      },
    },
    redirect: {
      logout: "/login",
    },
  },
  router: {
    middleware: ["auth"],
  },
  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {},
};
