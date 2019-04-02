package com.softuni.pcstore.domain.models.service;

import com.softuni.pcstore.domain.entities.Product;

import java.util.List;

public class CategoryServiceModel extends BaseServiceModel{
    
    private String name;
    private String description;
    private String image;
    private List<Product> products;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
