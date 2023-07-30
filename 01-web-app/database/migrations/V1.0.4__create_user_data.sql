-- Initial creation of user_data

create table user_data (
    id int not null auto_increment,
    -- some browsers ID can be longer than 255
    -- text maximum size is ~65,535 (64KB)
    username text not null comment
        'Username or Browser id to be stored here. Some browsers ID can be longer than 255. TEXT maximum length is ~65,535 (64KB)',
    field_name varchar(255) not null,
    field_value varchar(255) not null,
    time_spent bigint not null default 0,
    primary key (id)
);