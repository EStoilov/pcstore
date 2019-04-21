package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.domain.models.views.ProductCartViewModel;
import com.softuni.pcstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends  BaseController{
    
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/my/{id}")
    public ModelAndView myCart(@PathVariable String id, HttpSession httpSession){
        this.cartService.addProduct(id, httpSession);
        
        return redirect("/home");
    }
    
    @GetMapping("/details")
    public ModelAndView cartDetails(HttpSession httpSession){
        if(httpSession.getAttribute(Constants.SET_ATTRIBUTE_SESSION_TOTAL_SUM) == null){
            httpSession.setAttribute(Constants.SET_ATTRIBUTE_SESSION_TOTAL_SUM, "0");
        }
        return view("cart/cart-details");
    }

    @DeleteMapping("/remove-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeFromCartConfirm(String id, HttpSession session) {
        List<ProductCartViewModel> products =(List<ProductCartViewModel>) session.getAttribute(Constants.SET_ATTRIBUTE_SESSION_SHOPPING_CART);
        this.cartService.removeProduct(id,products );

        return super.redirect("/cart/details");
    }
}
