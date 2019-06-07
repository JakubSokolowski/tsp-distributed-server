package com.tsp;


import com.tsp.cluster.WorkerManager;
import com.tsp.cluster.instance.HardcodedInstanceProvider;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.job.JobContext;
import com.tsp.cluster.job.JobQueue;
import com.tsp.cluster.runner.BruteForceJobRunner;
import com.tsp.cluster.task.provider.BruteForceTaskProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        BruteForceJobRunner runner = new BruteForceJobRunner(new WorkerManager());
        new Thread(runner).start();
    }
}