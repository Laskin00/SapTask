create database if not exists task;
use task;

drop table if exists user;
create table user (id int not null unique,name varchar(50) not null, password varchar(500) not null,email varchar(70) not null,role varchar(10) not null,sessionToken varchar(20),createdAt  datetime default current_timestamp,primary key(id));

drop table if exists product;
create table product(id int not null unique, name varchar(100) not null unique, quantity int not null, minimalPrice decimal(6,2) not null, actualPrice decimal(6,2) not null, blackFridaySalePercentage decimal(4,2) default 0.00, blackFriday bool default false, primary key(id));

drop table if exists sale;
create table sales(id int not null unique, value decimal(12,2), createdAt  datetime default current_timestamp, primary key(id));






