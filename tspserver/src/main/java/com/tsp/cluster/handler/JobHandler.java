package com.tsp.cluster.handler;

import com.google.gson.Gson;
import com.tsp.cluster.job.JobContext;

import java.io.*;
import java.net.Socket;

public class JobHandler implements Runnable {
    protected Socket clientSocket;
    protected JobContext jobContext;

    public JobHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    JobHandler(Socket clientSocket, JobContext jobContext) {
        this.clientSocket = clientSocket;
        this.jobContext = jobContext;
    }

    void sendStopMessage() {
        try {
            OutputStream output = clientSocket.getOutputStream();
            String str;
            PrintWriter pw = new PrintWriter(output, true);
            pw.println("STOP");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendContext() {
        try {
            OutputStream output = clientSocket.getOutputStream();
            String str;
            PrintWriter pw = new PrintWriter(output, true);
            Gson gson = new Gson();
            pw.println(gson.toJson(jobContext.getProblemData()));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {};
}
