package com.softuni.pcstore.repository;

import com.softuni.pcstore.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface AddressRepository extends JpaRepository<Address, String> {
    
    @Query("SELECT a FROM Address a WHERE a.user.username = :username")
    List<Address> findAllByUser(@Param("username") String username);
}
