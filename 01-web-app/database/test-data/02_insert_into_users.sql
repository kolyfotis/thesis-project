insert into users(username, password, enabled)
values
    ('fotis', '{bcrypt}$2a$12$aqku6n7k88UlY.JS90MG2ulMXl7vLIVpwk0EtCGEaY6u4EhsGxsUa', 1),
    ('user1', '{bcrypt}$2a$12$aqku6n7k88UlY.JS90MG2ulMXl7vLIVpwk0EtCGEaY6u4EhsGxsUa', 1),
    ('user2', '{bcrypt}$2a$12$aqku6n7k88UlY.JS90MG2ulMXl7vLIVpwk0EtCGEaY6u4EhsGxsUa', 1);

insert into authorities(username, authority)
values
    ('fotis', 'ROLE_ADMIN'),
    ('fotis', 'ROLE_USER'),
    ('user1', 'ROLE_USER'),
    ('user2', 'ROLE_USER');
