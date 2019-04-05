package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
    List<CategoryServiceModel> findAllCategories();
    
    CategoryServiceModel findCategoryByName(String name);
    
    CategoryServiceModel findCategoryById(String id);

    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);
}
