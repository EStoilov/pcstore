package com.softuni.pcstore.domain.models.binding;

import com.softuni.pcstore.common.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddCategoryBindingModel {
    private String name;
    private String description;
    private MultipartFile image;

    public AddCategoryBindingModel() {
    }

    @NotNull(message = Constants.EXCEPTION_CATEGORY_NAME_NOT_NULL)
    @NotEmpty(message = Constants.EXCEPTION_CATEGORY_NAME_NOT_EMPTY)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = Constants.EXCEPTION_CATEGORY_DESCRIPTION_NOT_NULL)
    @NotEmpty(message = Constants.EXCEPTION_CATEGORY_DESCRIPTION_NOT_EMPTY)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = Constants.EXCEPTION_CATEGORY_IMAGE_NOT_NULL)
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
