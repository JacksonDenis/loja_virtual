SELECT CONSTRAINT_name from information_schema.constraint_column_usage 
	WHERE TABLE_name='usuarios_acesso' AND COLUMN_name= 'acesso_id'
	and CONSTRAINT_name <> 'unique_acesso_user';
	
	
alter TABLE usuarios_acesso drop CONSTRAINT "uk8bak9jswon2id2jbunuqlfl9e"

