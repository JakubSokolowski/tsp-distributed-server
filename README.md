# tsp-distributed-server
    Postgresql
    Wersja: 11.2
### Requirements installation and setup
#### Java 1.8
    sudo apt install openjdk-8-jdk
#### Maven
    sudo apt-get install maven
#### Install Postgress and pgadmin4 (tested on Ubuntu 18.04)
    sudo apt-get install curl ca-certificates
    curl https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -    
    sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt/ $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
    sudo apt-get update
    sudo apt-get install postgresql-11 pgadmin4
#### Setup pgadmin4
    sudo -u postgres psql postgres
    alter user postgres with password 'postgres';
Run pgAdmin 4, and using add server dialog create local server with adress `localhost`, user `postgres` and password `postgres`.
