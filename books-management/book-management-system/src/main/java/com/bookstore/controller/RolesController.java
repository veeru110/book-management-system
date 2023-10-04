package com.bookstore.controller;

import com.bookstore.constants.UserRole;
import com.bookstore.dao.IRoleManager;
import com.bookstore.model.Roles;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class RolesController {

    private final IRoleManager roleManager;

    @Autowired
    public RolesController(IRoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @PostConstruct
    public void rolesInit() {
        UserRole[] userRoles = UserRole.values();
        for (UserRole userRole : userRoles) {
            Roles roles = roleManager.findExistingRoleName(userRole.name());
            if (Objects.isNull(roles)) {
                roleManager.save(userRole.name());
            }
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<com.bookstore.model.Roles> getAllRoles() {
        return roleManager.getAllRoles();
    }
}
