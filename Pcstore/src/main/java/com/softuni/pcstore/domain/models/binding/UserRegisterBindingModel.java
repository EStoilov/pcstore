package com.softuni.pcstore.domain.models.binding;

import com.softuni.pcstore.common.Constants;
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

    @NotNull(message = Constants.EXCEPTION_USERNAME__NOT_NULL)
    @NotEmpty(message = Constants.EXCEPTION_USERNAME__NOT_EMPTY)
    @Length(min = 2, message = Constants.EXCEPTION_USERNAME__LENGTH)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = Constants.EXCEPTION_PASSWORD__NOT_NULL)
    @NotEmpty(message = Constants.EXCEPTION_PASSWORD__NOT_EMPTY)
    @Length(min = 4, max = 20, message = Constants.EXCEPTION_PASSWORD_LENGTH)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = Constants.EXCEPTION_CONFIRM_PASSWORD__NOT_NULL)
    @NotEmpty(message = Constants.EXCEPTION_CONFIRM_PASSWORD__NOT_EMPTY)
    @Length(min = 4, max = 20, message = Constants.EXCEPTION_CONFIRM_PASSWORD_LENGTH)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull(message = Constants.EXCEPTION_EMAIL_NOT_NULL)
    @NotEmpty(message = Constants.EXCEPTION_EMAIL_NOT_EMPTY)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
