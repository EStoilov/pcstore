package com.softuni.pcstore.domain.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    
    private String name;
    private String description;
    private String image;
    private List<Product> products;

    public Category() {
    }

    @Column(name = "name", nullable = false, updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", columnDefinition = "text",nullable = false, updatable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "image", nullable = false)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @OneToMany(targetEntity = Product.class)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
    )
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
