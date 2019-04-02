package com.softuni.pcstore.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRegisterBindingModel {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public UserRegisterBindingModel() {
    }

    @NotNull(message = "Username cannot be null!")
    @NotEmpty(message = "Username cannot be empty!")
    @Length(min = 2, message = "Username must be at least 2 symbols!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "Password cannot be null!")
    @NotEmpty(message = "Password cannot be empty!")
    @Length(min = 4, max = 20, message = "Password must be between 4 and 20 symbols!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "Confirm password cannot be null!")
    @NotEmpty(message = "Confirm password cannot be empty!")
    @Length(min = 4, max = 20, message = "Confirm password must be between 4 and 20 symbols!")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull(message = "Email cannot be null.")
    @NotEmpty(message = "Email cannot be empty.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
