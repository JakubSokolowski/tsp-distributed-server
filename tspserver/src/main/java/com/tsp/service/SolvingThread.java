package com.tsp.service;

import com.tsp.cluster.common.Algorithm;
import com.tsp.cluster.instance.HardcodedInstanceProvider;
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
            if(p != null && p.getCost() < 0 && problemRepository.findProblemWhichIsSolvingAtTheMoment() == null)
            {
                System.out.println("New Task!");

                p.setSolving(true);
                problemRepository.save(p);

                //Tu ma rozwiązywać i zapisać wynik do bazy

                BruteForceTaskProvider bftp = new BruteForceTaskProvider(p.getGraph());
                BruteForceJobContext context = new BruteForceJobContext(bftp);
                BruteForceJobRunner rn = new BruteForceJobRunner(context);
                new Thread(rn).start();
                // hakierka straszna na demo
                while(context.areAnyTasksAvailable()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                p.setCost(context.getBestSolution().getCost());
                p.setTour(context.getBestSolution().getTour());
                p.setSolving(false);
                problemRepository.save(p);

                //p = problemRepository.findOne(p.getId());

                System.out.println("Końcowy wynik: " + p);

                //Tutaj zapisz jakoś wynik albo w innym odpowiednim miejscu
                System.out.println("Starting Server");
            }


        }



    }
}
