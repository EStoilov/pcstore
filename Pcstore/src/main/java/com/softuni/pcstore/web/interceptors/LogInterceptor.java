package com.softuni.pcstore.web.interceptors;

import com.softuni.pcstore.domain.models.service.LogServiceModel;
import com.softuni.pcstore.service.LogService;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
    
    private final LogService logService;

    @Autowired
    public LogInterceptor(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        String event = handlerMethod.getMethod().getName();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LogServiceModel model = new LogServiceModel(username, event);
        logService.createLog(model);
    }
}
