# Back End Implementation
The back end of DEVils-Scrum-Tool is created with Java, using gradle as build tool and MySQL for the database.

## Install Database
1. Install mysql-server
2. Create a new connection (localhost:3306)
3. Add a new user, according to app.properties:

    CREATE USER 'scrumtool'@'localhost' IDENTIFIED BY 'scrumtool';
    CREATE DATABASE scrumtool;
    GRANT ALL ON scrumtool.* TO 'scrumtool'@'localhost';
    USE scrumtool;

4. Import the database (./src/main/resources/sql/schema.sql)
    `mysql> source ./src/main/resources/sql/schema.sql; (or via workbench gui)`

## Build and Run in Jetty Server
    ./gradlew appRun

## Up and Running!
Backend runs on `http://localhost:8765/`
- URL credentials (as port 8765) are defined in `./build.gradle`
- The backend api is "/scrumtool/api/", as defined in `./src/main/webapp/WEB-INF/web.xml`
- If you want a war file, first execute `./gradlew build` and find the war file in './build/libs/'
