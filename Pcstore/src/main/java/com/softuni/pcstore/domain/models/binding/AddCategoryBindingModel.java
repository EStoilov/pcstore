package com.softuni.pcstore.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

public class AddCategoryBindingModel {
    private String name;
    private String description;
    private MultipartFile image;

    public AddCategoryBindingModel() {
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
