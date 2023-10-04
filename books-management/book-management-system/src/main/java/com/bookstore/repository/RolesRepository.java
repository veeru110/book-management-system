package com.bookstore.repository;

import com.bookstore.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Roles findByRoleNameAndIsDeletedFalse(String roleName);
}
