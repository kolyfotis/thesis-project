-- Add engine_size to cars
-- It is nullable because electric do not have an engine

alter table cars
    add column engine_size smallint unsigned default 0;
