package com.tsp.cluster.instance;

import com.tsp.bean.User;
import com.tsp.graph.GraphRepresentation;
import com.tsp.cluster.common.Algorithm;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateOfOrdering() {
        return dateOfOrdering;
    }

    public void setDateOfOrdering(Date dateOfOrdering) {
        this.dateOfOrdering = dateOfOrdering;
    }

    public ArrayList<Integer> getTour() {
        return tour;
    }

    public void setTour(ArrayList<Integer> tour) {
        this.tour = tour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProblemInstance that = (ProblemInstance) o;
        return id == that.id &&
                cost == that.cost &&
                isSolving == that.isSolving &&
                algorithm == that.algorithm &&
                Objects.equals(graph, that.graph) &&
                Objects.equals(dateOfOrdering, that.dateOfOrdering) &&
                Objects.equals(tour, that.tour) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, algorithm, graph, dateOfOrdering, cost, tour, isSolving, user);
    }

    @Override
    public String toString() {
        return "ProblemInstance{" +
                "id=" + id +
                ", algorithm=" + algorithm +
                ", graph=" + graph +
                ", dateOfOrdering=" + dateOfOrdering +
                ", cost=" + cost +
                ", tour=" + tour +
                ", isSolving=" + isSolving +
                ", user=" + user +
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

    @Column(name = "date_of_ordering")
    private Date dateOfOrdering;

    private int cost;

    @Lob
    @Column(name="tour", columnDefinition="mediumblob")
    private ArrayList<Integer> tour;

    @Column(name = "is_solving")
    private boolean isSolving;

    @ManyToOne
    @JoinColumn(name = "username_of_user")
    private User user;
}
