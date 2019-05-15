/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsp.service;

import com.tsp.cluster.instance.ProblemInstance;

import java.util.List;

import com.tsp.repository.ProblemInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemInstanceService {

    @Autowired
    private ProblemInstanceRepository repository;

    public List<ProblemInstance> findAll() {

        List<ProblemInstance> problems = (List<ProblemInstance>) repository.findAll();
        return problems;
    }

    public List<ProblemInstance> findAll(String username) {

        List<ProblemInstance> problems = (List<ProblemInstance>) repository.findAllForUser(username);
        return problems;
    }

    public ProblemInstance findOne(long id) {

        return repository.findOne(id);
    }
    public void deleteOne(long id) {

        repository.delete(id);
    }
}
