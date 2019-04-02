package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.binding.AddCategoryBindingModel;
import com.softuni.pcstore.domain.models.service.CategoryServiceModel;
import com.softuni.pcstore.domain.models.views.CategoryHomeViewModel;
import com.softuni.pcstore.service.CategoryService;
import com.softuni.pcstore.service.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {
    
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, 
                              CloudinaryService cloudinaryService,
                              ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategory() {
        return view("category/add-category");
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirm(@ModelAttribute AddCategoryBindingModel addCategoryBindingModel) throws IOException {
        
        CategoryServiceModel categoryServiceModel  = 
                this.modelMapper.map(addCategoryBindingModel, CategoryServiceModel.class);
        categoryServiceModel.setImage(
                this.cloudinaryService.uploadImage(addCategoryBindingModel.getImage())
        );
        
        this.categoryService.addCategory(categoryServiceModel);
        
        return redirect("/home"); 
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allCategories(ModelAndView modelAndView){
        List<CategoryHomeViewModel> categories = 
                this.categoryService.findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryHomeViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        
        return view("/category/all-categories", modelAndView);
    }
    
    @GetMapping("/{name}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView findCurrentCategory(@PathVariable String name){
        
    }
        
}
