# Blog with Spring Security, Spring Boot and Vue.js

This GitHub repository contains a Blog App developed using Spring Boot for the backend and Vue.js for the frontend. The application implements JWT authentication for secure user access. The repository serves as a valuable resource for developers seeking to understand the integration of Spring Boot and Vue.js, as well as the implementation of JWT authentication in a real-world application.

## Tech Stack
* Vue.js + Nuxt.js, nuxt/auth
* Spring Boot, Spring Security JWT w/ Refresh Token
* PostgreSQL
* [Clean Blog (Bootstrap Template)](https://startbootstrap.com/theme/clean-blog)


## Installation

Generate a Encryption Key =>
[Link](https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx)

```bash
Insert your encryption key into docker-compose.yml file

...
environment:
  ...
  JWT_SECRET_KEY: <INSERT-YOUR-SECRET-KEY>
  ...
...
```

Clone the project

```bash
  git clone https://github.com/anilozmen/spring-boot-vue-security-jwt-auth
```

Go to the project directory

```bash
  cd spring-boot-vue-security-jwt-auth
```

Run with docker-compose

```bash
  docker-compose up -d 
```

