package com.bookstore.dao;

import com.bookstore.model.Roles;
import com.bookstore.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleManagerImpl implements IRoleManager {

    private final RolesRepository rolesRepository;

    @Autowired
    public RoleManagerImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Roles findExistingRoleName(String roleName) {
        return rolesRepository.findByRoleNameAndIsDeletedFalse(roleName);
    }

    @Override
    public void save(String roleName) {
        Roles roles = new Roles();
        roles.setRoleName(roleName);
        rolesRepository.save(roles);
    }
}
