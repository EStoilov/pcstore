package com.softuni.pcstore.domain.models.service;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
public class ProductServiceModel extends BaseServiceModel {
    private String name;
    private String description;
    private BigDecimal price;
    private List<CategoryServiceModel> category;
    private String image;
    private Integer quantity;

    public ProductServiceModel() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<CategoryServiceModel> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryServiceModel> category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
