package com.tsp.cluster.handler;

import com.google.gson.Gson;
import com.tsp.cluster.common.Solution;
import com.tsp.cluster.job.JobQueue;
import com.tsp.cluster.task.context.BruteForceTaskContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BruteForceJobHandler extends JobHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BruteForceJobHandler.class.getName());
    private final CyclicBarrier cyclicBarrier;
    private Gson gson = new Gson();

    public BruteForceJobHandler(Socket clientSocket, CyclicBarrier barrier) {
        super(clientSocket);
        cyclicBarrier = barrier;
    }

    @Override
    public void run() {
        try {
            waitForReadyConfirmation();
            sendCurrentJobContext();
            sendNextTask();

            String message;
            while ((message = br.readLine()) != null) {
                Solution sol = gson.fromJson(message, Solution.class);
                JobQueue.receiveTaskSolution(sol);
                JobQueue.updateCurrentJobProgress();
                JobQueue.saveCurrentJobState();

                if (!JobQueue.shouldContinueExecution()) {
                    try {
                        // Wait for all workers to finish their tasks in this job
                        // After all workers are finished, await() executes callback that marks current job as finished
                        // saves it's state to database, and sets viable job as currentJob.
                        // If there are no viable jobs, this callback blocks all of the worker threads
                        LOGGER.info("Worker {}:{} finished execution", clientSocket.getInetAddress(), clientSocket.getPort());
                        cyclicBarrier.await();
                        sendCurrentJobContext();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        LOGGER.info("Error waiting for workers to finish job execution due to: {}", e.getMessage());
                        e.printStackTrace();
                    }
                }
                sendNextTask();
            }
            LOGGER.info("Finished communication");

        } catch (IOException e) {
            LOGGER.info("Error in BruteForceJobHandler due to: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendNextTask() {
        LOGGER.info("Sending task to worker: {}:{}", clientSocket.getInetAddress(), clientSocket.getPort() );
        BruteForceTaskContext task = (BruteForceTaskContext) JobQueue.getCurrentJob().getNextAvailableTask();
        String str = gson.toJson(task);
        pw.println(str);
        LOGGER.info("Send task: {} to worker: {}:{}", str, clientSocket.getInetAddress(), clientSocket.getPort() );
    }
}
