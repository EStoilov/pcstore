package com.softuni.pcstore.config;

import com.softuni.pcstore.web.interceptors.FaviconInterceptor;
import com.softuni.pcstore.web.interceptors.LogInterceptor;
import com.softuni.pcstore.web.interceptors.NavbarCategoriesInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    
    private final NavbarCategoriesInterceptor navbarCategoriesInterceptor;
    private final LogInterceptor logInterceptor;
    private final FaviconInterceptor faviconInterceptor;

    @Autowired
    public InterceptorConfiguration(NavbarCategoriesInterceptor navbarCategoriesInterceptor, LogInterceptor logInterceptor, FaviconInterceptor faviconInterceptor) {
        this.navbarCategoriesInterceptor = navbarCategoriesInterceptor;
        this.logInterceptor = logInterceptor;
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(navbarCategoriesInterceptor) ;
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/categories/**", "/products/**");
        registry.addInterceptor(faviconInterceptor);
    }
}
