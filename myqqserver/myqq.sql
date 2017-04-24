create database myqq;
use myqq;
create table user (
user_id int primary key auto_increment,
user_name varchar(20) not null unique,
user_passwd varchar(20) not null
);

create table chat (
chat_id int primary key auto_increment,
user_name varchar(20) not null,
chat_content text not null
);