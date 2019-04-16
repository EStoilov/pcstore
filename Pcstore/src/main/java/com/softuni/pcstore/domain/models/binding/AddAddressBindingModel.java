package com.softuni.pcstore.domain.models.binding;

import com.softuni.pcstore.common.Constants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
public class AddAddressBindingModel {
    private String city;
    private String street;
    private String number;
    private String postCode;
    private String phoneNumber;

    public AddAddressBindingModel() {
    }

    @NotEmpty(message = Constants.EXCEPTION_CITY_NOT_EMPTY)
    @NotNull(message = Constants.EXCEPTION_CITY_NOT_NULL)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty(message = Constants.EXCEPTION_STREET_NOT_EMPTY)
    @NotNull(message = Constants.EXCEPTION_STREET_NOT_NULL)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @NotEmpty(message = Constants.EXCEPTION_NUMBER_NOT_EMPTY)
    @NotNull(message = Constants.EXCEPTION_NUMBER_NOT_NULL)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NotEmpty(message = Constants.EXCEPTION_POSTCODE_NOT_EMPTY)
    @NotNull(message = Constants.EXCEPTION_POSTCODE_NOT_NULL)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @NotEmpty(message = Constants.EXCEPTION_PHONE_NUMBER_NOT_EMPTY)
    @NotNull(message = Constants.EXCEPTION_PHONE_NUMBER_NOT_NULL)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
