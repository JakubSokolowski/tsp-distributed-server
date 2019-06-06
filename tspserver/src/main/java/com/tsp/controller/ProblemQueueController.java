package com.tsp.controller;

import com.tsp.bean.ProblemInstanceId;
import com.tsp.bean.User;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.service.ProblemInstanceService;
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
        //tutaj start rozwiązywania problemu
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(path = "/Start",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity startProblem(@RequestBody ProblemInstanceId id){
        //tutaj stop rozwiązywania problemu
        return new ResponseEntity(HttpStatus.OK);
    }
    

    


}
