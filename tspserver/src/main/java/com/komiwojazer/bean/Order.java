package com.komiwojazer.bean;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_order;

    private int cost;

    private String path;

    private String matrix;

    private Date date_of_order;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public long getId_order() {
        return id_order;
    }

    public void setId_order(long id_order) {
        this.id_order = id_order;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMatrix() {
        return matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public Date getDate_of_order() {
        return date_of_order;
    }

    public void setDate_of_order(Date date_of_order) {
        this.date_of_order = date_of_order;
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
        Order order = (Order) o;
        return id_order == order.id_order &&
                cost == order.cost &&
                Objects.equals(path, order.path) &&
                Objects.equals(matrix, order.matrix) &&
                Objects.equals(date_of_order, order.date_of_order) &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_order, cost, path, matrix, date_of_order, user);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id_order=" + id_order +
                ", cost=" + cost +
                ", path='" + path + '\'' +
                ", matrix='" + matrix + '\'' +
                ", date_of_order=" + date_of_order +
                ", user=" + user +
                '}';
    }
}
