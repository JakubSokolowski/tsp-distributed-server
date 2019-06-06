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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void deleteOne(long id) {
        ProblemInstance deletingProblem = repository.findOne(id);
        List<ProblemInstance> list = repository.findWithGraterIndexInQueue(deletingProblem.getIndexInQueue());
        for(ProblemInstance p : list)
        {
            p.setIndexInQueue(p.getIndexInQueue()-1);
            repository.save(p);
        }
        repository.delete(id);
    }

    @Transactional
    public void upProblem(long id) {
        ProblemInstance p = repository.findOne(id);
        int index = p.getIndexInQueue();
        if(index > 1) {
            ProblemInstance other = repository.findByIndexInQueue(index - 1);
            if(other == null)return;
            if(other.isSolving())return;
            other.setIndexInQueue(other.getIndexInQueue() + 1);
            p.setIndexInQueue(p.getIndexInQueue() - 1);
            repository.save(p);
            repository.save(other);


        }
    }

    @Transactional
    public void downProblem(long id) {

        ProblemInstance p = repository.findOne(id);
        if(p.isSolving())return;
        int index = p.getIndexInQueue();
        ProblemInstance other = repository.findByIndexInQueue(index + 1);
            if(other != null) {
                other.setIndexInQueue(other.getIndexInQueue() - 1);
                p.setIndexInQueue(p.getIndexInQueue() + 1);
                repository.save(p);
                repository.save(other);
            }


    }

    @Transactional
    public void startProblem(long id) {
        ProblemInstance p = repository.findOne(id);
        if(p!=null)
        {
            ProblemInstance other = repository.findByIndexInQueue(1);
            if(p.getId() != other.getId())
            {
                other.setIndexInQueue(p.getIndexInQueue());
                other.setSolving(false);
                p.setIndexInQueue(1);
                repository.save(other);
            }
            p.setSolving(true);
            repository.save(p);
        }


    }

    @Transactional
    public void stopProblem(long id) {
        ProblemInstance p = repository.findOne(id);
        if(p!=null)
        {
            p.setSolving(false);
            repository.save(p);
        }


    }


}
