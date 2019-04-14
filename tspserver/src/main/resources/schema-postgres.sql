DROP TABLE IF EXISTS cities;

--TWORZENIE TABELI Z DOSTAWCAMI
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;


create table users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username)
);

create table orders (
    id_order SERIAL PRIMARY KEY,
    cost INT,
    path VARCHAR(200),
    matrix VARCHAR(200),
    date_of_order DATE,
    username varchar(50) not null,
    foreign key (username) references users (username)
);

insert into users(username, password, enabled)values('Jan','Kowalski',true);
insert into users(username, password, enabled)values('Barbara','Witek',true);
insert into users(username, password, enabled)values('Piotrek','Nowak',true);
insert into users(username, password, enabled)values('Michal','Wygoda',true);
insert into users(username, password, enabled)values('Magdalena','Zalewska',true);

insert into authorities(username,authority)values('Jan','USER');
insert into authorities(username,authority)values('Barbara','USER');
insert into authorities(username,authority)values('Piotrek','USER');
insert into authorities(username,authority)values('Michal','USER');
insert into authorities(username,authority)values('Magdalena','USER');


