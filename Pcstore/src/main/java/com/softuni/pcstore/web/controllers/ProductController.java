package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.binding.AddProductBindingModel;
import com.softuni.pcstore.domain.models.service.CategoryServiceModel;
import com.softuni.pcstore.domain.models.service.ProductServiceModel;
import com.softuni.pcstore.domain.models.views.ProductAllViewModel;
import com.softuni.pcstore.service.CategoryService;
import com.softuni.pcstore.service.CloudinaryService;
import com.softuni.pcstore.service.ProductService;
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
@RequestMapping("/products")
public class ProductController extends BaseController {
    
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CloudinaryService cloudinaryService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct() {
        return super.view("product/add-product");
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(@ModelAttribute AddProductBindingModel addProductBindingModel) throws IOException {
        ProductServiceModel productServiceModel = 
                this.modelMapper.map(addProductBindingModel, ProductServiceModel.class);
        
        CategoryServiceModel categoryServiceModel =
                this.categoryService.findCategoryById(addProductBindingModel.getCategory());
        productServiceModel.setCategory(categoryServiceModel);
        productServiceModel.setImage(this.cloudinaryService.uploadImage(addProductBindingModel.getImage()));
        this.productService.createProduct(productServiceModel);
        
        return redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        List<ProductAllViewModel> products =
                this.productService.findAllProducts()
                        .stream()
                        .map(p -> {
                            String categoryName = p.getCategory().getName();
                            ProductAllViewModel productAllViewModel = this.modelMapper
                                    .map(p,ProductAllViewModel.class );
                            productAllViewModel.setCategory(categoryName);
                            return productAllViewModel;
                        })
                        .collect(Collectors.toList());
        modelAndView.addObject("products", products );

        return super.view("product/all-products", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
         AddProductBindingModel addProductBindingModel = 
                 this.modelMapper.map(this.productService.findProductById(id),AddProductBindingModel.class);
         modelAndView.addObject("product", addProductBindingModel);
        
         return view("product/edit-product", modelAndView);
    }
}
