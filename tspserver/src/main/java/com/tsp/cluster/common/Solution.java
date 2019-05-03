package com.tsp.cluster.common;

import java.util.ArrayList;

public class Solution implements Comparable<Solution> {
    public int cost;
    public ArrayList<Integer> tour;

    public Solution() {
        this.cost = Integer.MAX_VALUE;
        this.tour = new ArrayList<>();
    }
    public Solution(int cost, ArrayList<Integer> tour){
        this.cost = cost;
        this.tour = tour;
    }

    public Solution(int cost) {
        this.cost = cost;
    }


    @Override
    public int compareTo(Solution other) {
        if(this.cost == other.cost ) return 0;
        return this.cost < other.cost ? 1: - 1;
    }
}
