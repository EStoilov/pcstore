package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.error.NotFoundExceptions;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler({NotFoundExceptions.class})
    public ModelAndView handleProductNotFound(RuntimeException e){
        ModelAndView modelAndView = new ModelAndView("/errors/error");
        modelAndView.addObject("myMessage", e.getMessage());

        return modelAndView;
    }

    @ExceptionHandler({Throwable.class})
    public ModelAndView handleSqlException(Throwable e){
        ModelAndView modelAndView = new ModelAndView("/errors/error");
        Throwable throwable = e;

        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        modelAndView.addObject("message", throwable.getMessage());

        return modelAndView;
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ModelAndView handleIllegalArgumentExceptions(RuntimeException e){
        ModelAndView modelAndView = new ModelAndView("/errors/error");
        modelAndView.addObject("myMessage", e.getMessage());
        return modelAndView;
    }
}
