package com.tsp.controller;

import com.tsp.bean.Order;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.service.ProblemInstanceService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Problems")
public class ProblemInstanceController {
    @Autowired
    ProblemInstanceService problemService;
    
    @RequestMapping(value="/All", method = RequestMethod.GET)
    public Collection<ProblemInstance> getAllOrder(){
        List<ProblemInstance> problems = (List<ProblemInstance>) problemService.findAll();
        return problems;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<ProblemInstance> getAllOrderForUser(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<ProblemInstance> problems = (List<ProblemInstance>) problemService.findAll(username);
        return problems;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProblemInstance getOrderById(@PathVariable("id") long id){
        return problemService.findOne(id);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOrderById(@PathVariable("id") long id){
        problemService.deleteOne(id);
    }


}
