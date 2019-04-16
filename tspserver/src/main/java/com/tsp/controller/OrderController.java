/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsp.controller;

import com.tsp.bean.Order;
import com.tsp.service.OrderService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/Orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Order> getAllOrder(){
        List<Order> orders = (List<Order>) orderService.findAll();
        return orders;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable("id") long id){
        return orderService.findOne(id);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOrderById(@PathVariable("id") long id){
        orderService.deleteOne(id);
    }
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrderById(@RequestBody Order order){
        orderService.updateOne(order);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertOrder(@RequestBody Order order){
        orderService.insertOne(order);
    }

}
