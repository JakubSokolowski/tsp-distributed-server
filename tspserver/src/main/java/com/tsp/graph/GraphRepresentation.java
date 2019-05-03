package com.tsp.graph;

import java.util.ArrayList;
import java.util.Vector;

public abstract class GraphRepresentation {
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

    protected void initMatrix(int matrixSize) {
        costMatrix = new Vector<>(matrixSize);

    }
    protected Vector<Vector<Integer>> costMatrix;
    protected int numOfEdges = 0;
}
