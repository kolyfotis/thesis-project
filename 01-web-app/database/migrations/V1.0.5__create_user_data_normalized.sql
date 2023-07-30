-- Initial creation of user_data_normalized

create table user_data_normalized (
    id int not null auto_increment,
    -- some browsers ID can be longer than 255
    -- text maximum size is ~65,535 (64KB)
    username text not null comment
        'Username or Browser id to be stored here. Some browsers ID can be longer than 255. TEXT maximum length is ~65,535 (64KB)',
    field_name varchar(255) not null,
    field_value varchar(255) not null,
    time_spent decimal(10,9) not null default 0.0,
    primary key (id)
);