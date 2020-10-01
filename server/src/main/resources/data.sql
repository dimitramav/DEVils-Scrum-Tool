/* Import some default values in database for testing */

/* users id: 1, 2, 3 */
insert into users (email, username, firstname, lastname, password, is_admin, num_projects)
    values ('abcd@efg', 'abcdefg', 'abcd', 'efg',
        '$2a$10$dv3.ubdVYNQOKsLbwMvixeUJOjGQc7mZTMSRT44gXfVsAgNh4sl3q', false, 1);
insert into users (email, username, firstname, lastname, password, is_admin, num_projects)
    values ('abcd@ef', 'abcdef', 'abcd', 'ef',
        '$2a$10$SiwP1VKR5sDrRIblcxGy7u2ihRTFdXaezIqDxJWLOFbZMWI.4cGLy', false, 2);
insert into users (email, username, firstname, lastname, password, is_admin, num_projects)
    values ('abcd@efgh', 'abcdefgh', 'abcd', 'efgh',
        '$2a$10$6VUQx/eLZyNo0WvXrEyCK.ak2jqIb98LvREpJiVxbJvMNb6zs2OYy', false, 1);

/* projects id: 1, 2 */
insert into projects (title, is_done, deadline_date) values ('test project 1', false, '2020-08-16');
insert into projects (title, is_done, deadline_date) values ('test project 2', false, '2020-08-17');

/* users - projects relation (project team members) */
insert into user_has_project (user_id, project_id, role) values (2, 1, 'Product Owner');
insert into user_has_project (user_id, project_id, role) values (1, 2, 'Product Owner');
insert into user_has_project (user_id, project_id, role) values (2, 2, 'Scrum Master');
insert into user_has_project (user_id, project_id, role) values (3, 2, 'Developer');

/* sprints id: 1 */
insert into sprints (deadline_date, goal, plan, is_current, num_sprint, project_id)
    values ('2020-08-06', 'testing', 'Test Sprint', true, 1, 2);

/* pbis id: 1, 2, 3 */
insert into pbis (title, description, priority, is_epic, project_id)
    values ('Test Epic', 'some desc', 2, true, 2);
insert into pbis (title, description, priority, is_epic, project_id, sprint_id, epic_id)
    values ('Test Story 1', 'first', 3, false, 2, 1, 1);
insert into pbis (title, description, priority, is_epic, project_id, epic_id)
    values ('Test Story 2', 'second', 3, false, 2, 1);

/* tasks id: 1, 2 */
insert into tasks (description, state, pbi_id) values ('Test Task 1', 2, 2);
insert into tasks (description, state, pbi_id) values ('Test Task 2', 1, 2);

/* issues id: 1 */
insert into issues (description, task_id) values ('Test Issue', 1);
