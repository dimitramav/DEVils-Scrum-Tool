# Server Implementation
The back end of DEVils-Scrum-Tool is created with Spring Boot, using Gradle as build tool and Hibernate
to connect with the PostgreSQL database. It runs on an embedded Tomcat server.

## Testing - In-Memory Database

### Build and Run Server with an Embedded H2 In-Memory Database (for testing)
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
3. Then log in (with root privileges):
	`sudo -u postgres psql -d scrumtool`
3. Import the database (./src/main/resources/schema.sql)
	`psql> \i /absolute/path/to/schema.sql`

### Build and Run Server
	./gradlew bootRun

### Build (without time consuming tests)
	./gradlew build -x test

### Spring Boot Integration Testing (JUnit5 & Mockito)
	./gradlew test

### Format the code
	./gradlew spotlessApply

## Up and Running!
Your server runs on http://localhost:8765/api
- Credentials (such as ports) for server and database are defined in `application.properties` file
- For testing purposes, `application-test.properties` is loaded (spring.profiles.active=test)
- On integration tests, an embedded h2 database is used (created by `schema.sql`, populated by `data.sql`)
- Spring Dependencies can be found on build.gradle
