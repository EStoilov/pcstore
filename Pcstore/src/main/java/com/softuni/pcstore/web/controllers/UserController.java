package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.binding.UserRegisterBindingModel;
import com.softuni.pcstore.domain.models.service.UserServiceModel;
import com.softuni.pcstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController extends  BaseController{
    
    private final UserService userService;
    private final ModelMapper modelMapper;
    
    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(
            ModelAndView modelAndView,
            @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel) {
        modelAndView.addObject("userRegisterBindingModel", userRegisterBindingModel);
        return view("register", modelAndView);
    }
    
    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(
            ModelAndView modelAndView,
            @Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
            BindingResult bindingResult) {
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userRegisterBindingModel", "password", "Passwords don't match."));
        }

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("userRegisterBindingModel", userRegisterBindingModel);
            return super.view("register", modelAndView);
        }
        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        
        return redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return view("login");
    }
}
