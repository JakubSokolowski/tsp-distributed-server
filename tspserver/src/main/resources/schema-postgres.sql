DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS problems;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS workers;


create table users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    enabled boolean not null
);

create table authorities (
    id_authority SERIAL PRIMARY KEY,
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);

create table problems (
    problem_id SERIAL PRIMARY KEY,
    graph oid,
    tour oid,
    cost INT,
    time_of_running_in_seconds INT,
    percentage_of_progress INT,
    is_solving boolean,
    date_of_ordering DATE,
    username_of_user varchar(50) not null,
    foreign key (username_of_user) references users (username)
);

create table workers (
    worker_id SERIAL PRIMARY KEY,
    port INT,
    ip_address VARCHAR(200)
);

insert into users(username, password, enabled)values('Janek','Kowalski',true);
insert into users(username, password, enabled)values('Barbara','Witek',true);
insert into users(username, password, enabled)values('Piotrek','Nowak',true);
insert into users(username, password, enabled)values('Michal','Wygoda',true);
insert into users(username, password, enabled)values('Magdalena','Zalewska',true);
insert into users(username, password, enabled)values('admin','admin',true);

insert into authorities(username,authority)values('admin','ADMIN');
insert into authorities(username,authority)values('Janek','USER');
insert into authorities(username,authority)values('Barbara','USER');
insert into authorities(username,authority)values('Piotrek','USER');
insert into authorities(username,authority)values('Michal','USER');
insert into authorities(username,authority)values('Magdalena','USER');

