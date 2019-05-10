package com.tsp.cluster.handler;

import com.google.gson.Gson;
import com.tsp.cluster.common.Solution;
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
            while(!(str = br.readLine()).equals("READY")){
                continue;
            }
            sendContext();
            if(!(str = br.readLine()).equals("RECEIVED_CONTEXT"))
                throw new IOException("Error while sending context");

            BruteForceTaskContext task = (BruteForceTaskContext) jobContext.getNextAvailableTask();
            str = gson.toJson(task);
            counter++;
            System.out.println(str);
            pw.println(str);

            while((str = br.readLine()) != null) {
                System.out.println("Server received " + str);
                Solution sol = gson.fromJson(str, Solution.class);
                jobContext.updateSolution(sol);
                if(!jobContext.areAnyTasksAvailable()) {
                    sendStopMessage();
                    System.out.println(gson.toJson(jobContext.getBestSolution()));
                    break;
                }
                task = (BruteForceTaskContext) jobContext.getNextAvailableTask();
                str = gson.toJson(task);
                counter++;
                System.out.println(str);
                pw.println(str);
                System.out.println("Server send message...");
                long time = System.currentTimeMillis();
                System.out.println("Request processed: " + time);
            }
            System.out.println("Finished communication");

        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
