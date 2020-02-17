# SapTask
How to setup the database:
mysql -u root -p
CREATE USER 'task'@'%' IDENTIFIED BY 'Task123!';
grant all privileges on task.* to 'task'@'localhost' identified by 'Task123!';
FLUSH PRIVILEGES;
