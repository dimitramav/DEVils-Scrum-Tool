/* Import some default values in database for testing */

insert into users (id, email, username, firstname, lastname, password, is_admin, num_projects)
    values (1, 'abcd@efg', 'abcdefg', 'abcd', 'efg',
        '$2a$10$dv3.ubdVYNQOKsLbwMvixeUJOjGQc7mZTMSRT44gXfVsAgNh4sl3q', false, 1);
insert into users (id, email, username, firstname, lastname, password, is_admin, num_projects)
    values (2, 'abcd@ef', 'abcdef', 'abcd', 'ef',
        '$2a$10$SiwP1VKR5sDrRIblcxGy7u2ihRTFdXaezIqDxJWLOFbZMWI.4cGLy', false, 2);
insert into users (id, email, username, firstname, lastname, password, is_admin, num_projects)
    values (3, 'abcd@efgh', 'abcdefgh', 'abcd', 'efgh',
        '$2a$10$6VUQx/eLZyNo0WvXrEyCK.ak2jqIb98LvREpJiVxbJvMNb6zs2OYy', false, 1);

insert into projects (id, title, is_done, deadline_date) values (4, 'test project 1', false, '2020-08-16');
insert into projects (id, title, is_done, deadline_date) values (5, 'test project 2', false, '2020-08-17');

insert into user_has_project (user_id, project_id, role) values (2, 4, 'Product Owner');
insert into user_has_project (user_id, project_id, role) values (1, 5, 'Product Owner');
insert into user_has_project (user_id, project_id, role) values (2, 5, 'Scrum Master');
insert into user_has_project (user_id, project_id, role) values (3, 5, 'Developer');

insert into sprints (id, deadline_date, goal, plan, is_current, num_sprint, project_id)
    values (6, '2020-08-06', 'testing', 'Test Sprint', true, 1, 5);

insert into pbis (id, title, description, priority, is_epic, project_id)
    values (7, 'Test Epic', 'some desc', 2, true, 5);
insert into pbis (id, title, description, priority, is_epic, project_id, sprint_id, epic_id)
    values (8, 'Test Story 1', 'first', 3, false, 5, 6, 7);
insert into pbis (id, title, description, priority, is_epic, project_id, epic_id)
    values (9, 'Test Story 2', 'second', 3, false, 5, 7);

insert into tasks (id, description, state, pbi_id) values (10, 'Test Task 1', 2, 8);
insert into tasks (id, description, state, pbi_id) values (11, 'Test Task 2', 1, 8);

insert into issues (id, description, task_id) values (12, 'Test Issue', 10);
