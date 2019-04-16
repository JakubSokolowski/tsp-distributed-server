/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsp.service;

import com.tsp.bean.Order;
import com.tsp.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {

        List<Order> order = (List<Order>) repository.findAll();
        return order;
    }
    public Order findOne(long id) {

        return repository.findOne(id);
    }
    public void deleteOne(long id) {

        repository.delete(id);
    }
    public void updateOne(Order order) {
        Order updatingOrder = repository.findOne(order.getOrderId());
        updatingOrder.setCost(order.getCost());
        updatingOrder.setDateOfOrder(order.getDateOfOrder());
        updatingOrder.setOrderId(order.getOrderId());
        updatingOrder.setMatrix(order.getMatrix());
        updatingOrder.setPath(order.getPath());
        repository.save(updatingOrder);
    }
    public void insertOne(Order order) {
        repository.save(order);
    }
}
