package com.softuni.pcstore.config;

import com.softuni.pcstore.web.interceptors.NavbarCategoriesInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    
    private final NavbarCategoriesInterceptor navbarCategoriesInterceptor;

    @Autowired
    public InterceptorConfiguration(NavbarCategoriesInterceptor navbarCategoriesInterceptor) {
        this.navbarCategoriesInterceptor = navbarCategoriesInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(navbarCategoriesInterceptor) ;
    }
}
