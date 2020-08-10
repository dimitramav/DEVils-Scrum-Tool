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
# copy from build stage the artifact (dist) to the default nginx serving path
#COPY --from=build-stage /app/dist /usr/share/nginx/html
# port to expose for localhost
#EXPOSE 80
# nginx running each time we start the container (daemon off)
#CMD ["nginx", "-g", "daemon off;"]

# Use custom nginx configuration (instead of default, as above -in comments-)
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf
