package com.komiwojazer.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(name = "workers")
public class Worker {
    private long id_worker;
    private String ip_address;
    private int port;
}
