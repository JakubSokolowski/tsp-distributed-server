package com.tsp.cluster.task.provider;

import com.tsp.cluster.instance.HardcodedInstanceProvider;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.task.context.BruteForceTaskContext;
import com.tsp.cluster.task.context.TaskContext;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;


public class BruteForceTaskProviderTest {

    private BruteForceTaskProvider provider;

    @org.junit.Before
    public void setUp() throws Exception {
        ProblemInstance problemInstance = new HardcodedInstanceProvider().getProblemInstance();
        provider = new BruteForceTaskProvider(problemInstance.getGraph());
    }

    @org.junit.After
    public void tearDown() throws Exception {
        provider = null;
    }

    @org.junit.Test
    public void getNextAvailableTask() {
        // given
        BruteForceTaskContext expectedTask = new BruteForceTaskContext(new ArrayList<>(Arrays.asList(1, 2)));

        // when
        TaskContext actualTask = provider.getNextAvailableTask();

        // then
        Assert.assertEquals(expectedTask, actualTask);
    }

    @org.junit.Test
    public void areAnyTasksAvailable() {
        // when
        boolean available = provider.areAnyTasksAvailable();

        // then
        Assert.assertTrue(available);
    }

    @org.junit.Test
    public void getNumOfAvailableTasks() {
        // given
        int expected = 20;

        // when
        int actual = provider.getNumOfAvailableTasks();

        // then
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void addTask() {
        // given
        BruteForceTaskContext task = new BruteForceTaskContext(new ArrayList<>());
        int expected = 21;

        // when
        provider.addTask(task);
        int actual = provider.getNumOfAvailableTasks();

        // then
        Assert.assertEquals(expected, actual);
    }
}
