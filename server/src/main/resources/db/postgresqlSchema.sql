/* SQL script to import the schema on 'scrumtool' database for postgres */

/* Drop tables if exist */
DROP TABLE IF EXISTS Issues;
DROP TABLE IF EXISTS Tasks;
DROP TABLE IF EXISTS PBIs;
DROP TABLE IF EXISTS Sprints;
DROP TABLE IF EXISTS Notifications;
DROP TABLE IF EXISTS Project_has_User;
DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS Users;


/* Users */
CREATE TABLE Users (
    idUser SERIAL PRIMARY KEY,
    email CHAR(45) NOT NULL UNIQUE,
    username CHAR(45) NOT NULL UNIQUE,
    firstname CHAR(45) NOT NULL,
    lastname CHAR(45) NOT NULL,
    password CHAR(300) NOT NULL,
    country CHAR(45) DEFAULT NULL,
    job CHAR(45) DEFAULT NULL,
    company CHAR(45) DEFAULT NULL,
    description CHAR(500) DEFAULT NULL,
    bio CHAR(1000) DEFAULT NULL,
    photo CHAR(200) DEFAULT NULL,
    isAdmin BOOLEAN NOT NULL,
    numProjects INT NOT NULL
);


/* Projects */
CREATE TABLE Projects (
    idProject SERIAL PRIMARY KEY,
    title CHAR(45) NOT NULL,
    isDone BOOLEAN NOT NULL,
    deadlineDate DATE DEFAULT NULL
);


/* Intermediate table for n2n relation between Users and Projects */
CREATE TABLE Project_has_User (
    Project_id INT NOT NULL REFERENCES Projects(idProject),
    User_id INT NOT NULL REFERENCES Users(idUser),
    role CHAR(20) NOT NULL
);


/* Notifications */
CREATE TABLE Notifications (
    idNotification SERIAL PRIMARY KEY,
    Project_id INT DEFAULT NULL REFERENCES Projects(idProject),
    projectTitle CHAR(45) DEFAULT NULL,
    role CHAR(20) DEFAULT NULL,
    FromUsername CHAR(45) DEFAULT NULL REFERENCES Users(username),
    ToUserEmail CHAR(45) NOT NULL REFERENCES Users(email),
    type CHAR(45) NOT NULL,
    message CHAR(400) DEFAULT NULL
);


/* Sprint */
CREATE TABLE Sprints (
    idSprint SERIAL PRIMARY KEY,
    deadlineDate DATE NOT NULL,
    goal CHAR(200) DEFAULT NULL,
    plan CHAR(500) DEFAULT NULL,
    isCurrent BOOLEAN NOT NULL,
    numSprint INT DEFAULT NULL,
    Project_id INT NOT NULL REFERENCES Projects(idProject)
);


/* Product Backlog Items (epics and stories) */
CREATE TABLE PBIs (
    idPBI SERIAL PRIMARY KEY,
    title CHAR(45) NOT NULL,
    description CHAR(500) DEFAULT NULL,
    priority INT NOT NULL,
    isEpic BOOLEAN NOT NULL,
    Project_id INT NOT NULL REFERENCES Projects(idProject),
    Epic_id INT DEFAULT NULL REFERENCES PBIs(idPBI) ON DELETE CASCADE,
    Sprint_id INT DEFAULT NULL REFERENCES Sprints(idSprint)
);


/* Tasks */
CREATE TABLE Tasks (
    idTask SERIAL PRIMARY KEY,
    description CHAR(500) NOT NULL,
    state INT NOT NULL,
    PBI_id INT NOT NULL REFERENCES PBIs(idPBI)
);
COMMENT ON COLUMN Tasks.state IS '0: todo - 1: doing - 2: done.';


/* Issues */
CREATE TABLE Issues (
    idIssue SERIAL PRIMARY KEY,
    description CHAR(500) DEFAULT NULL,
    Task_id INT DEFAULT NULL REFERENCES Tasks(idTask)
);
