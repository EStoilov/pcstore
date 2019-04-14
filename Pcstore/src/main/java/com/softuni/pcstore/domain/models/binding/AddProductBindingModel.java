package com.softuni.pcstore.domain.models.binding;

import com.softuni.pcstore.common.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
public class AddProductBindingModel {
    
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> category;
    private MultipartFile image;

    public AddProductBindingModel() {
    }

    @NotNull(message = Constants.EXCEPTION_PRODUCT_NAME_NOT_NULL)
    @NotEmpty(message = Constants.EXCEPTION_PRODUCT_NAME_NOT_EMPTY)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = Constants.EXCEPTION_PRODUCT_DESCRIPTION_NOT_NULL)
    @NotEmpty(message = Constants.EXCEPTION_PRODUCT_DESCRIPTION_NOT_EMPTY)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = Constants.EXCEPTION_PRODUCT_PRICE_NOT_NULL)
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

    @NotNull(message = Constants.EXCEPTION_PRODUCT_IMAGE_NOT_NULL)
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
