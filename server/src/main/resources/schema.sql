/* SQL script to import the schema on 'scrumtool' database for postgres */

/* Drop tables if exist */
DROP TABLE IF EXISTS issues;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS pbis;
DROP TABLE IF EXISTS sprints;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS user_has_project;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;


/* Users */
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR NOT NULL UNIQUE,
    username VARCHAR NOT NULL UNIQUE,
    firstname VARCHAR NOT NULL,
    lastname VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    country VARCHAR DEFAULT NULL,
    job VARCHAR DEFAULT NULL,
    company VARCHAR DEFAULT NULL,
    description TEXT DEFAULT NULL,
    bio TEXT DEFAULT NULL,
    photo TEXT DEFAULT NULL,
    is_admin BOOLEAN NOT NULL,
    num_projects INT NOT NULL
);


/* Projects */
CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    is_done BOOLEAN NOT NULL,
    deadline_date DATE DEFAULT NULL
);


/* Intermediate table for n2n relation between Users and Projects */
CREATE TABLE user_has_project (
    user_id INT NOT NULL REFERENCES users(id),
    project_id INT NOT NULL REFERENCES projects(id),
    role VARCHAR NOT NULL
);


/* Notifications */
CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    project_id INT DEFAULT NULL REFERENCES projects(id) ON DELETE CASCADE,
    project_title VARCHAR DEFAULT NULL,
    role VARCHAR DEFAULT NULL,
    from_username VARCHAR DEFAULT NULL REFERENCES users(username),
    to_user_email VARCHAR NOT NULL REFERENCES users(email),
    type VARCHAR NOT NULL,
    message TEXT DEFAULT NULL
);


/* Sprint */
CREATE TABLE sprints (
    id SERIAL PRIMARY KEY,
    deadline_date DATE NOT NULL,
    goal TEXT DEFAULT NULL,
    plan TEXT DEFAULT NULL,
    is_current BOOLEAN NOT NULL,
    num_sprint INT DEFAULT NULL,
    project_id INT NOT NULL REFERENCES projects(id)
);


/* Product Backlog Items (epics and stories) */
CREATE TABLE pbis (
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    description VARCHAR DEFAULT NULL,
    priority INT NOT NULL,
    is_epic BOOLEAN NOT NULL,
    project_id INT NOT NULL REFERENCES projects(id),
    sprint_id INT DEFAULT NULL REFERENCES sprints(id),
    epic_id INT DEFAULT NULL REFERENCES pbis(id)
);


/* Tasks */
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    description VARCHAR NOT NULL,
    state INT NOT NULL,
    pbi_id INT NOT NULL REFERENCES pbis(id)
);
COMMENT ON COLUMN tasks.state IS '0: todo - 1: doing - 2: done.';


/* Issues */
CREATE TABLE issues (
    id SERIAL PRIMARY KEY,
    description VARCHAR DEFAULT NULL,
    task_id INT DEFAULT NULL REFERENCES tasks(id)
);
