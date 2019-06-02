package com.tsp.cluster.handler;

import com.google.gson.Gson;
import com.tsp.cluster.job.BruteForceJobContext;
import com.tsp.cluster.job.JobQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class JobHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobHandler.class.getName());
    private final Gson gson = new Gson();
    protected Socket clientSocket;
    protected InputStream input;
    protected OutputStream output;

    protected BufferedReader br;
    protected PrintWriter pw;


    public JobHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(input));
            pw = new PrintWriter(output, true);
        } catch (IOException e) {
            LOGGER.error("Error in JobHandler constructor due to: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    void sendStopMessage() {
        pw.println("STOP");

    }

    public void sendCurrentJobContext() {
        try {
            LOGGER.info("Sending new job context");
            BruteForceJobContext ctx = (BruteForceJobContext) JobQueue.getCurrentJob();
            pw.println("NEW_CONTEXT");
            pw.println(gson.toJson(ctx.getProblemData()));
            LOGGER.info(
                    "Sent new job context. Instance ID: {} SIZE: ",
                    ctx.getProblemInstance().getId(),
                    ctx.getProblemData().getNumOfCities()
            );

            if (!(br.readLine()).equals("RECEIVED_CONTEXT")) {
                LOGGER.error("Did not receive RECEIVED_CONTEXT message from worker");
                throw new IOException("Error while sending context");
            }

        } catch (IOException ex) {
            LOGGER.error("Error while sending context due to: {}", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void notifyContextChangeNeeded() {
        pw.println("CTX_CHANGE");

    }

    void waitForReadyConfirmation() throws IOException {
        LOGGER.info("Waiting for worker READY confirmation");
        while (!br.readLine().equals("READY")) {
            continue;
        }
        LOGGER.info("Worker READY confirmation received");
    }

    public void run() {
    }
}
