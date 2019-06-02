package com.tsp.cluster.job;

import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.task.provider.BruteForceTaskProvider;
import com.tsp.cluster.task.provider.TaskProvider;

public class BruteForceJobContext extends JobContext {
    public BruteForceJobContext(TaskProvider taskProvider) {
        super(taskProvider);
    }

    public BruteForceJobContext(ProblemInstance instance) {
        super(new BruteForceTaskProvider(instance));
        this.problemInstance = instance;
    }

}
