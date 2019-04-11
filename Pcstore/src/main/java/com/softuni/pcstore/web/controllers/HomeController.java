package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.views.CategoryHomeDetailsViewModel;
import com.softuni.pcstore.domain.models.views.CategoryHomeViewModel;
import com.softuni.pcstore.domain.models.views.CategoryNavbarViewModel;
import com.softuni.pcstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
    
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
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
    public ModelAndView home(ModelAndView modelAndView, Principal principal){
        String username = principal.getName();
        List<CategoryHomeViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryHomeViewModel.class))
                .collect(Collectors.toList());
        
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("username", username);
        
        return view("home", modelAndView);
    }
    
    @GetMapping("/info")
    public String info(HttpRequest request){

        int b = 5;
        return null;
    }
    
    
}
