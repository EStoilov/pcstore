package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.service.OrderServiceModel;
import com.softuni.pcstore.domain.models.service.ProductServiceModel;
import com.softuni.pcstore.domain.models.views.OrderViewModel;
import com.softuni.pcstore.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{
    
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myOrder(ModelAndView modelAndView,
                                HttpSession httpSession,
                                Principal principal, 
                                @ModelAttribute OrderViewModel orderViewModel ){
        OrderViewModel order =  this.modelMapper.map
                (this.orderService.createOrder(httpSession,principal.getName()), OrderViewModel.class);
        modelAndView.addObject("order", order);
        
        return view("order/my-order",modelAndView);
    }
    
    @PostMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myOrderConfirm(@ModelAttribute OrderViewModel orderViewModel 
                                        , HttpSession session
                                        , Principal principal){
        String addressId = orderViewModel.getAddress();
        String username = principal.getName();
        this.orderService.createOrderConfirm(session,username, addressId );
        
        return redirect("/home");
    }
                                
}
