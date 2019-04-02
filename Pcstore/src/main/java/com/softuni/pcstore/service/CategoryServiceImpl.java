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
}
