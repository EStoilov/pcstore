package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.OrderServiceModel;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {
    OrderServiceModel createOrder(HttpSession session, String userName);
    
    OrderServiceModel createOrderConfirm(HttpSession session, String username, String addressId);
    
    List<OrderServiceModel> findAllMyOrders(String username);
}
