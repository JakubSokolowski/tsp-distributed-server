package com.tsp.cluster.job;

import com.tsp.cluster.common.Solution;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.task.context.TaskContext;
import com.tsp.cluster.task.provider.TaskProvider;
import com.tsp.graph.GraphRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobContext.class.getName());

    protected TaskProvider taskProvider;
    protected Solution bestSolution = new Solution();
    protected JobState jobState = JobState.ENQUEUED;
    protected ProblemInstance problemInstance;

    public JobContext(TaskProvider taskProvider) {
        this.taskProvider = taskProvider;
    }

    public JobContext(TaskProvider taskProvider, JobState jobState) {
        this.taskProvider = taskProvider;
        this.jobState = jobState;
    }

    public JobState getJobState() {
        return this.jobState;
    }

    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }

    public synchronized Solution getBestSolution() {
        return bestSolution;
    }

    public synchronized TaskContext getNextAvailableTask() {
        LOGGER.info("Current number of available tasks: {}", taskProvider.getNumOfAvailableTasks());
        return taskProvider.getNextAvailableTask();
    }

    public synchronized boolean areAnyTasksAvailable() {
        return taskProvider.areAnyTasksAvailable();
    }

    public synchronized int getNumOfAvailableTasks() {
        return taskProvider.getNumOfAvailableTasks();
    }

    public GraphRepresentation getProblemData() {
        return taskProvider.getProblemData();
    }

    public ProblemInstance getProblemInstance() {
        return problemInstance;
    }

    public void setProblemInstance(ProblemInstance instance) {
        this.problemInstance = instance;
    }

    public int getPercentageOfProgress() {
        return taskProvider.getPercentageOfProgress();
    }

    public synchronized void updateSolution(Solution solution) {
        if (bestSolution.compareTo(solution) < 0) {
            LOGGER.info(
                    "Found new best solution for problem {}. Old cost: {} New cost : {}",
                    problemInstance.getId(),
                    bestSolution.getCost(),
                    solution.getCost()
            );
            bestSolution = solution;
        }

    }
}
