create database eisetasks;
create user eisetasks_admin with encrypted password 'eisetasks_admin_pass';
grant all privileges on database eisetasks to eisetasks_admin;