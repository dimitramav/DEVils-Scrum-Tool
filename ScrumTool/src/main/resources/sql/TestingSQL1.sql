use ScrumTool_db;
insert into User (password, mail, firstname, lastname, isAdmin, numProjects)
	values ('$2a$10$2VqiIm8sO1CvA65JXO9O2OWyXtwJK1Cbzj/CfiE6VmOyor.AKWcAu', 'orestis@gmail.com', 'Orestis', 'Garmpis', False, 2);
insert into User (password, mail, firstname, lastname, isAdmin, numProjects)
	values ('$2a$10$xgAzw4vx19hQxuw7jjFyku/GCT8izH27zKIdjnVZJhZg4lCh2EH1m', 'kostas@gmail.com', 'Kostas', 'Kotronis', False, 4);
insert into User (password, mail, firstname, lastname, isAdmin, numProjects)
	values ('$2a$10$FFZIXZWNB5chE3QbidHfXe/W1apJon4lMLSp.zh1YEIg5e6mpBfmC', 'markos@gmail.com', 'Markos', 'Varvagiannis', False, 3);
#select * from User;
insert into Project (title, isDone, deadlineDate) values ('Twitter', FALSE, '2019-11-17');
insert into Project (title, isDone, deadlineDate) values ('Scrum', FALSE, '2018-07-15');
insert into Project (title, isDone, deadlineDate) values ('Facebook', FALSE, '2018-09-16');
insert into Project (title, isDone, deadlineDate) values ('Trivaggos', TRUE, '2017-09-30');
#select * from Project;
insert into Project_has_User (Project_id, User_id, role) values (4, 1, 'Product Owner');
insert into Project_has_User (Project_id, User_id, role) values (2, 1, 'Developer');
insert into Project_has_User (Project_id, User_id, role) values (1, 2, 'Developer');
insert into Project_has_User (Project_id, User_id, role) values (2, 2, 'Developer');
insert into Project_has_User (Project_id, User_id, role) values (3, 2, 'Developer');
insert into Project_has_User (Project_id, User_id, role) values (4, 2, 'Scrum Master');
insert into Project_has_User (Project_id, User_id, role) values (1, 3, 'Developer');
insert into Project_has_User (Project_id, User_id, role) values (2, 3, 'Developer');
insert into Project_has_User (Project_id, User_id, role) values (3, 3, 'Developer');
#select * from Project_has_User;
#select * from Project where idProject in (select Project_id from Project_has_User where User_id = 1);
