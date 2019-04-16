package com.softuni.pcstore.service;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.domain.entities.Address;
import com.softuni.pcstore.domain.entities.User;
import com.softuni.pcstore.domain.models.service.AddressServiceModel;
import com.softuni.pcstore.domain.models.service.UserServiceModel;
import com.softuni.pcstore.repository.AddressRepository;
import com.softuni.pcstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AddressServiceModel> findAddressesByUsername(String username) {
        return this.addressRepository.findAllByUser(username)
                .stream()
                .map(a -> this.modelMapper.map(a, AddressServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public AddressServiceModel addAddress(AddressServiceModel addressServiceModel, String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        addressServiceModel.setUser(userServiceModel);
        this.addressRepository.save(this.modelMapper.map(addressServiceModel, Address.class));
        
        return addressServiceModel;
    }

    @Override
    public AddressServiceModel findAddressById(String id) {
        Address addressEntity  = this.addressRepository.findById(id)
        .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        
        return this.modelMapper.map(addressEntity, AddressServiceModel.class); 
    }

    @Override
    public void deleteAddress(String id) {
        Address address = this.addressRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        this.addressRepository.delete(address);
    }

    @Override
    public AddressServiceModel editAddress(String id, AddressServiceModel addressServiceModel) {
        Address address = this.addressRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        address.setCity(addressServiceModel.getCity());
        address.setStreet(addressServiceModel.getStreet());
        address.setNumber(addressServiceModel.getNumber());
        address.setPostCode(addressServiceModel.getPostCode());
        address.setPhoneNumber(addressServiceModel.getPhoneNumber());
        
        return this.modelMapper.map(this.addressRepository.saveAndFlush(address), AddressServiceModel.class);
    }
}