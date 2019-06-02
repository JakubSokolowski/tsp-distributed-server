package com.tsp.bean;

import javax.persistence.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity()
@Table(name = "workers")
public class Worker {
    private static final Logger LOGGER = Logger.getLogger(Worker.class.getName());
    private static int workerCount = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_worker")
    private long idWorker;
    private int port;
    @Column(name = "ip_address")
    private String ipAddress;

    @Transient
    private Socket connectionSocket;

    public Worker(Socket socket) {
        workerCount++;
        connectionSocket = socket;
        this.idWorker = workerCount;
        this.ipAddress = socket.getInetAddress().getHostAddress();
        this.port = socket.getPort();
    }

    public void sendStopMessage() {
        try {
            OutputStream output = connectionSocket.getOutputStream();
            PrintWriter pw = new PrintWriter(output, true);
            pw.println("STOP");
            LOGGER.log(Level.FINE, "server sent stop message to worker {0}", idWorker);

        } catch (IOException ex) {
            LOGGER.log(
                    Level.SEVERE,
                    "failed to send stop message for worker {0} due to exception {1}",
                    new Object[]{idWorker, ex.getMessage()}
            );
        }
    }

    public long getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(long idWorker) {
        this.idWorker = idWorker;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public Socket getSocket() {
        return connectionSocket;
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
