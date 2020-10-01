# Build Stage

# use the nodejs (lts alpine) docker image
FROM node:lts-alpine as build-stage
# work on a new "app" directory
WORKDIR /app
# copy package json files into app directory
COPY package*.json ./
# install npm dependencies of scrumtool client
RUN npm install
# copy all files to the image's filesystem
COPY . .
# compile and minify artifact for production
RUN npm run build

# Production Stage

# use the nginx server (stable alpine) docker image
FROM nginx:stable-alpine as production-stage
# app is container's working dir
RUN mkdir /app
# copy from build stage the artifact (dist) to wroking directory
COPY --from=build-stage /app/dist /app
# copy the custom nginx configuration on its default directory
COPY nginx.conf /etc/nginx/nginx.conf
