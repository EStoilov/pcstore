package com.softuni.pcstore.web.interceptors;

import com.softuni.pcstore.domain.models.views.CategoryNavbarViewModel;
import com.softuni.pcstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NavbarCategoriesInterceptor extends HandlerInterceptorAdapter {
    
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public NavbarCategoriesInterceptor(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }
        
        List<CategoryNavbarViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryNavbarViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("navCategories", categories);
        
        super.postHandle(request, response, handler, modelAndView);
    }
}
