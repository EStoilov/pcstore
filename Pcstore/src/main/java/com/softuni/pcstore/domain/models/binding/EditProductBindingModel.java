package com.softuni.pcstore.domain.models.binding;

import java.math.BigDecimal;
import java.util.List;
public class EditProductBindingModel {
    
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> category;

    public EditProductBindingModel() {
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

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
