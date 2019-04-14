package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.binding.AddProductBindingModel;
import com.softuni.pcstore.domain.models.binding.EditProductBindingModel;
import com.softuni.pcstore.domain.models.service.ProductServiceModel;
import com.softuni.pcstore.domain.models.views.DeleteProductViewModel;
import com.softuni.pcstore.domain.models.views.EditProductViewModel;
import com.softuni.pcstore.domain.models.views.ProductAllViewModel;
import com.softuni.pcstore.domain.models.views.ProductDetailsViewModel;
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
@RequestMapping("/products")
public class ProductController extends BaseController {
    
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct(@ModelAttribute AddProductBindingModel addProductBindingModel) {
        return view("product/add-product");
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(ModelAndView modelAndView,
                                          @Valid @ModelAttribute AddProductBindingModel addProductBindingModel,
                                          BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            modelAndView.addObject("addProductBindingModel", addProductBindingModel);
            
            return view("product/add-product", modelAndView);
        }
        ProductServiceModel productServiceModel = 
                this.modelMapper.map(addProductBindingModel, ProductServiceModel.class);
        productServiceModel.setImage(this.cloudinaryService.uploadImage(addProductBindingModel.getImage()));
        this.productService.createProduct(productServiceModel,addProductBindingModel.getCategory());
        
        return redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        List<ProductAllViewModel> products =
                this.productService.findAllProducts()
                        .stream()
                        .map(p -> this.modelMapper
                                    .map(p,ProductAllViewModel.class))
                        .collect(Collectors.toList());
        modelAndView.addObject("products", products );

        return view("product/all-products", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
         EditProductViewModel addProductBindingModel = 
                 this.modelMapper.map(this.productService.findProductById(id),EditProductViewModel.class);
         modelAndView.addObject("product", addProductBindingModel);
        
         return view("product/edit-product", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute EditProductBindingModel model) {
        this.productService.editProduct(id, this.modelMapper.map(model, ProductServiceModel.class), model.getCategory());
        
        return redirect("/products/details/" + id); 
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView details(@PathVariable String id, ModelAndView modelAndView){
        ProductDetailsViewModel detailsProduct = this.modelMapper.map(
                this.productService.findProductById(id), ProductDetailsViewModel.class);
        modelAndView.addObject("product", detailsProduct);
        
        return view("product/details-product", modelAndView);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView) {
        DeleteProductViewModel deleteProductViewModel =
                this.modelMapper.map(this.productService.findProductById(id),DeleteProductViewModel.class);
        modelAndView.addObject("product", deleteProductViewModel);

        return view("product/delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProductConfirm(@PathVariable String id) {
        this.productService.deleteProduct(id);

        return redirect("/products/all");
    }
}
