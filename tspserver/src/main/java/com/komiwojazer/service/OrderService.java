/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.komiwojazer.service;

import com.komiwojazer.bean.Order;
import com.komiwojazer.repository.OrderRepository;
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
        Order updatingOrder = repository.findOne(order.getId_order());
        updatingOrder.setCost(order.getCost());
        updatingOrder.setDate_of_order(order.getDate_of_order());
        updatingOrder.setId_order(order.getId_order());
        updatingOrder.setMatrix(order.getMatrix());
        updatingOrder.setPath(order.getPath());
        repository.save(updatingOrder);
    }
    public void insertOne(Order order) {
        repository.save(order);
    }
}
