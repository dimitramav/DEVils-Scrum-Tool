# Server Implementation
The back-end of DEVils-Scrum-Tool is created with Spring Boot, using Gradle as build tool and Hibernate
to connect with the PostgreSQL database. It runs on an embedded Tomcat server.

## Demo with In-Memory Database

### Build and Run Server with an Embedded H2 In-Memory Database
	./gradlew bootRun --args='--spring.profiles.active=test'

#### Default imported users on test-profile for login (email / password)
	- abcd@ef.com   / abcd1234!
	- abcd@efg.com  / abcd1234!
	- abcd@efgh.com / abcd1234!

## Production - Local Development steps

### Install locally a PostgreSQL Database (for production)
1. Install postgresql
2. Create a new database with name `scrumtool`:
	`psql> CREATE DATABASE scrumtool;`
3. Then log in (may need root privileges):
	`sudo -u postgres psql -d scrumtool`
3. Import the database (./src/main/resources/schema.sql)
	`psql> \i /absolute/path/to/schema.sql`

### Build and Run Server
	./gradlew bootRun

### Build (without time-consuming tests)
	./gradlew build -x test

### Spring Boot Integration Testing (JUnit5 & Mockito)
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

## Up and Running!
Check it on http://localhost:8765/api/hello (Message: "Greetings from Spring Boot!")
- Credentials (such as ports) for server and database are defined in `application.properties` file
- For testing purposes, `application-test.properties` is loaded (spring.profiles.active=test)
- On integration tests, an embedded h2 database is used (created by `schema.sql`, populated by `data.sql`)
- Use of docker-compose in order to create associated containers for server and database
- Spring Dependencies can be found on build.gradle
