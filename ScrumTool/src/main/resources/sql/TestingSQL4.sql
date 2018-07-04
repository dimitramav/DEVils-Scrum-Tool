use ScrumTool_db;
select count(*) from Project where idProject in (select Project_id from Project_has_User where User_id = 2) and isDone = false;
set @orisma = 2;
set @num_stoixeiwn = 4;
set @start = ((@orisma-1)*@num_stoixeiwn);
set @end = @start + 4;
prepare stmt from 'select * from Project where isDone=0 order by deadlineDate limit ?, ?';
execute stmt using @start, @num_stoixeiwn;