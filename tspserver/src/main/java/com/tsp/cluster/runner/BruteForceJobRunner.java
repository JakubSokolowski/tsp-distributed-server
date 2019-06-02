package com.tsp.cluster.runner;

import com.tsp.cluster.WorkerManager;
import com.tsp.cluster.handler.BruteForceJobHandler;
import com.tsp.cluster.job.JobQueue;

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
        try {
            workerManager.openRegistration();
            new Thread(() -> workerManager.registerWorkers()).start();
            Thread.sleep(20000);
            workerManager.closeRegistration();

            final CyclicBarrier barrier = new CyclicBarrier(workerManager.workers.size(), JobQueue::updateCurrentJob);

            workerManager.workers.forEach(
                    (id, worker) -> new Thread(
                            new BruteForceJobHandler(worker.getSocket(), barrier)
                    ).start()
            );

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stopRunner() {
        this.isStopped = true;
    }


}
