package com.tsp.cluster;

import com.tsp.bean.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorkerManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerManager.class.getName());
    public Map<Long, Worker> workers = Collections.synchronizedMap(new HashMap<>());
    private boolean isRegistrationOpen = false;
    private ServerSocket serverSocket = null;

    public WorkerManager() {
    }

    public void registerWorkers() {
        openServerSocket();
        while (isRegistrationOpen()) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
                Worker worker = new Worker(clientSocket);
                addWorker(worker);
                int maxWorkerNum = 1;
                if (workers.size() >= maxWorkerNum) {
                    closeRegistration();
                }
            } catch (IOException e) {
                LOGGER.error("Error in registration due to: {}", e.getMessage());
                throw new RuntimeException("Error accepting client connection", e);
            }
        }

    }

    public boolean isRegistrationOpen() {
        return isRegistrationOpen;
    }

    public synchronized void openRegistration() {
        LOGGER.info("Opened registration");
        isRegistrationOpen = true;
    }

    public synchronized void closeRegistration() throws IOException {
        LOGGER.info("Closed registration");
        isRegistrationOpen = false;
        serverSocket.close();
        serverSocket = null;
    }

    public void stopAllWorkers() {
        for (Worker worker : workers.values()) {
            worker.sendStopMessage();
        }
    }

    public Worker getWorker(long workerId) {
        return workers.get(workerId);
    }

    public void addWorker(Worker worker) {
        workers.put(worker.getIdWorker(), worker);
        LOGGER.info("Registered worker. ID:{}, IP: {}:{}", workers.size(), worker.getIpAddress(), worker.getPort());
    }

    public void removeWorker(long workerId) {
        workers.remove(workerId);
    }

    private void openServerSocket() {
        int serverPort = 42069;
        try {
            this.serverSocket = new ServerSocket(serverPort);
            LOGGER.info("Opend worker socket at port: {}", serverPort);
        } catch (IOException e) {
            LOGGER.error(
                    "Cannot open port {} due to exception {}",
                    Integer.toString(serverPort), e.getMessage()
            );
        }
    }
}
