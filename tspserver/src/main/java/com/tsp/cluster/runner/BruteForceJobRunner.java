package com.tsp.cluster.runner;

import com.tsp.cluster.WorkerManager;
import com.tsp.cluster.handler.BruteForceJobHandler;
import com.tsp.cluster.instance.HardcodedInstanceProvider;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.job.JobContext;
import com.tsp.cluster.job.JobQueue;
import com.tsp.cluster.task.provider.BruteForceTaskProvider;

import java.net.ServerSocket;
import java.util.concurrent.CyclicBarrier;

public class BruteForceJobRunner implements Runnable {
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private WorkerManager workerManager;

    public BruteForceJobRunner(WorkerManager manager) {
        this.workerManager = manager;
    }

    @Override
    public void run() {
        workerManager.openRegistration();
        workerManager.registerWorkers();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final CyclicBarrier barrier = new CyclicBarrier(workerManager.workers.size(), JobQueue::handleJobFinish);
        workerManager.workers.forEach(
                (id, worker) -> new Thread(
                        new BruteForceJobHandler(worker.getSocket(), barrier)
                ).start()
        );

    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stopRunner() {
        this.isStopped = true;
    }


}
