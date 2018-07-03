use ScrumTool_db;
set @orisma = 1;
set @num_stoixeiwn = 4;
set @start = ((@orisma-1)*@num_stoixeiwn);
set @end = @start + 4;
prepare stmt from 'select * from Project where isDone=0 order by deadlineDate limit ?, ?';
execute stmt using @start, @end;
#select * from Project where isDone=0 order by deadlineDate;