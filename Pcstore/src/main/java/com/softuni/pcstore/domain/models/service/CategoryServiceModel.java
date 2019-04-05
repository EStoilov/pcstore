package com.softuni.pcstore.domain.models.service;

import com.softuni.pcstore.domain.entities.Product;

import java.util.List;

public class CategoryServiceModel extends BaseServiceModel{
    
    private String name;
    private String description;
    private String image;
    private List<ProductServiceModel> products;

    public CategoryServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ProductServiceModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductServiceModel> products) {
        this.products = products;
    }
}
