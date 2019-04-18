
Прежде чем выполнить запуск приложения, необходимо в postgresql создать
бд eisetasks, пользователя eisetask_admin с паролем eisetasks_admin_pass и выдать ему разрешения на эту бд.
Для этого можно воспользоваться следующими командами:
create database eisetasks;
create user eisetasks_admin with encrypted password 'eisetasks_admin_pass';
grant all privileges on database eisetasks to eisetasks_admin;


