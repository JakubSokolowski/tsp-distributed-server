package com.tsp.bean;

import javax.persistence.*;
import java.util.Objects;

@Entity()
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_worker")
    private long idWorker;
    @Column(name = "ip_address")
    private String ipAddress;

    private int port;

    public long getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(long idWorker) {
        this.idWorker = idWorker;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return idWorker == worker.idWorker &&
                port == worker.port &&
                Objects.equals(ipAddress, worker.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWorker, ipAddress, port);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "idWorker=" + idWorker +
                ", ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                '}';
    }
}
