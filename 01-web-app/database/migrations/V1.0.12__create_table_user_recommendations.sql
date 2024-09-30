-- Initial creation of user_recommendations table

create table user_recommendations (
    username varchar(50) not null,
    car_id int not null,
    relevance decimal(10,9) not null default 0.0,
    primary key (username, car_id)
);