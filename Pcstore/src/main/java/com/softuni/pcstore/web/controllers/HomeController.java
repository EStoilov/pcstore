package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.views.CategoryHomeViewModel;
import com.softuni.pcstore.domain.models.views.ProductAllViewModel;
import com.softuni.pcstore.domain.models.views.ProductCartViewModel;
import com.softuni.pcstore.service.CategoryService;
import com.softuni.pcstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
    
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(CategoryService categoryService, ProductService productService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView){
        List<CategoryHomeViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryHomeViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        
        return view("index", modelAndView);
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @Scheduled(fixedRate = 3000)
    public ModelAndView home(ModelAndView modelAndView, Principal principal){
        String username = principal.getName();
        List<ProductAllViewModel> products = this.productService.getRandomProducts()
                .stream().map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList());
        
        modelAndView.addObject("products", products);
        modelAndView.addObject("username", username);
        
        return view("home", modelAndView);
    }
    
}
