create database crud_app_javaFX;

use crud_app_javaFX;

create table students (
    id int auto_increment primary key,
    FirstName varchar(20) not null,
    LastName varchar(20) not null,
    COURSE varchar(255) not null
);

insert into students(FirstName, LastName, COURSE) values("John", "Doe", "Java");