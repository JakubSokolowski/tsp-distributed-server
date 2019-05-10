package com.tsp.cluster.instance;

import com.tsp.graph.GraphRepresentation;
import com.tsp.cluster.common.Algorithm;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "problems")
public class ProblemInstance {
    public ProblemInstance() {
        algorithm = Algorithm.BRUTE_FORCE;
    }

    public ProblemInstance(Algorithm alg, GraphRepresentation graph){
        algorithm = alg;
        this.graph = graph;
        this.cost = Integer.MIN_VALUE;
        isSolving = false;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setGraph(GraphRepresentation graph) {
        this.graph = graph;
    }

    public Algorithm getAlgorithm() { return algorithm; }
    public GraphRepresentation getGraph() {return graph;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isSolving() {
        return isSolving;
    }

    public void setSolving(boolean solving) {
        isSolving = solving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProblemInstance that = (ProblemInstance) o;
        return id == that.id &&
                algorithm == that.algorithm &&
                Objects.equals(graph, that.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, algorithm, graph);
    }

    @Override
    public String toString() {
        return "ProblemInstance{" +
                "id=" + id +
                ", algorithm=" + algorithm +
                ", graph=" + graph +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private long id;

    @Transient
    private Algorithm algorithm;

    @Lob
    @Column(name="graph", nullable=false, columnDefinition="mediumblob")
    private GraphRepresentation graph;

    private int cost;

    @Column(name = "is_solving")
    private boolean isSolving;
}
