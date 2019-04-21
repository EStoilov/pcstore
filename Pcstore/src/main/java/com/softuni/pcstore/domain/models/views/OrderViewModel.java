package com.softuni.pcstore.domain.models.views;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public class OrderViewModel {
    
    private String address;
    private BigDecimal totalPrice;
    private LocalDateTime sendDate;
    private boolean isDelivered;
    private List<ProductOrderViewModel> products;

    public OrderViewModel() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public List<ProductOrderViewModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderViewModel> products) {
        this.products = products;
    }
}
