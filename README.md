# DEVils-Scrum-Tool
Web Application for Software Project Management based on Scrum Methodology. This platform helps teams to implement software projects based on agile methodology (Scrum) via sprints, epics, user stories, tasks and issues. Deploying DEVils-Scrum-Tool on cloud platforms like Heroku (check instructions) is very easy, making it a perfect free and private application for teams to work with on their own projects! You may try it online, on https://devils-scrumtool.herokuapp.com

## Environment
This web application is created using Spring Boot and Gradle for build in the back-end, while the front-end is implemented on Vuejs framework. Both implementations are fully independenent. Unit and integration tests have been implemented on client and server respectively. For dependencies, check the README.md files in each directory.

### Prerequisites
- You need to install `nodejs` (for client), `java 11` (for server) and `postgresql` (for database)
- Another option is to install `docker`, so as to run client, server and database as containers
- For production, a heroku account is recommended, where both front-end and back-end can be deployed

## Build and Run Server
- In server directory, follow the instructions given on [Server README](./server/README.md) in order to install
the database and also test, build and run the back-end, both locally and online (for production).

## Build and Run Client
- Just as above, move onto client folder, open a new bash instance and follow the instructions, depending on what kind of deployment you want (local, docker, heroku). Read the [Client README](./client/README.md) for more.

## Documentation
Check the [docs](./docs) directory for deeper understanding of platform's functionality.
