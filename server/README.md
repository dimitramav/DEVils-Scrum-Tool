# Server Implementation
The back end of DEVils-Scrum-Tool is created with Spring Boot, using Gradle as build tool and Hibernate
to connect with the PostgreSQL database. It runs on an embedded Tomcat server.

## Install Database
1. Install postgresql
2. Create a new database with name `scrumtool`. For example (with root privileges):
	`sudo -u postgres psql -d scrumtool`
3. Import the database (./src/main/resources/db/postgresqlSchema.sql)
	`psql> \i /absolute/path/to/postgresqlSchema.sql`

## Build and Run Server
	./gradlew bootRun

## Build (without time consuming tests)
	./gradlew build -x test

## Spring Boot Testing (JUnit5 & Mockito)
	./gradlew test

## Format the code
	./gradlew spotlessApply

## Up and Running!
Your server runs on http://localhost:8765/api
- Credentials (such as ports) for server and database are defined in `application.properties` file
- Spring Dependencies can be found on build.gradle
