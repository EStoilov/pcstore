package com.softuni.pcstore.service;

import com.softuni.pcstore.common.Constants;
import com.softuni.pcstore.domain.entities.Product;
import com.softuni.pcstore.domain.models.views.ProductCartViewModel;
import com.softuni.pcstore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addProduct(String productId, HttpSession httpSession) {
        Product productEntity = this.productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException(Constants.EXCEPTION_NOT_FOUND));
        ProductCartViewModel productCartViewModel = this.modelMapper.map(productEntity, ProductCartViewModel.class);
        List<ProductCartViewModel>  products = this.retrieveCart(httpSession);
        
        if(this.checkProductContains(products, productId )){
            this.increaseQuantity(products, productId);
        } else {
            productCartViewModel.setQuantity(1);
            products.add(productCartViewModel);
        }
        
        httpSession.setAttribute(Constants.SET_ATTRIBUTE_SESSION_TOTAL_SUM, this.calcTotal(products));
    }

    private void initCart(HttpSession httpSession) {
        if (httpSession.getAttribute(Constants.SET_ATTRIBUTE_SESSION_SHOPPING_CART) == null) {
            httpSession.setAttribute(Constants.SET_ATTRIBUTE_SESSION_SHOPPING_CART, new LinkedList<>());
        }
    }

    private List<ProductCartViewModel> retrieveCart(HttpSession session) {
        this.initCart(session);

        return (List<ProductCartViewModel>) session.getAttribute(Constants.SET_ATTRIBUTE_SESSION_SHOPPING_CART);
    }

    public BigDecimal calcTotal(List<ProductCartViewModel> cart) {
        
        BigDecimal result = new BigDecimal(0);
        for (ProductCartViewModel item : cart) {
            result = result.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        return result;
    }
    
    private boolean checkProductContains(List<ProductCartViewModel> cart, String productId){
        for (ProductCartViewModel currentProduct : cart) {
           if(currentProduct.getId().equals(productId)){
               return true;
           }
        }
        return false;
    }
    
    private void increaseQuantity(List<ProductCartViewModel> cart, String productId){
        for (ProductCartViewModel productCartViewModel : cart) {
            if(productCartViewModel.getId().equals(productId)){
                int currentQuantity = productCartViewModel.getQuantity();
                productCartViewModel.setQuantity(++currentQuantity);
            }
        }
    }

    @Override
    public void removeProduct(String id, List<ProductCartViewModel> products) {
        products.removeIf(p -> p.getId().equals(id));
    }
}
