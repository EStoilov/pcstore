package com.softuni.pcstore.service;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.domain.entities.Category;
import com.softuni.pcstore.domain.entities.Product;
import com.softuni.pcstore.domain.models.service.ProductServiceModel;
import com.softuni.pcstore.repository.CategoryRepository;
import com.softuni.pcstore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
    public ProductServiceModel createProduct(ProductServiceModel productServiceModel, List<String> categories) {
        List<Category> categoriesEntity = this.categoryRepository.findAll().stream()
                .filter(c -> categories.contains(c.getId()))
                .collect(Collectors.toList());
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product.setCategory(categoriesEntity);
        product = this.productRepository.save(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return this.productRepository.findById(id)
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
    }

    @Override
    public ProductServiceModel editProduct(String id, 
                                           ProductServiceModel productServiceModel, 
                                           List<String> categories) {
       Product product = this.productRepository.findById(id)
                            .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));

        List<Category> categoriesEntity = this.categoryRepository.findAll().stream()
                .filter(c -> categories.contains(c.getId()))
                .collect(Collectors.toList());
        
        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setCategory(categoriesEntity);
        
        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        this.productRepository.delete(product);               
    }
    
    @Override
    public List<ProductServiceModel> getRandomProducts() {
        List<ProductServiceModel> productServiceModels = this.productRepository.findAll()
                .stream().map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
        
        List<ProductServiceModel> randomProducts = new ArrayList<>();
        
        int firstRandomIndex = this.getRandomNumber(0, productServiceModels.size()-1);
        int secRandomIndex = this.getRandomNumber(0, productServiceModels.size()-1);
        int thirdRandomIndex = this.getRandomNumber(0, productServiceModels.size()-1);
        
        randomProducts.add(productServiceModels.get(firstRandomIndex));
        randomProducts.add(productServiceModels.get(secRandomIndex));
        randomProducts.add(productServiceModels.get(thirdRandomIndex));
        
        return randomProducts;
    }

    private int getRandomNumber(int min, int max){
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
