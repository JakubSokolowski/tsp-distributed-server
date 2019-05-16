package com.tsp.cluster.task.provider;

import com.tsp.cluster.task.context.BruteForceTaskContext;
import com.tsp.cluster.task.context.TaskContext;
import com.tsp.graph.GraphRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BruteForceTaskProvider implements TaskProvider {
    public BruteForceTaskProvider(GraphRepresentation graph) {
        this.graph = graph;
        taskQueue = new ConcurrentLinkedQueue<>();
        initTasks();
    }
    @Override
    public synchronized TaskContext getNextAvailableTask() {
        return taskQueue.poll();
    }

    @Override
    public synchronized boolean areAnyTasksAvailable() {
        return !taskQueue.isEmpty();
    }

    @Override
    public synchronized int getNumOfAvailableTasks() {
        return taskQueue.size();
    }

    @Override
    public synchronized void addTask(TaskContext task) {
        taskQueue.add(task);
    }

    @Override
    public GraphRepresentation getProblemData() {
        return graph;
    }

    @Override
    public synchronized int getMaxNumberOfTask() {
        return maxNumberOfTask;
    }

    @Override
    public synchronized void setMaxNumberOfTask(int maxNumberOfTask) {
        this.maxNumberOfTask = maxNumberOfTask;
    }

    @Override
    public synchronized int getPercentageOfProgress() {
        return (int)( (double)(maxNumberOfTask - taskQueue.size()) / (double)(maxNumberOfTask)*100);
    }

    /**
     * Initializes task queue with all possible permutations of 2 cities
     */
    private void initTasks() {
        int numOfCities = graph.getNumOfCities();
        for(int i = 2; i <= numOfCities; i++) {
            for(int j = 2; j <= numOfCities ; j++){
                if(i == j) continue;
                ArrayList<Integer> lockedCities = new ArrayList<>(Arrays.asList(i,j));
                taskQueue.add(new BruteForceTaskContext(lockedCities));
            }
        }
        maxNumberOfTask = taskQueue.size();
    }

    private GraphRepresentation graph;
    private ConcurrentLinkedQueue<TaskContext> taskQueue;
    private int maxNumberOfTask;
    private int actualNumberOfTask;
}
