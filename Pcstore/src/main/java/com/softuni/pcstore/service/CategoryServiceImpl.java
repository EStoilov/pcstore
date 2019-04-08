package com.softuni.pcstore.service;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.domain.entities.Category;
import com.softuni.pcstore.domain.models.service.CategoryServiceModel;
import com.softuni.pcstore.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findCategoryByName(String name) {
        return this.categoryRepository.findByName(name)
                .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);
        
        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel findCategoryById(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));

        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel) {
        Category category = this.categoryRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        
        category.setName(categoryServiceModel.getName());
        category.setDescription(categoryServiceModel.getDescription());
        
        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel deleteCategory(String id, CategoryServiceModel categoryServiceModel) {
        Category category = this.categoryRepository
                .findById(id).orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        this.categoryRepository.delete(category);
        
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }
}
