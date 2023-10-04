package com.bookstore.dao;

import com.bookstore.model.Roles;

import java.util.List;

public interface IRoleManager {

    List<Roles> getAllRoles();

    Roles findExistingRoleName(String roleName);

    void save(String roleName);

}
