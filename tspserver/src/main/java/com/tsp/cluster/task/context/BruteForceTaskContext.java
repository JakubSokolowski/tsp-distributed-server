package com.tsp.cluster.task.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BruteForceTaskContext implements TaskContext {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BruteForceTaskContext that = (BruteForceTaskContext) o;
        return Objects.equals(lockedCities, that.lockedCities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lockedCities);
    }

    public BruteForceTaskContext(ArrayList<Integer> locked) {
        lockedCities = locked;
    }

    public BruteForceTaskContext(Integer[] locked) {
        lockedCities = new ArrayList<>(Arrays.asList(locked));
    }

    @Override
    public String serialize() {
        return lockedCities.toString();
    }
    private ArrayList<Integer> lockedCities;
}
