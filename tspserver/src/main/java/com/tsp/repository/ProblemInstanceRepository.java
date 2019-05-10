/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsp.repository;

import com.tsp.bean.User;
import com.tsp.cluster.instance.ProblemInstance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProblemInstanceRepository extends CrudRepository<ProblemInstance, Long> {

    @Transactional
    @Query("SELECT p FROM ProblemInstance p WHERE p.cost < 0")
    ProblemInstance findUsolvedInstanceProblem();

    @Query("SELECT p FROM ProblemInstance p WHERE p.isSolving = true")
    ProblemInstance findProblemWhichIsSolvingAtTheMoment();
    
}
