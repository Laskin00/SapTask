drop database if exists task;
create database if not exists task;
use task;

drop table if exists user;
create table user (id int not null unique,name varchar(50) not null, password varchar(500) not null,email varchar(70) not null unique,role varchar(10) not null,sessionToken varchar(20),createdAt  datetime default current_timestamp,primary key(id));

drop table if exists product;
create table product(id int not null unique, name varchar(100) not null unique, quantity int not null, minimalPrice decimal(6,2) not null, actualPrice decimal(6,2) not null, blackFridayPercentage decimal(4,2) default 0.00, blackFriday bool default false, primary key(id));

drop table if exists sale;
create table sale(id int not null unique, userId int not null, productName varchar(60), value decimal(12,2), createdAt  datetime default current_timestamp,
foreign key(userId) references user(id), foreign key (productName) references product(name), primary key(id));







