package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);

    List<UserServiceModel> findAllUsers();
    
    UserServiceModel findByUsername(String username);
    
    void setUserRole(String id, String role);
    
}
