package com.softuni.pcstore.repository;

import com.softuni.pcstore.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.user.username = :username") 
   List<Order > findAllOrdersByUser(@Param("username") String username);
}
