package com.softuni.pcstore.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
public class AddProductBindingModel {
    
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private MultipartFile image;

    public AddProductBindingModel() {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
