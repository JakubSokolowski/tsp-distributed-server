package com.tsp.cluster.handler;

import com.google.gson.Gson;
import com.tsp.cluster.job.JobContext;
import com.tsp.cluster.task.context.BruteForceTaskContext;

import java.io.*;
import java.net.Socket;

public class BruteForceJobHandler extends JobHandler {
    public BruteForceJobHandler(Socket clientSocket, JobContext jobContext) {
        super(clientSocket, jobContext);
    }

    private Gson gson = new Gson();
    @Override
    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            String str;

            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            PrintWriter pw = new PrintWriter(output, true);

            int counter = 0;

            sendContext();
            while((str = br.readLine()) != null) {
                System.out.println("Server received " + str);
                Thread.sleep(1000);
                if(!jobContext.areAnyTasksAvailable()) {
                    sendStopMessage();
                    break;
                }
                BruteForceTaskContext task = (BruteForceTaskContext) jobContext.getNextAvailableTask();
                str = gson.toJson(task);
                counter++;
                System.out.println(str);
                pw.println(str);
                System.out.println("Server send message...");
                long time = System.currentTimeMillis();
                System.out.println("Request processed: " + time);
            }
            System.out.println("Finished communication");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
