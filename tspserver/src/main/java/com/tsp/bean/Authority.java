/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsp.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Adam
 */
@Entity
@Table(name = "authorities")
public class Authority implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_authority;

    private String authority;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public long getId_authority() {
        return id_authority;
    }

    public void setId_authority(long id_authority) {
        this.id_authority = id_authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority1 = (Authority) o;
        return id_authority == authority1.id_authority &&
                Objects.equals(authority, authority1.authority) &&
                Objects.equals(user, authority1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_authority, authority, user);
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id_authority=" + id_authority +
                ", authority='" + authority + '\'' +
                ", user=" + user +
                '}';
    }
}
