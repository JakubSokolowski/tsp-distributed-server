package com.tsp.cluster.job;

import com.tsp.repository.ProblemInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JobQueueInitializer {
    @Autowired
    private ProblemInstanceRepository repository;
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        JobQueue.setProblemRepository(repository);
    }
}
