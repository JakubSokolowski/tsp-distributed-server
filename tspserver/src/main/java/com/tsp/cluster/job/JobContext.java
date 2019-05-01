package com.tsp.cluster.job;

import com.tsp.graph.GraphRepresentation;
import com.tsp.cluster.common.Solution;
import com.tsp.cluster.task.context.TaskContext;
import com.tsp.cluster.task.provider.TaskProvider;

public class JobContext {
    protected TaskProvider taskProvider;
    protected Solution bestSolution = new Solution();

    public JobContext(TaskProvider taskProvider) {
        this.taskProvider = taskProvider;
    }

    public synchronized Solution getBestSolution() {
        return bestSolution;
    }

    public synchronized TaskContext getNextAvailableTask() {
        return taskProvider.getNextAvailableTask();
    }

    public synchronized boolean areAnyTasksAvailable() {
        return taskProvider.areAnyTasksAvailable();
    }

    public synchronized int getNumOfAvailableTasks() {
        return taskProvider.getNumOfAvailableTasks();
    }

    public GraphRepresentation getProblemData() {return taskProvider.getProblemData();}

    public synchronized void updateSolution(Solution solution) {
        if(bestSolution.compareTo(solution) < 0) {
            bestSolution = solution;
            System.out.println("Found new best solution!");
        }
    }
}
