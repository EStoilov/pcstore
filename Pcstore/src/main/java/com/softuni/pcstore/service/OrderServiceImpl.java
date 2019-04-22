package com.softuni.pcstore.service;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.domain.entities.Address;
import com.softuni.pcstore.domain.entities.Order;
import com.softuni.pcstore.domain.entities.User;
import com.softuni.pcstore.domain.models.service.AddressServiceModel;
import com.softuni.pcstore.domain.models.service.OrderServiceModel;
import com.softuni.pcstore.domain.models.service.ProductServiceModel;
import com.softuni.pcstore.domain.models.service.UserServiceModel;
import com.softuni.pcstore.domain.models.views.ProductCartViewModel;
import com.softuni.pcstore.repository.AddressRepository;
import com.softuni.pcstore.repository.OrderRepository;
import com.softuni.pcstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, AddressRepository addressRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    
    @Override
    public OrderServiceModel createOrder(HttpSession session, String userName) {
        List<ProductCartViewModel> productCartViewModels =(List<ProductCartViewModel>) session
                .getAttribute(Constants.SET_ATTRIBUTE_SESSION_SHOPPING_CART);
        List<ProductServiceModel> productServiceModels = productCartViewModels.stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setProducts(productServiceModels);
        orderServiceModel.setTotalPrice(this.calcTotalPrice(productCartViewModels));
        
        return orderServiceModel;
    }

    @Override
    public OrderServiceModel createOrderConfirm(HttpSession session, String username, String addressId) {
        OrderServiceModel orderServiceModel = this.createOrder(session, username);
        Order order = this.modelMapper.map(orderServiceModel, Order.class);
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        Address address = this.addressRepository.findById(addressId)
                .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND)) ;
        LocalDateTime date = LocalDateTime.now();
        order.setAddress(address);
        order.setUser(user);
        order.setSendDate(date);
        this.orderRepository.save(order);
        
        return orderServiceModel;
    }

    @Override
    public List<OrderServiceModel> findAllMyOrders(String username) {
        List<OrderServiceModel> orders = this.orderRepository
                .findAllOrdersByUser(username)
                .stream()
                .map(o-> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
        
        return orders;
    }

    private BigDecimal calcTotalPrice(List<ProductCartViewModel> productServiceModels) {
        BigDecimal result = new BigDecimal(0);
        for (ProductCartViewModel productCartViewModel : productServiceModels) {
            result = result.add(productCartViewModel.getPrice()
                    .multiply(new BigDecimal(productCartViewModel.getQuantity()))) ;
            
        }
        return result;
    }
}
