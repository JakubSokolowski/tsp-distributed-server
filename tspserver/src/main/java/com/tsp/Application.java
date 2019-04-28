package com.tsp;


import com.tsp.cluster.job.BruteForceJobContext;
import com.tsp.cluster.runner.BruteForceJobRunner;
import com.tsp.cluster.instance.HardcodedInstanceProvider;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.task.provider.BruteForceTaskProvider;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ProblemInstance problemInstance = new HardcodedInstanceProvider().getProblemInstance();
        BruteForceTaskProvider bftp = new BruteForceTaskProvider(problemInstance.getGraph());
        BruteForceJobContext context = new BruteForceJobContext(bftp);
        BruteForceJobRunner rn = new BruteForceJobRunner(context);
        new Thread(rn).start();
        System.out.println("Starting Server");
    }
}