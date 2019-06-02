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
        return (List<ProblemInstance>) repository.findAll();
    }

    public List<ProblemInstance> findAll(String username) {
        return repository.findAllForUser(username);
    }

    public ProblemInstance findOne(long id) {

        return repository.findOne(id);
    }

    public void deleteOne(long id) {
        repository.delete(id);
    }
}
