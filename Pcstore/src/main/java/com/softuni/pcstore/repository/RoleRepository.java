package com.softuni.pcstore.repository;

import com.softuni.pcstore.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    
    Role findByAuthority(String authority);
}
