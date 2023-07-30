-- Initial creation of cars

create table cars (
    id int not null auto_increment,
    make varchar(255) not null,
    model varchar(255) not null,
    body_type varchar(255) not null,
    fuel_type varchar(255) not null,
    price decimal(10,2) unsigned not null,
    horsepower smallint unsigned not null,
    gearbox varchar(255) not null,
    drivetrain varchar(255) not null,
    door_number tinyint unsigned not null,
    first_registered smallint unsigned not null,
    color varchar(255) not null,
    -- allow null for easier insertions
    image blob,
    primary key (id)
);
