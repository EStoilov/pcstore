package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.entities.Product;
import com.softuni.pcstore.domain.models.service.CategoryServiceModel;
import com.softuni.pcstore.domain.models.service.ProductServiceModel;
import com.softuni.pcstore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, 
                              ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel createProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product = this.productRepository.save(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return this.productRepository.findById(id)
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException());
    }
    
    
}
