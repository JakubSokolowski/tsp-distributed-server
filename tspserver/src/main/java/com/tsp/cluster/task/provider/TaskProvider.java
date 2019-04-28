package com.tsp.cluster.task.provider;

import com.tsp.cluster.task.context.TaskContext;
import com.tsp.graph.GraphRepresentation;

public interface TaskProvider {
    TaskContext getNextAvailableTask();
    boolean areAnyTasksAvailable();
    int getNumOfAvailableTasks();
    void addTask(TaskContext task);
    GraphRepresentation getProblemData();
}
