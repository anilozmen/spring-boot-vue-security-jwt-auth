FROM node:18.16.0-slim

WORKDIR /app

COPY package*.json .

RUN npm i --silent

COPY . .

RUN npm run build

EXPOSE 3000

CMD [ "npm", "start" ]