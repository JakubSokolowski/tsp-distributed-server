package com.tsp.cluster.runner;

import com.tsp.cluster.job.BruteForceJobContext;
import com.tsp.cluster.handler.BruteForceJobHandler;
import com.tsp.cluster.handler.JobHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BruteForceJobRunner implements Runnable {
    private BruteForceJobContext jobContext;
    private int serverPort = 42069;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private Thread runningThread;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public BruteForceJobRunner(BruteForceJobContext jobContext) {
        this.jobContext = jobContext;
    }

    @Override
    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }

        openServerSocket();
        while(! isStopped()){
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server is stopped.") ;
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            System.out.println(jobContext.getNumOfAvailableTasks());
            if(!jobContext.areAnyTasksAvailable()) stop();
            this.threadPool.execute(new BruteForceJobHandler(clientSocket, jobContext));


        }
        this.threadPool.shutdown();
        System.out.println("Server Stopped.");
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + Integer.toString(serverPort), e);
        }
    }

}
