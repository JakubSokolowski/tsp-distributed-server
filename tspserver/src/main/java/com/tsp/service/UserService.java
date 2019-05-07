/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsp.service;

import com.tsp.bean.Authority;
import com.tsp.bean.User;
import com.tsp.repository.AuthorityRepository;
import com.tsp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthorityRepository authorityRepository;


    public List<User> findAll() {

        List<User> users = (List<User>) repository.findAll();
        
        return users;
    }
    public User findOne(String username) {

        return repository.findOne(username);
    }
    public void deleteOne(String username) {

        repository.delete(username);
    }

    @Transactional
    public void updateOne(String username,User user) {
        User updatingUser = repository.findOne(username);
        updatingUser.setPassword(user.getPassword());
        repository.save(updatingUser);
    }
    @Transactional
    public boolean insertOne(User user) {
        User lc = repository.findOne(user.getUsername());
        if(lc == null) {
            repository.save(user);
            Authority authority = new Authority();
            authority.setAuthority("USER");
            authority.setUser(user);
            authorityRepository.save(authority);
            return true;
        }
        return false;
    }

    public String findAutorityByUsername(String username)
    {
        return authorityRepository.findAuthorityByUsername(username);
    }
}
