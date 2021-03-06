package com.tsp.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class SymmetricMatrix extends GraphRepresentation implements Serializable {
    public SymmetricMatrix(Vector<Vector<Integer>> matrix) {
        super(matrix);
    }

    @Override
    public void addEdge(int source, int destination, int cost) {
        if(!isConnected(source, destination)) {
            costMatrix.get(source).set(destination, cost);
            costMatrix.get(destination).set(source, cost);
            numOfEdges++;
        }
    }

    @Override
    public boolean isConnected(int source, int destination) {
        return costMatrix.get(source).get(destination) != Integer.MAX_VALUE;
    }

    @Override
    public int getCost(int source, int destination) {
        return costMatrix.get(source).get(destination);
    }

    @Override
    public int getNumOfEdges() {
        return numOfEdges;
    }

    @Override
    public int getNumOfCities() {
        return costMatrix.size();
    }

    @Override
    public int getTourCost(ArrayList<Integer> tour) {
        int result = 0;
        for(int i = 0; i < getNumOfCities(); i++) {
            result += getCost(tour.get(i) - 1, tour.get(i+1) - 1);
        }
        return result;
    }

    @Override
    public String toString() {
        return "SymmetricMatrix{" +
                "costMatrix=" + costMatrix +
                ", numOfEdges=" + numOfEdges +
                '}';
    }
}
