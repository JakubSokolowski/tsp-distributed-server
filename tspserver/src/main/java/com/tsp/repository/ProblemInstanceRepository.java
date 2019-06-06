/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsp.repository;

import com.tsp.cluster.instance.ProblemInstance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Repository
public interface ProblemInstanceRepository extends CrudRepository<ProblemInstance, Long> {

    @Transactional
    @Query("SELECT p FROM ProblemInstance p WHERE p.cost < 0")
    ProblemInstance findUsolvedInstanceProblem();

    @Transactional
    @Query("SELECT p FROM ProblemInstance p WHERE p.isSolving = true")
    ProblemInstance findProblemWhichIsSolvingAtTheMoment();

    @Transactional
    @Query("SELECT p FROM ProblemInstance p WHERE p.user.username = :#{#username }")
    ArrayList<ProblemInstance> findAllForUser(@Param("username")String username);

    @Transactional
    @Query("SELECT max(p.indexInQueue) FROM ProblemInstance p")
    Integer findMaxIndex();
    
}
