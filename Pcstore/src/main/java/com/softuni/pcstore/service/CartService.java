package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.views.ProductCartViewModel;

import javax.servlet.http.HttpSession;
import java.util.List;
public interface CartService {
    void addProduct(String productId, HttpSession httpSession);
    
    void removeProduct(String id, List<ProductCartViewModel> products);
}
