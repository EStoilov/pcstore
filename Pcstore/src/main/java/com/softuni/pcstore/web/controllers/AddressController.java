package com.softuni.pcstore.web.controllers;

import com.softuni.pcstore.domain.models.binding.AddAddressBindingModel;
import com.softuni.pcstore.domain.models.binding.EditAddressBindingModel;
import com.softuni.pcstore.domain.models.service.AddressServiceModel;
import com.softuni.pcstore.domain.models.views.AddressViewModel;
import com.softuni.pcstore.domain.models.views.AddressesAllViewModel;
import com.softuni.pcstore.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/addresses")
public class AddressController extends BaseController {
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressController(AddressService addressService, ModelMapper modelMapper) {
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/fetch")
    @ResponseBody
    public List<AddressViewModel> fetchAddresses(Principal principal) {
        String username = principal.getName();
        List<AddressViewModel> addresses = this.addressService.findAddressesByUsername(username)
                .stream()
                .map(a -> this.modelMapper.map(a, AddressViewModel.class))
                .collect(Collectors.toList());
        
        return addresses;
    }
    
    @GetMapping("/add")
    public ModelAndView addAddress(ModelAndView modelAndView,
                                   @ModelAttribute AddAddressBindingModel addAddressBindingModel){
        modelAndView.addObject("addAddressBindingModel", addAddressBindingModel);
        
        return view("address/add-address", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addAddressConfirm(ModelAndView modelAndView,
                                          @Valid @ModelAttribute AddAddressBindingModel addAddressBindingModel,
                                          BindingResult bindingResult,
                                          Principal principal){
        if(bindingResult.hasErrors()){
            modelAndView.addObject("addAddressBindingModel",addAddressBindingModel);
            
            return view("address/add-address", modelAndView);
        }
        String username = principal.getName();
        this.addressService.addAddress(this.modelMapper.map(addAddressBindingModel, AddressServiceModel.class), username);

        return redirect("/users/profile");
    }
    
    @GetMapping("/all/{username}")
    public ModelAndView findMyAddresses(ModelAndView modelAndView, @PathVariable String username){
        List<AddressesAllViewModel> addresses = this.addressService.findAddressesByUsername(username).stream()
                .map(a -> this.modelMapper.map(a, AddressesAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("addresses", addresses);
        
        return view("address/all-address", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteAddress(@PathVariable String id, ModelAndView modelAndView) {
        AddressesAllViewModel model = this.modelMapper
                .map(this.addressService.findAddressById(id), AddressesAllViewModel.class);
        modelAndView.addObject("model",model );

        return view("address/delete-address", modelAndView);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteProductConfirm(@PathVariable String id) {
        this.addressService.deleteAddress(id);

        return redirect("/users/profile");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editAddress(@PathVariable String id, ModelAndView modelAndView) {
        AddressesAllViewModel model = this.modelMapper
                .map(this.addressService.findAddressById(id), AddressesAllViewModel.class);
        modelAndView.addObject("model",model );

        return view("address/edit-address", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute EditAddressBindingModel model) {
        this.addressService.editAddress(id, this.modelMapper.map(model, AddressServiceModel.class));

        return redirect("/users/profile");
    }
}
