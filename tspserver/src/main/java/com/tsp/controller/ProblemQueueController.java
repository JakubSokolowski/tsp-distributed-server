package com.tsp.controller;

import com.tsp.bean.ProblemInstanceId;
import com.tsp.bean.User;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.service.ProblemInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping(value = "/Queue")
public class ProblemQueueController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProblemQueueController.class.getName());

    @Autowired
    ProblemInstanceService problemService;

    @RequestMapping(path = "/Up",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity upProblem(@RequestBody ProblemInstanceId id){
        problemService.upProblem(id.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(path = "/Down",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity downProblem(@RequestBody ProblemInstanceId id){
        problemService.downProblem(id.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/Stop",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity stopProblem(@RequestBody ProblemInstanceId id){
        LOGGER.info("Controller received STOP message for instance: {}", id);
        problemService.stopProblem(id.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(path = "/Start",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity startProblem(@RequestBody ProblemInstanceId id){
        LOGGER.info("Controller received Start message for instance: {}", id);
        problemService.startProblem(id.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(path = "/Delete",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteProblem(@RequestBody ProblemInstanceId id){
        problemService.deleteOne(id.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
    

    


}
