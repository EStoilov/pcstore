package com.softuni.pcstore.service;

import com.softuni.pcstore.domain.models.service.RoleServiceModel;

import java.util.Set;
public interface RoleService {
    void seedRolesInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
