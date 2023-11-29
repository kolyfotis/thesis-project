-- Add normalized_time_spent to user_data

alter table user_data
    add column normalized_time_spent decimal(10,9) not null default 0.0;
