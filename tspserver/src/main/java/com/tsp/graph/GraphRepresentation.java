package com.tsp.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public abstract class GraphRepresentation implements Serializable {
    public GraphRepresentation(){}
    public GraphRepresentation(Vector<Vector<Integer>> matrix) {
        costMatrix = matrix;
    }
    public abstract void addEdge(int source, int destination, int cost);
    public abstract boolean isConnected(int source, int destination);
    public abstract int getCost(int source, int destination);
    public abstract int getNumOfEdges();
    public abstract int getNumOfCities();
    public abstract int getTourCost(ArrayList<Integer> tour);

    public Vector<Vector<Integer>> getCostMatrix() {
        return costMatrix;
    }

    public void setCostMatrix(Vector<Vector<Integer>> costMatrix) {
        this.costMatrix = costMatrix;
    }

    public void setNumOfEdges(int numOfEdges) {
        this.numOfEdges = numOfEdges;
    }

    protected void initMatrix(int matrixSize) {
        costMatrix = new Vector<>(matrixSize);

    }
    protected Vector<Vector<Integer>> costMatrix;
    protected int numOfEdges = 0;
}
