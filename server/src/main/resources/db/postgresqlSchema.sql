/* SQL script to import the schema on 'scrumtool' database for postgres */

/* Drop tables if exist */
DROP TABLE IF EXISTS issues;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS pbis;
DROP TABLE IF EXISTS sprints;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS project_has_user;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;


/* Users */
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
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
    is_admin BOOLEAN NOT NULL,
    num_projects INT NOT NULL
);


/* Projects */
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    title CHAR(45) NOT NULL,
    is_done BOOLEAN NOT NULL,
    deadline_date DATE DEFAULT NULL
);


/* Intermediate table for n2n relation between Users and Projects */
CREATE TABLE project_has_user (
    project_id INT NOT NULL REFERENCES projects(id),
    user_id INT NOT NULL REFERENCES users(id),
    role CHAR(20) NOT NULL
);


/* Notifications */
CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    project_id INT DEFAULT NULL REFERENCES projects(id),
    project_title CHAR(45) DEFAULT NULL,
    role CHAR(20) DEFAULT NULL,
    from_username CHAR(45) DEFAULT NULL REFERENCES users(username),
    to_user_email CHAR(45) NOT NULL REFERENCES users(email),
    type CHAR(45) NOT NULL,
    message CHAR(400) DEFAULT NULL
);


/* Sprint */
CREATE TABLE sprints (
    id SERIAL PRIMARY KEY,
    deadline_date DATE NOT NULL,
    goal CHAR(200) DEFAULT NULL,
    plan CHAR(500) DEFAULT NULL,
    is_current BOOLEAN NOT NULL,
    num_sprint INT DEFAULT NULL,
    project_id INT NOT NULL REFERENCES projects(id)
);


/* Product Backlog Items (epics and stories) */
CREATE TABLE pbis (
    id SERIAL PRIMARY KEY,
    title CHAR(45) NOT NULL,
    description CHAR(500) DEFAULT NULL,
    priority INT NOT NULL,
    is_epic BOOLEAN NOT NULL,
    project_id INT NOT NULL REFERENCES projects(id),
    epic_id INT DEFAULT NULL REFERENCES pbis(id) ON DELETE CASCADE,
    sprint_id INT DEFAULT NULL REFERENCES sprints(id)
);


/* Tasks */
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    description CHAR(500) NOT NULL,
    state INT NOT NULL,
    pbi_id INT NOT NULL REFERENCES pbis(id)
);
COMMENT ON COLUMN tasks.state IS '0: todo - 1: doing - 2: done.';


/* Issues */
CREATE TABLE issues (
    id SERIAL PRIMARY KEY,
    description CHAR(500) DEFAULT NULL,
    task_id INT DEFAULT NULL REFERENCES tasks(id)
);
