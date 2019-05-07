DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS orders;
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

create table orders (
    order_id SERIAL PRIMARY KEY,
    cost INT,
    tour VARCHAR(200),
    matrix VARCHAR(200),
    date_of_order DATE,
    username varchar(50) not null,
    foreign key (username) references users (username)
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

insert into authorities(username,authority)values('Janek','USER');
insert into authorities(username,authority)values('Barbara','USER');
insert into authorities(username,authority)values('Piotrek','USER');
insert into authorities(username,authority)values('Michal','USER');
insert into authorities(username,authority)values('Magdalena','USER');

insert into orders (order_id,cost,tour,matrix,date_of_order,username)values(1,100,'01230','0234103412041230','2019-04-04','Janek');
