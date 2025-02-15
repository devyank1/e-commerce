package com.dev.yank.ecommerce.model;

import com.dev.yank.ecommerce.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tb_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date orderDate;
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // CANCELLED, COMPLETED OR PENDING

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItem;

    // CONSTRUCTORS

    public Order() {}

    public Order(Long id, Date orderDate, Double totalAmount, OrderStatus orderStatus, User user, Set<OrderItem> orderItem) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.user = user;
        this.orderItem = orderItem;
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Set<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }
}
