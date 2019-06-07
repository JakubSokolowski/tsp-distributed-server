package com.tsp.service;

import com.tsp.bean.User;
import com.tsp.cluster.common.Algorithm;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.cluster.job.BruteForceJobContext;
import com.tsp.cluster.job.JobQueue;
import com.tsp.graph.GraphParser;
import com.tsp.graph.GraphRepresentation;
import com.tsp.repository.ProblemInstanceRepository;
import com.tsp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Component
public class FileThread {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileThread.class.getName());
    @Autowired
    ProblemInstanceRepository problemRepository;

    @Autowired
    UserRepository userRepository;

    private String path;
    private String username;

    public FileThread() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void run() {
        try {
            String data = new String(Files.readAllBytes(Paths.get(path)));
            GraphRepresentation sm = GraphParser.parse(data);
            Files.deleteIfExists(Paths.get(this.path));
            ProblemInstance problem = new ProblemInstance(Algorithm.BRUTE_FORCE, sm);
            User user = userRepository.findOne(username);
            problem.setUser(user);
            problem.setDateOfOrdering(new Date());
            LOGGER.info(
                    "Received new job request from user: {}. ID: {} SIZE: {}",
                    username,
                    problem.getId(),
                    sm.getNumOfCities()
            );
            JobQueue.enqueue(new BruteForceJobContext(problem));

        } catch (IOException e) {
            LOGGER.error(
                    "Error receiving new job context from user: {} due to : {}",
                    username,
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
