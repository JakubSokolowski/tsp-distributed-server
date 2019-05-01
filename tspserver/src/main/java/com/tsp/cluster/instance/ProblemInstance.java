package com.tsp.cluster.instance;

import com.tsp.graph.GraphRepresentation;
import com.tsp.cluster.common.Algorithm;

public class ProblemInstance {
    public ProblemInstance(Algorithm alg, GraphRepresentation graph){
        algorithm = alg;
        graphRepresentation = graph;
    }
    public Algorithm getAlgorithm() { return algorithm; }
    public GraphRepresentation getGraph() {return graphRepresentation;}

    private Algorithm algorithm;
    private GraphRepresentation graphRepresentation;
}
