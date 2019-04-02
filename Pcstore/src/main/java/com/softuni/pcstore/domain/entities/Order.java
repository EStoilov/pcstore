package com.softuni.pcstore.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private User user;
    private Address address;
    private BigDecimal totalPrice;
    private LocalDateTime sendDate;
    private boolean isDelivered;
    private List<Product> products;

    public Order() {
    }

    @ManyToOne(targetEntity = User.class)
    @JoinTable(name = "orders_users"
            , joinColumns = @JoinColumn(name = "order_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "total_price", nullable = false)
    @DecimalMin("0.01")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "send_date")
    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    @ManyToMany(targetEntity = Product.class)
    @JoinTable(name = "orders_products"
            , joinColumns = @JoinColumn(name = "order_id")
            , inverseJoinColumns = @JoinColumn(name = "product_id"))
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Column(name = "is_delivered", nullable = false)
    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        this.isDelivered = delivered;
    }
}
