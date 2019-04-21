package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.OrderServiceModel;

import javax.servlet.http.HttpSession;

public interface OrderService {
    OrderServiceModel createOrder(HttpSession session, String userName);
}
