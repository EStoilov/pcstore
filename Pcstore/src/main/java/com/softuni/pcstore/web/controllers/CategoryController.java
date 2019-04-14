package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.binding.AddCategoryBindingModel;
import com.softuni.pcstore.domain.models.service.CategoryServiceModel;
import com.softuni.pcstore.domain.models.views.*;
import com.softuni.pcstore.service.CategoryService;
import com.softuni.pcstore.service.CloudinaryService;
import com.softuni.pcstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    public ModelAndView addCategory(@ModelAttribute AddCategoryBindingModel addCategoryBindingModel) {
        return view("category/add-category");
    }
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirm(ModelAndView modelAndView,
                                           @Valid @ModelAttribute AddCategoryBindingModel addCategoryBindingModel,
                                           BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
           modelAndView.addObject("addCategoryBindingModel", addCategoryBindingModel);
           
           return view("category/add-category", modelAndView);
        }
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
    public ModelAndView findProductsCategory(@PathVariable String name, ModelAndView modelAndView){
        
        List<ProductInCategoryViewModel> products = this.categoryService.findProductByCategoryName(name)
                .stream()
                .map(p -> this.modelMapper.map(p, ProductInCategoryViewModel.class))
                .collect(Collectors.toList());
        
        modelAndView.addObject("products", products);
        modelAndView.addObject("category", name);
        
        return view("/category/category-all-products", modelAndView);
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories() {
        return this.categoryService.findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView){
        CategoryHomeDetailsViewModel viewModel = 
                this.modelMapper.map(this.categoryService.findCategoryById(id),CategoryHomeDetailsViewModel.class);
        modelAndView.addObject("model", viewModel);
        
        return view("/category/edit-category", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCategoryConfirm(@PathVariable String id, @ModelAttribute CategoryHomeDetailsViewModel categoryHomeDetailsViewModel){
        this.categoryService.editCategory(id, this.modelMapper.map(categoryHomeDetailsViewModel, CategoryServiceModel.class));
        return redirect("/categories/all");
    }
    
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategory(@PathVariable String id, ModelAndView modelAndView){
        CategoryHomeDetailsViewModel viewModel =
                this.modelMapper.map(this.categoryService.findCategoryById(id),CategoryHomeDetailsViewModel.class);
        modelAndView.addObject("model", viewModel);

        return view("/category/delete-category", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategoryConfirm(@PathVariable String id, @ModelAttribute CategoryHomeDetailsViewModel categoryHomeDetailsViewModel){
        this.categoryService.deleteCategory(id, this.modelMapper.map(categoryHomeDetailsViewModel, CategoryServiceModel.class));
        return redirect("/categories/all");
    }
    
}
