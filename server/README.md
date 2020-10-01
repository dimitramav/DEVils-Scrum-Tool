# Server Implementation
The back-end of DEVils-Scrum-Tool is created with Spring Boot, using Gradle as build tool and Hibernate
to connect with the PostgreSQL database. Java 11 is required. Open a new bash instance to run server:

## Testing - Demo with In-Memory Database

### Build and Run Server with an Embedded H2 In-Memory Database
	./gradlew bootRun --args='--spring.profiles.active=test'

### Up and Running!
Check it on http://localhost:8765/api/hello (Message: "Greetings from Spring Boot!")

#### Default imported users on test-profile for login (email / password)
	- abcd@ef.com   / abcd1234!
	- abcd@efg.com  / abcd1234!
	- abcd@efgh.com / abcd1234!

## Development - Local installation steps

### Install locally a PostgreSQL Database
1. Install postgresql
2. Create a new database with name `scrumtool`:
	`psql> CREATE DATABASE scrumtool;`
3. Then log in (may need root privileges):
	`sudo -u postgres psql -d scrumtool`
3. Import the database (./src/main/resources/schema.sql)
	`psql> \i /absolute/path/to/schema.sql`

### Build and Run Server
	./gradlew bootRun --args='--spring.profiles.active=dev'

### Build (without time-consuming tests)
	./gradlew build -x test

### Spring Boot Integration Testing (AssertJ & Mockito)
	./gradlew test

### Format the code
	./gradlew spotlessApply

## Other Deployment options

### Docker
1. Install `docker-compose`
2. Build server and database images together with docker-compose
	`docker-compose up --no-start`
3. Start the server and database containers
	`docker-compose start`
4. Stop them with `docker-compose stop`

### Heroku (for production)
1. Login to heroku and create a new project
2. Add postgresql add-on: `heroku addons:create heroku-postgresql`
3. On `./src/main/resources/application-heroku.properties` apply `originRequestUrl` as `https://your-frontend.herokuapp.com`
4. Git add, commit and push to heroku master
5. Import schema to database: `heroku pg:psql YOUR-HEROKU-DATABASE-NAME < ./src/main/resources/schema.sql`
