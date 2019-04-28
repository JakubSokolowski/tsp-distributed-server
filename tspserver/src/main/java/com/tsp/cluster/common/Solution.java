package com.tsp.cluster.common;

import java.util.ArrayList;

public class Solution implements Comparable<Solution> {
    private int cost;
    private ArrayList<Integer> path;

    public Solution() {
        this.cost = Integer.MAX_VALUE;
        this.path = new ArrayList<>();
    }
    public Solution(int cost, ArrayList<Integer> path){
        this.cost = cost;
        this.path = path;
    }

    public Solution(int cost) {
        this.cost = cost;
    }

    public Solution(String str) {

    }

    @Override
    public int compareTo(Solution other) {
        if(this.cost == other.cost ) return 0;
        return this.cost < other.cost ? 1: - 1;
    }
}
