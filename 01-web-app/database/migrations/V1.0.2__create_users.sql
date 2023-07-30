-- Initial creation of users

create table users (
    username varchar(50) not null,
    password char(68) not null,
    enabled tinyint(1) not null,
    primary key (username)
);
