package com.tsp;


import com.tsp.cluster.job.BruteForceJobContext;
import com.tsp.cluster.runner.BruteForceJobRunner;
import com.tsp.cluster.instance.HardcodedInstanceProvider;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.task.provider.BruteForceTaskProvider;
import com.tsp.service.SolvingThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

    @Autowired
    SolvingThread s;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        /*
        ProblemInstance problemInstance = new HardcodedInstanceProvider().getProblemInstance();
        BruteForceTaskProvider bftp = new BruteForceTaskProvider(problemInstance.getGraph());
        BruteForceJobContext context = new BruteForceJobContext(bftp);
        BruteForceJobRunner rn = new BruteForceJobRunner(context);
        new Thread(rn).start();
        System.out.println("Starting Server");
        */

    }
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        s.start();
    }
}