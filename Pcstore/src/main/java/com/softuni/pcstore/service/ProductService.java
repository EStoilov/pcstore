package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.ProductServiceModel;

import java.io.IOException;
import java.util.List;
public interface ProductService {
    List<ProductServiceModel>  findAllProducts();

    ProductServiceModel createProduct(ProductServiceModel productServiceModel);

    ProductServiceModel findProductById(String id);
}
