package com.softuni.pcstore.service;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.domain.entities.User;
import com.softuni.pcstore.domain.models.service.UserServiceModel;
import com.softuni.pcstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();
        
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }


        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.EXCEPTION_USERNAME_NOT_FOUND));
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        List<UserServiceModel> users =  this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
        
        return  users;
    }

    @Override
    public void setUserRole(String id, String role) {
        UserServiceModel user = this.modelMapper.map(this.userRepository
                .findById(id).orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND)), UserServiceModel.class);

        user.getAuthorities().clear();

        switch (role) {
            case "user":
                user.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                break;
            case "moderator":
                user.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                user.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
                break;
            case "admin":
                user.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                user.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
                user.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));
                break;
        }

        this.userRepository.save(this.modelMapper.map(user, User.class));
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        
        return userServiceModel;
    }
}
