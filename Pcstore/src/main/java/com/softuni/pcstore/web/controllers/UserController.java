package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.binding.UserRegisterBindingModel;
import com.softuni.pcstore.domain.models.service.UserServiceModel;
import com.softuni.pcstore.domain.models.views.UserAllViewModel;
import com.softuni.pcstore.domain.models.views.UserProfileViewModel;
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
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
            return view("register", modelAndView);
        }
        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        
        return redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView loginConfirm() {
        return view("login");
    }
    
    @GetMapping("/all")
    public ModelAndView findAllUsers(ModelAndView modelAndView){
        List<UserAllViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u -> {
                    UserAllViewModel user = this.modelMapper.map(u, UserAllViewModel.class);
                    user.setAuthorities(u.getAuthorities().stream()
                            .map(a -> a.getAuthority()).collect(Collectors.toSet()));
                    return user;
                }).collect(Collectors.toList());


        modelAndView.addObject("users", users);
        
        return view("user/all-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id) {
        this.userService.setUserRole(id, "user");

        return redirect("/users/all");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id) {
        this.userService.setUserRole(id, "moderator");

        return redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");

        return redirect("/users/all");
    }
    
    @GetMapping("/profile")
    public ModelAndView userProfile(ModelAndView modelAndView, Principal principal){
        String username = principal.getName();
        UserProfileViewModel userProfileViewModel = this.modelMapper
                .map(this.userService.findByUsername(username), UserProfileViewModel.class);
        modelAndView.addObject("user", userProfileViewModel);
        
        return view("user/my-profile", modelAndView); 
    }
}
