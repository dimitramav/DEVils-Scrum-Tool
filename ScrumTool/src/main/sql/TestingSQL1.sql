use ScrumTool_db;
select * from Project;
insert into Project (title, isDone, deadlineDate) values ('Scrum', FALSE, '2018-07-15');
insert into Project (title, isDone, deadlineDate) values ('Facebook', FALSE, '2018-08-16');
insert into Project (title, isDone, deadlineDate) values ('Twitter', FALSE, '2019-09-17');
insert into User (password, mail, firstname, lastname, isAdmin, numProjects)
	values ('garmpis', 'sdi1400025@di.uoa.gr', 'Orestis', 'Garmpis', True, 2);
insert into User (password, mail, firstname, lastname, isAdmin, numProjects)
	values ('kotronis', 'sdi1400074@di.uoa.gr', 'Kostas', 'Kotronis', False, 1);
select * from User;
insert into Project_has_User (Project_id, User_id, role) values (2, 1, 'Product Owner');
insert into Project_has_User (Project_id, User_id, role) values (3, 2, 'Developer');
select * from Project_has_User;
select * from Project where idProject in (select Project_id from Project_has_User where User_id = 1);
