create database users;
use users;

create table user (
id int primary key auto_increment,
login varchar(60) unique,
password varchar(60),
first_name varchar(60),
last_name varchar(60),
age int,
sex enum ('man', 'woman'),
description varchar(150));
