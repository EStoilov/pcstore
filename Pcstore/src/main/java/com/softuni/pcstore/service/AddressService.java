package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.AddressServiceModel;

import java.util.List;

public interface AddressService {
    
    List<AddressServiceModel> findAddressesByUsername(String username);
    
    AddressServiceModel addAddress(AddressServiceModel addressServiceModel, String username);
    
    AddressServiceModel findAddressById(String id);
    
    void deleteAddress(String id);
    
    AddressServiceModel editAddress(String id, AddressServiceModel addressServiceModel);
}
