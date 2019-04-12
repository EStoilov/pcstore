package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.ProductServiceModel;

import java.io.IOException;
import java.util.List;
public interface ProductService {
    List<ProductServiceModel>  findAllProducts();

    ProductServiceModel createProduct(ProductServiceModel productServiceModel, List<String> categories);

    ProductServiceModel findProductById(String id);
    
    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel, List<String> categories);
    
    void deleteProduct(String id);
            
}
