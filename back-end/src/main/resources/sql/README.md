## Information
- The database ships on mysql (install mysql-server)
- Import schema.sql for automatic creation of scrumtool database and data insertion.
- TestingSQL1.sql (as 2, 3, 4) are testing queries that should not be used
  (they exist just for debugging reasons).
- There must be a MySQL connection and a user with username:scrumtool and password:scrumtool
  for proper communication (see app.properties in `src/main/resources/scrumtool`).

## Install MySQL Server, connect and create the database
    CREATE USER 'scrumtool'@'localhost' IDENTIFIED BY 'scrumtool';
    CREATE DATABASE scrumtool;
    GRANT ALL ON scrumtool.* TO 'scrumtool'@'localhost';
    USE scrumtool;
    source ./src/main/resources/sql/schema.sql; (from bash, else import file using workbench)

## Existing Users
- Emails / Passwords for existing Users (Log in, passwords encrypted in database):
  - orestis@gmail.com / orestis7#
  - kostas@gmail.com  / kostas7#
  - markos@gmail.com  / markos7#
  - dimitra@gmail.com / dimitra7#
