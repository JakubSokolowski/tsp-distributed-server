package com.tsp.bean;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    private int cost;

    private String path;

    private String matrix;

    @Column(name = "date_of_order")
    private Date dateOfOrder;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
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
        return orderId == order.orderId &&
                cost == order.cost &&
                Objects.equals(path, order.path) &&
                Objects.equals(matrix, order.matrix) &&
                Objects.equals(dateOfOrder, order.dateOfOrder) &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, cost, path, matrix, dateOfOrder, user);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", cost=" + cost +
                ", path='" + path + '\'' +
                ", matrix='" + matrix + '\'' +
                ", dateOfOrder=" + dateOfOrder +
                ", user=" + user +
                '}';
    }
}
