# Stage 1: Build the React application
FROM node:20-alpine AS builder

WORKDIR /app
ARG REACT_APP_BACKEND_URL
ENV REACT_APP_BACKEND_URL=$REACT_APP_BACKEND_URL
COPY package*.json ./


ENV NODE_OPTIONS=--openssl-legacy-provider

COPY package*.json .
COPY .env .env
COPY public public
COPY src src
COPY ssl ssl
RUN npm install
RUN npm run build

# Stage 2: Serve the React application with Nginx
FROM nginx:alpine

# Copy the built React files from the builder stage
COPY --from=builder /app/build /usr/share/nginx/html

# Copy the Nginx configuration files
COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

# Copy SSL certificates
COPY ssl/nginx.crt /etc/nginx/ssl/nginx.crt
COPY ssl/nginx.key /etc/nginx/ssl/nginx.key

EXPOSE 80
EXPOSE 443
EXPOSE 3000

ENTRYPOINT ["nginx", "-g", "daemon off;"]
