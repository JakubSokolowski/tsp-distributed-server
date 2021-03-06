package com.tsp.cluster.instance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tsp.bean.User;
import com.tsp.cluster.common.Algorithm;
import com.tsp.cluster.job.JobQueue;
import com.tsp.cluster.job.JobState;
import com.tsp.graph.GraphRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "problems")
public class ProblemInstance {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProblemInstance.class.getName());
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private long id;
    @Transient
    private Algorithm algorithm;
    @Lob
    @Column(name = "graph", nullable = false, columnDefinition = "mediumblob")
    private GraphRepresentation graph;
    @Column(name = "date_of_ordering")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateOfOrdering;
    private int cost;
    @Column(name = "percentage_of_progress")
    private int percentageOfProgress;
    @Lob
    @Column(name = "tour", columnDefinition = "mediumblob")
    private ArrayList<Integer> tour;
    @Column(name = "is_solving")
    private boolean isSolving;
    @ManyToOne
    @JoinColumn(name = "username_of_user")
    private User user;

    @Column(name = "time_of_running_in_seconds")
    private long timeOfRunningInSeconds;

    @Column(name = "index_in_queue")
    private int indexInQueue;

    @Column(name = "status")
    private JobState state;

    public ProblemInstance() {
        algorithm = Algorithm.BRUTE_FORCE;
    }

    public ProblemInstance(Algorithm alg, GraphRepresentation graph) {
        algorithm = alg;
        this.graph = graph;
        this.cost = Integer.MIN_VALUE;
        isSolving = false;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public GraphRepresentation getGraph() {
        return graph;
    }

    public void setGraph(GraphRepresentation graph) {
        this.graph = graph;
    }

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

    public int getPercentageOfProgress() {
        return percentageOfProgress;
    }

    public void setPercentageOfProgress(int percentageOfProgress) {
        this.percentageOfProgress = percentageOfProgress;
    }

    public long getTimeOfRunningInSecconds() {
        return timeOfRunningInSeconds;
    }

    public void setTimeOfRunningInSecconds(long timeOfRunningInSeconds) {
        this.timeOfRunningInSeconds = timeOfRunningInSeconds;
    }

    public long getTimeOfRunningInSeconds() {
        return timeOfRunningInSeconds;
    }

    public void setTimeOfRunningInSeconds(int timeOfRunningInSeconds) {
        this.timeOfRunningInSeconds = timeOfRunningInSeconds;
    }

    public int getIndexInQueue() {
        return indexInQueue;
    }

    public void setIndexInQueue(int index) {
        LOGGER.info("Setting new index for job: {}. prev: {} curr: {}", id, indexInQueue, index);
        this.indexInQueue = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProblemInstance that = (ProblemInstance) o;
        return id == that.id &&
                cost == that.cost &&
                percentageOfProgress == that.percentageOfProgress &&
                isSolving == that.isSolving &&
                timeOfRunningInSeconds == that.timeOfRunningInSeconds &&
                indexInQueue == that.indexInQueue &&
                algorithm == that.algorithm &&
                Objects.equals(graph, that.graph) &&
                Objects.equals(dateOfOrdering, that.dateOfOrdering) &&
                Objects.equals(tour, that.tour) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, algorithm, graph, dateOfOrdering, cost, percentageOfProgress, tour, isSolving, user, timeOfRunningInSeconds, indexInQueue);
    }

    @Override
    public String toString() {
        return "ProblemInstance{" +
                "id=" + id +
                ", algorithm=" + algorithm +
                ", graph=" + graph +
                ", dateOfOrdering=" + dateOfOrdering +
                ", cost=" + cost +
                ", percentageOfProgress=" + percentageOfProgress +
                ", tour=" + tour +
                ", isSolving=" + isSolving +
                ", user=" + user +
                ", timeOfRunningInSeconds=" + timeOfRunningInSeconds +
                ", indexInQueue=" + indexInQueue +
                '}';
    }

    public JobState getState() {
        return state;
    }

    public void setState(JobState state) {
        this.state = state;
    }
}
