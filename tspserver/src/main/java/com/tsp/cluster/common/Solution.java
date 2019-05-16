package com.tsp.cluster.common;

import java.util.ArrayList;

public class Solution implements Comparable<Solution> {
    public int tourCost;
    public ArrayList<Integer> tour;

    public Solution() {
        this.tourCost = Integer.MAX_VALUE;
        this.tour = new ArrayList<>();
    }
    public Solution(int cost, ArrayList<Integer> tour){
        this.tourCost = cost;
        this.tour = tour;
    }

    public Solution(int cost) {
        this.tourCost = cost;
    }

    public int getCost() {
        return tourCost;
    }

    public void setCost(int cost) {
        this.tourCost = cost;
    }

    public ArrayList<Integer> getTour() {
        return tour;
    }

    public void setTour(ArrayList<Integer> tour) {
        this.tour = tour;
    }

    @Override
    public int compareTo(Solution other) {
        if(this.tourCost == other.tourCost ) return 0;
        else if(this.tourCost < other.tourCost) return 1;
        else return - 1;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "tourCost=" + tourCost +
                ", tour=" + tour +
                '}';
    }
}
