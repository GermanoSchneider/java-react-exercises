FROM node:18-alpine
WORKDIR /note-web-app-client/
COPY public/ /note-web-app-client/public
COPY src/ /note-web-app-client/src
COPY package.json /note-web-app-client/
RUN npm install
CMD ["npm", "start"]