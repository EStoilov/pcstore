package com.softuni.pcstore.web.interceptors;

import com.softuni.pcstore.common.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String linkFavicon = Constants.URL_FAVICON;
        if(modelAndView != null){
            modelAndView.addObject("favicon", linkFavicon);
        }
    }
}
