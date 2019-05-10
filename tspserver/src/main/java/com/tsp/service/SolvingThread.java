package com.tsp.service;

import com.tsp.cluster.common.Algorithm;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.job.BruteForceJobContext;
import com.tsp.cluster.runner.BruteForceJobRunner;
import com.tsp.cluster.task.provider.BruteForceTaskProvider;
import com.tsp.graph.SymmetricMatrix;
import com.tsp.repository.ProblemInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

@Component
public class SolvingThread extends Thread {
    @Autowired
    ProblemInstanceRepository problemRepository;

    public SolvingThread()
    {

    }

   @Override
    public void run() {
        super.run();
        while(true)
        {
            ProblemInstance p = problemRepository.findUsolvedInstanceProblem();
            if(p != null)
            {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //ProblemInstance problemInstance = new HardcodedInstanceProvider().getProblemInstance();
                BruteForceTaskProvider bftp = new BruteForceTaskProvider(p.getGraph());
                BruteForceJobContext context = new BruteForceJobContext(bftp);
                BruteForceJobRunner rn = new BruteForceJobRunner(context);
                new Thread(rn).start();
                //Tutaj zapisz jakoś wynik albo w innym odpowiednim miejscu
                System.out.println("Starting Server");


                //p.setCost(300);
                //ProblemInstance saving = problemRepository.findOne(p.getId());
                //saving.setCost(p.getCost());
                //problemRepository.save(p);
            }
        }

    }
}
