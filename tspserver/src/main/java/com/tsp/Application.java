package com.tsp;


import com.tsp.cluster.WorkerManager;
import com.tsp.cluster.runner.BruteForceJobRunner;
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